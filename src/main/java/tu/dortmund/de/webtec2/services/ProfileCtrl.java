package tu.dortmund.de.webtec2.services;

import java.util.List;

import org.apache.tapestry5.hibernate.HibernateSessionManager;

import tu.dortmund.de.webtec2.entities.Croak;
import tu.dortmund.de.webtec2.entities.User;

public class ProfileCtrl {
	private HibernateSessionManager hibernateSessionManager;
	private GlobalCtrl globalCtrl;
	
	public ProfileCtrl(HibernateSessionManager hibernateSessionManager,
			GlobalCtrl globalctrl){
		this.hibernateSessionManager = hibernateSessionManager;
		this.globalCtrl = globalctrl;
		
	}
	
	public User loadUser(String name){
		return globalCtrl.findUserByName(name);
	}
	
	public List<Croak> loadCroaks(User user){
		return globalCtrl.loadCroaks(user);
	}
	
	public List<User> loadFollowers(User user){
		return globalCtrl.loadFollower(user);
	}
}
