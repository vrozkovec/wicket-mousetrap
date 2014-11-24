package com.mysticcoders.wicket.mousetrap;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

/**
 * A resource reference that contributes Mousetrap's
 * <a href="https://github.com/ccampbell/mousetrap/tree/master/plugins/global-bind">Global bind</a>
 * plugin. This extension allows you to specify keyboard events that will work anywhere including inside textarea/input fields.
 * To enable it use {@link com.mysticcoders.wicket.mousetrap.Mousetrap.BindType#GLOBAL} or
 * {@link com.mysticcoders.wicket.mousetrap.Mousetrap.BindType#DEFAULT_GLOBAL} bind types
 */
public class MouseTrapGlobalJSReference extends JavaScriptResourceReference
{
	public MouseTrapGlobalJSReference()
	{
		super(MouseTrapGlobalJSReference.class, "mousetrap-global-bind.js");
	}

	@Override
	public Iterable<? extends HeaderItem> getDependencies()
	{
		List<HeaderItem> headerItems = new ArrayList<HeaderItem>();
		headerItems.add(JavaScriptHeaderItem.forReference(new MouseTrapJSReference()));
		return headerItems;
	}
}
