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

	void onActivate(String userName) {
		currentUser = profileCtrl.loadUser();
		profileUser = profileCtrl.loadUser(userName);
		
		if(profileUser != null) {
			croaks = profileCtrl.loadCroaks(profileUser);
			countFollower = profileUser.getFollowers().size();
			isOwnProfile = profileUser.getName().equals(currentUser.getName())
							&& currentUser != null;
			isFollower = (profileCtrl.getIndexOfUser(profileUser.getFollowing(), currentUser) != -1
							|| profileCtrl.getIndexOfNote(currentUser, profileUser) != -1)
							&& !(isOwnProfile || currentUser == null);
			isFollowing = profileCtrl.getIndexOfUser(profileUser.getFollowers(), currentUser) != -1
							&& !(isOwnProfile || currentUser == null);
		}
	}
	
	@CommitAfter
	Object onActionFromFollowMe(String userName) {
		profileCtrl.followMe(userName);
		Link profileLink = pageRenderLink.createPageRenderLinkWithContext(Profile.class, userName);
		return profileLink;
	}
	
	@CommitAfter
	Object onActionFromFollow(String userName) {
		profileCtrl.follow(userName);
		Link profileLink = pageRenderLink.createPageRenderLinkWithContext(Profile.class, userName);
		return profileLink;
	}
	
	@CommitAfter
	Object onActionFromUnfollow(String userName) {
		profileCtrl.unfollow(userName);
		Link profileLink = pageRenderLink.createPageRenderLinkWithContext(Profile.class, userName);
		return profileLink;
	}

}
