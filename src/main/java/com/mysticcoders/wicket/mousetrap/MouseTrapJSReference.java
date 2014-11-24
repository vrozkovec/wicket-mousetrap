package com.mysticcoders.wicket.mousetrap;

import org.apache.wicket.request.resource.JavaScriptResourceReference;

/**
 * A resource reference that contributes <a href="http://craig.is/killing/mice">Mousetrap.js</a>
 */
public class MouseTrapJSReference extends JavaScriptResourceReference
{
	public MouseTrapJSReference()
	{
		super(MouseTrapJSReference.class, "mousetrap.js");
	}
}
