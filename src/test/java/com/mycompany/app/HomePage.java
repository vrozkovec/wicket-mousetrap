package com.mycompany.app;

import com.mysticcoders.wicket.mousetrap.KeyBinding;
import com.mysticcoders.wicket.mousetrap.Mousetrap;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);

		final FeedbackPanel feedback = new FeedbackPanel("feedback");
		feedback.setOutputMarkupId(true);

		final Mousetrap mousetrap = new Mousetrap();
		add(mousetrap);

		final AjaxLink<Void> link1 = new AjaxLink<Void>("link1")
		{
			@Override
			public void onClick(AjaxRequestTarget target)
			{
				info("Link 1 has been clicked");
				target.add(feedback);
			}
		};
		mousetrap.addBind(new KeyBinding("keyup").addKeyCombo("ctrl", "alt", "g"), link1);

		AjaxLink<Void> link2 = new AjaxLink<Void>("link2")
		{
			@Override
			public void onClick(AjaxRequestTarget target)
			{
				info("Link 2 has been clicked");
				target.add(feedback);
			}
		};
		mousetrap.addBind(new KeyBinding().addKeyCombo("ctrl", "alt", "h"), link2);

		add(feedback, link1, link2);
    }
}
