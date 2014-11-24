package com.mysticcoders.wicket.mousetrap;

import org.apache.wicket.resource.JQueryPluginResourceReference;

/**
 * A resource reference that contributes <a href="http://craig.is/killing/mice">Mousetrap.js</a>
 */
public class MouseTrapJSReference extends JQueryPluginResourceReference
{
	public MouseTrapJSReference()
	{
		super(MouseTrapJSReference.class, "mousetrap.js");
	}
}
