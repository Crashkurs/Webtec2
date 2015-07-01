package tu.dortmund.de.webtec2.pages;

import java.util.List;

import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

import tu.dortmund.de.webtec2.entities.Croak;
import tu.dortmund.de.webtec2.entities.User;
import tu.dortmund.de.webtec2.services.ProfileCtrl;

public class Profile {
	
	@Property
	private String name;
	
	@Property
	private List<Croak> croaks;
	
	@Property
	private List<User> followers;
	
	@Property
	private List<User> following;
	
	@Property
	private boolean isFollower;
	
	@Property
	private boolean isFollowing;
	
	@Property
	private boolean isOwnProfile;
	
	@Property
	private boolean loadSuccess = false;

	@Property
	private Croak croak;
	
	@Property
	private User follower;
	
	@Property
	private Zone profile;

    @Inject
    private AlertManager alertManager;
	
	@Inject
	private ProfileCtrl profileCtrl;

	void onActivate(String userName) {
		try {
			this.name = userName;
			User user = profileCtrl.loadUser(name);
			
			if(user != null) {
				User currentUser = profileCtrl.loadUser();
				croaks = profileCtrl.loadCroaks(user);
				followers = user.getFollowers();
				following = user.getFollowing();
				isOwnProfile = currentUser != null
							   && user.getName().equals(currentUser.getName());
				if(isOwnProfile || currentUser == null) {
					isFollower = true;
					isFollowing = true;
				} else {
					isFollower = profileCtrl.containsUser(following, currentUser)
								 || profileCtrl.notesContainUser(currentUser, user);
					isFollowing = profileCtrl.containsUser(followers, currentUser);		  
				}
				loadSuccess = true;
			}
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
	}
	
	@CommitAfter
	Object onActionFromFollowMe(String userName){
		profileCtrl.followMe(userName);
		return profile;
	}
	
	@CommitAfter
	Object onActionFromFollow(String userName){
		profileCtrl.follow(userName);
		return profile;
	}
	

}
