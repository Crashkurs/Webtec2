package tu.dortmund.de.webtec2.pages;

import java.util.List;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import tu.dortmund.de.webtec2.entities.Croak;
import tu.dortmund.de.webtec2.entities.User;
import tu.dortmund.de.webtec2.services.GlobalCtrl;
import tu.dortmund.de.webtec2.services.ProfileCtrl;

public class Profile {
	
	@Property
	private User currentUser;
	
	@Property
	private User profileUser;
	
	@Property
	private List<Croak> croaks;
	
	@Property
	private int countFollower;
	
	@Property
	private boolean isFollower;
	
	@Property
	private boolean isFollowing;
	
	@Property
	private boolean isOwnProfile;

	@Property
	private Croak croak;
	
	@Property
	private User follower;
	
	@Property
	private Zone profile;

    @Inject
    PageRenderLinkSource pageRenderLink;
	
	@Inject
	private ProfileCtrl profileCtrl;
	
	@Inject
	private GlobalCtrl globalCtrl;

	void onActivate(String userName) {
		currentUser = profileCtrl.loadUser();
		profileUser = profileCtrl.loadUser(userName);
		
		if(profileUser != null) {
			croaks = globalCtrl.loadCroaks(profileUser);
			countFollower = profileUser.getFollowers().size();
			isOwnProfile = currentUser != null
							&& profileUser.getName().equals(currentUser.getName()) ;
			isFollower = !(isOwnProfile || currentUser == null)
							&& (globalCtrl.getIndexOfUser(profileUser.getFollowing(), currentUser) != -1
							|| globalCtrl.getIndexOfNote(currentUser, profileUser) != -1);
			isFollowing = !(isOwnProfile || currentUser == null) 
							&& globalCtrl.getIndexOfUser(profileUser.getFollowers(), currentUser) != -1;
		}
	}
	
	@CommitAfter
	Object onActionFromFollowMe(String profileName) {
		profileCtrl.followMe(profileName);
		Link profileLink = pageRenderLink.createPageRenderLinkWithContext(Profile.class, profileName);
		return profileLink;
	}
	
	@CommitAfter
	Object onActionFromFollow(String profileName) {
		profileCtrl.follow(profileName);
		Link profileLink = pageRenderLink.createPageRenderLinkWithContext(Profile.class, profileName);
		return profileLink;
	}
	
	@CommitAfter
	Object onActionFromUnfollow(String profileName) {
		profileCtrl.unfollow(profileName);
		Link profileLink = pageRenderLink.createPageRenderLinkWithContext(Profile.class, profileName);
		return profileLink;
	}
	
	@CommitAfter
	Object onActionFromDeleteUser(String profileName) {
		Link link;
		if(profileCtrl.deleteUser())
			link = pageRenderLink.createPageRenderLink(Register.class);
		else
			link = pageRenderLink.createPageRenderLinkWithContext(Profile.class, profileName);
		return link;
	}
}
