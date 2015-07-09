package tu.dortmund.de.webtec2.pages;

import java.util.LinkedList;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import tu.dortmund.de.webtec2.entities.Croak;
import tu.dortmund.de.webtec2.entities.Notification;
import tu.dortmund.de.webtec2.entities.User;
import tu.dortmund.de.webtec2.services.GlobalCtrl;
import tu.dortmund.de.webtec2.services.HomeCtrl;

@RequiresAuthentication
public class Home {

	@Property
	private LinkedList<Croak> croaks;
	
	@Property
	private LinkedList<Croak> followedCroaks;

	@Property
	private List<Notification> notes;
	
	@Property
	private Notification note;
	
	@Property
	private Croak croak;
	
	@Property
	private Croak followerCroak;

	@Property
	private String croakInput;

	@Inject
	private HomeCtrl homectrl;
	
	@Inject
	private GlobalCtrl globalCtrl;
	
    @Inject
    PageRenderLinkSource pageRenderLink;
	
	@Property
	private boolean canDeleteUser = false;

	public void onActivate() {
		try {
			User user = globalCtrl.getCurrentUser();
			croaks = homectrl.loadOwnCroaks();
			notes = user.getNotifications();
			followedCroaks = homectrl.loadFollowedCroaks();
			for(String roles : user.getRoles()) {
				if(roles.equals("admin")) {
					canDeleteUser = true;
				}
			}
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
	}
	
	@RequiresRoles("admin")
	Object onActionFromDeleteAll() {
		globalCtrl.deleteAllUser();
		return Login.class;
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
}
