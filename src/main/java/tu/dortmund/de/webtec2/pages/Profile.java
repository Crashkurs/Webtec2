package tu.dortmund.de.webtec2.pages;

import java.util.List;

import org.apache.tapestry5.annotations.Property;
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
	private Croak croak;
	
	@Property
	private User follower;
	
	@Inject
	private ProfileCtrl profileCtrl;

	void onActivate(String userName) {
		try {
			this.name = userName;
			User user = profileCtrl.loadUser(name);
			croaks = profileCtrl.loadCroaks(user);
			followers = profileCtrl.loadFollowers(user);
			
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
	}
}
