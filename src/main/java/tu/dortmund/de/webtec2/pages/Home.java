package tu.dortmund.de.webtec2.pages;

import java.util.LinkedList;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import tu.dortmund.de.webtec2.entities.Croak;
import tu.dortmund.de.webtec2.entities.Notification;
import tu.dortmund.de.webtec2.services.HomeCtrl;

@RequiresAuthentication
public class Home {

	@Property
	private LinkedList<Croak> croaks;
	
	@Property
	private LinkedList<Croak> followedCroaks;

	@Property
	private LinkedList<Notification> notes;
	
	@Property
	private Notification note;
	
	@Property
	private Croak croak;
	
	@Property
	private Croak followerCroak;

	@Property
	private String croakInput;
	
    @Inject
    PageRenderLinkSource pageRenderLink;

	@Inject
	private HomeCtrl homectrl;

	public void onActivate() {
		try {
			croaks = homectrl.loadOwnCroaks();
			notes = homectrl.loadNotifications();
			followedCroaks = homectrl.loadFollowedCroaks();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
	}
	
	
	Object onSelectedFromPost(String s) {
		if (croaks == null) {
			croaks = new LinkedList<Croak>();
		}
	    if (!s.equals("") && !s.trim().isEmpty() && s.length()<= 140) {
			croaks.add(homectrl.createCroak(s));
		}
		return null;
	}
	
	@CommitAfter
	Object onActionFromDeleteNote(String userName) {
		homectrl.deleteNote(userName);
		Link homeLink = pageRenderLink.createPageRenderLink(Home.class);
		return homeLink;
	}
}
