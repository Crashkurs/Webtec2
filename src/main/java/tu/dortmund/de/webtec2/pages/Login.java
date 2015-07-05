package tu.dortmund.de.webtec2.pages;

import javax.inject.Inject;

import org.apache.shiro.SecurityUtils;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.services.PageRenderLinkSource;

public class Login {
	
	@Inject
	PageRenderLinkSource pageRender;
	
	Object onActivate() {
		if(SecurityUtils.getSubject().isAuthenticated() || SecurityUtils.getSubject().isRemembered()) {
			Link link = pageRender.createPageRenderLink(Home.class);
			return link;
		}
		return null;
	}
}
