package tu.dortmund.de.webtec2.pages;

import java.util.LinkedList;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import tu.dortmund.de.webtec2.entities.Croak;
import tu.dortmund.de.webtec2.entities.User;
import tu.dortmund.de.webtec2.services.ProfileCtrl;

public class Profile {
	
	@Property
	private String name;
	
	@Property
	private LinkedList<Croak> croaks;
	
	@Property
	private LinkedList<User> followers;
	
	@Property
	private Croak croak;
	
	@Property
	private User follower;
	
	@Inject
	private ProfileCtrl profileCtrl;

	public void onActivate(String userName) {
		try {
			User user = profileCtrl.loadUser(userName);
			name = userName;
			croaks = (LinkedList<Croak>) profileCtrl.loadCroaks(user);
			followers = (LinkedList<User>) profileCtrl.loadFollowers(user);
			
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
	}
}
