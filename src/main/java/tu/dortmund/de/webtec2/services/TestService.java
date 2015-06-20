package tu.dortmund.de.webtec2.services;

import java.util.Date;

import org.apache.tapestry5.hibernate.HibernateSessionManager;

import tu.dortmund.de.webtec2.entities.Croak;
import tu.dortmund.de.webtec2.entities.Notification;
import tu.dortmund.de.webtec2.entities.User;


/**
 * Diese Klasse erstellt einen Account mit Namen "admin" und Passwort "admin", falls noch nicht vorhanden.
 * 
 * @author Mats
 *
 */
public class TestService {

	private RegisterCtrl registerCtrl;
	private  HibernateSessionManager hibernateSessionManager;

	public TestService(RegisterCtrl registerCtrl, HibernateSessionManager hibernateSessionManager) {
		this.registerCtrl = registerCtrl;
		this.hibernateSessionManager = hibernateSessionManager;
		initializeAdminAccount();
	}

	private void initializeAdminAccount() {
		try{
			User admin = registerCtrl.createNewUser("admin", "admin", "admin");
			User test = registerCtrl.createNewUser("test", "test", "test");
			//Test User folgt Admin User
			admin.getFollowers().add(test);
			admin.getFollowing().add(test);
			test.getFollowers().add(admin);
			test.getFollowing().add(admin);
			Croak croak = new Croak(admin, "test croak vom admin");
			Croak croak2 = new Croak(test, "test croak von test");
			Notification note = new Notification(test, admin, new Date().getTime());
			hibernateSessionManager.getSession().update(admin);
			hibernateSessionManager.getSession().update(test);
			hibernateSessionManager.getSession().persist(croak);
			hibernateSessionManager.getSession().persist(croak2);
			hibernateSessionManager.getSession().persist(note);
			hibernateSessionManager.commit();
		}catch(IllegalArgumentException ex) {
			ex.printStackTrace();
		}
	}
}
