package tu.dortmund.de.webtec2.services;

import java.util.Date;

import org.apache.tapestry5.hibernate.HibernateSessionManager;
import org.hibernate.Session;

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
			Session session = hibernateSessionManager.getSession();
			Date time = new Date();
			
			User admin = registerCtrl.createNewUser("admin", "admin", "admin");
			admin.getRoles().add("admin");
			User test = registerCtrl.createNewUser("test", "test", "test");
			User test2 = registerCtrl.createNewUser("test2", "test2", "test2");
			//Test User folgt Admin User und vice versa
			admin.getFollowers().add(test);
			admin.getFollowing().add(test);
			test.getFollowers().add(admin);
			test.getFollowing().add(admin);
			Croak croak = new Croak(admin, "test croak vom admin", new Date(time.getTime()-500));
			Croak croak2 = new Croak(test, "test croak von test", time);
			Notification note = new Notification(test.getName(), time);
			admin.getNotifications().add(note);
			
			session.update(admin);
			session.update(test);
			session.update(test2);
			session.persist(croak);
			session.persist(croak2);
			session.persist(note);
			hibernateSessionManager.commit();
		}catch(IllegalArgumentException ex) {
			ex.printStackTrace();
		}
	}
}
