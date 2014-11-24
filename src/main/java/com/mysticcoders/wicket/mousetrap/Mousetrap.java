package com.mysticcoders.wicket.mousetrap;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.util.lang.Args;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Binding for mousetrap.js
 *
 * from: http://craig.is/killing/mice
 *
 * @author Andrew Lombardi
 */
public class Mousetrap extends Behavior {
    private static final long serialVersionUID = 1L;

    /**
     * The type of the key binding
     */
    public enum BindType {
        /**
         * The key binding won't be fired when the focus is on a text field or text area
         */
        NORMAL,

        /**
         * The key binding will be fired when the focus is on a text field or text area
         */
        GLOBAL,

        /**
         * The key binding won't be fired when the focus is on a text field or text area and the
         * default behavior of the JavaScript event will be prevented and stopped
         */
        DEFAULT,

        /**
         * The key binding will be fired when the focus is on a text field or text area and the
         * default behavior of the JavaScript event will be prevented and stopped
         */
        DEFAULT_GLOBAL
    }

    private static class Trap implements Serializable {
        private final KeyBinding keyBinding;
        private final BindType bindType;
        private final Component component;
        private final String jsEvent;

        private Trap(KeyBinding keyBinding, BindType bindType, Component component, String jsEvent) {
            this.keyBinding = Args.notNull(keyBinding, "keyBinding");
            this.bindType = Args.notNull(bindType, "bindType");
            this.component = Args.notNull(component, "component");
            this.jsEvent = jsEvent;
        }
    }

    /**
     * A list of all registered mouse traps
     */
    private final List<Trap> traps = new ArrayList<Trap>();

    /**
     * A flag indicating whether {@link com.mysticcoders.wicket.mousetrap.MouseTrapGlobalJSReference}
     * should be contributed.
     * Switches its value to {@code true} as soon as a binding with type {@linkplain com.mysticcoders.wicket.mousetrap.Mousetrap.BindType#GLOBAL}
     * or {@linkplain com.mysticcoders.wicket.mousetrap.Mousetrap.BindType#DEFAULT_GLOBAL} is added
     */
    private boolean renderGlobalJsReference = false;

    /**
     * Adds a key binding to Mousetrap for given behavior
     *
     * @param keyBinding keys to bind
     */
    public Mousetrap addBind(KeyBinding keyBinding, Component component) {
        return addBind(keyBinding, component, "click", BindType.NORMAL);
    }

    public Mousetrap addBind(KeyBinding keyBinding, Component component, String jsEvent) {
        return addBind(keyBinding, component, jsEvent, BindType.NORMAL);
    }

    public Mousetrap addBind(KeyBinding keyBinding, Component component, String jsEvent, BindType type) {
        if (type == BindType.GLOBAL || type == BindType.DEFAULT_GLOBAL) {
            renderGlobalJsReference = true;
        }
        traps.add(new Trap(keyBinding, type, component, jsEvent));
        return this;
    }

    /**
     * Renders the JS resources needed by this behavior and the JavaScript that registers the key bindings
     *
     * @param component The component this behavior is attached to
     * @param response The header response to write to
     */
    public void renderHead(final Component component, IHeaderResponse response) {
        super.renderHead(component, response);

        if (traps.size() > 0) {
            if (renderGlobalJsReference) {
                response.render(JavaScriptHeaderItem.forReference(new MouseTrapGlobalJSReference()));
            } else {
                response.render(JavaScriptHeaderItem.forReference(new MouseTrapJSReference()));
            }

            CharSequence mouseTrapJS = getMouseTraps(traps);
            response.render(OnDomReadyHeaderItem.forScript(mouseTrapJS));
        }
    }

    /**
     * Constructs the JavaScript that registers the key bindings
     *
     * @param traps The list of added mouse traps
     * @return the JavaScript that registers the key bindings
     */
    private CharSequence getMouseTraps(List<Trap> traps)
    {
        StringBuilder mousetrapBinds = new StringBuilder();

        for (Trap trap : traps) {
            KeyBinding keyBinding = trap.keyBinding;
            BindType bindType = trap.bindType;
            Component component = trap.component;
            String jsEvent = trap.jsEvent;

            mousetrapBinds.append("Mousetrap.bind");
            if (bindType == BindType.GLOBAL || bindType == BindType.DEFAULT_GLOBAL) {
                mousetrapBinds.append("Global");
            }
            mousetrapBinds.append('(').append(keyBinding).append(", function() { ");

            mousetrapBinds.append("jQuery('#").append(component.getMarkupId()).append("').triggerHandler('").append(jsEvent).append("');");

            if (bindType == BindType.DEFAULT || bindType == BindType.DEFAULT_GLOBAL) {
                mousetrapBinds.append("return false;");
            }

            mousetrapBinds.append('}');

            if (keyBinding.getEventType() != null) {
                mousetrapBinds.append(", '")
                        .append(keyBinding.getEventType())
                        .append('\'');
            }

            mousetrapBinds.append(");\n");
        }

        return mousetrapBinds;
    }
}
