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
			User paul = registerCtrl.createNewUser("Paul", "Paul", "Paul");
			User jan = registerCtrl.createNewUser("Jan", "Jan", "Jan");
			User dieter = registerCtrl.createNewUser("Dieter", "Dieter", "Dieter");
			User mats = registerCtrl.createNewUser("Mats", "Mats", "Mats");
			User lorenzo = registerCtrl.createNewUser("Lorenzo", "Lorenzo", "Lorenzo");
			User dominik = registerCtrl.createNewUser("Dominik", "Dominik", "Dominik");
			User jörn = registerCtrl.createNewUser("Jörn", "Jörn", "Jörn");
			
			admin.addFollower(paul);
			paul.getFollowing().add(admin);
			
			jan.addFollower(admin);
			admin.getFollowing().add(jan);
			
			dieter.addFollower(jan);
			jan.getFollowing().add(dieter);

			jan.addFollower(dieter);
			dieter.getFollowing().add(jan);
			
			jan.addFollower(paul);
			paul.getFollowing().add(jan);
			
			jan.addFollower(mats);
			mats.getFollowing().add(jan);
			
			mats.addFollower(lorenzo);
			lorenzo.getFollowing().add(mats);
			
			mats.addFollower(jörn);
			jörn.getFollowing().add(mats);
			
			mats.addFollower(dominik);
			dominik.getFollowing().add(mats);
			
			lorenzo.addFollower(mats);
			mats.getFollowing().add(lorenzo);
			
			dominik.addFollower(mats);
			mats.getFollowing().add(dominik);
			
			jörn.addFollower(mats);
			mats.getFollowing().add(jörn);
			
			jan.addFollower(lorenzo);
			lorenzo.getFollowing().add(jan);
			
			dominik.addFollower(lorenzo);
			lorenzo.getFollowing().add(dominik);
			
			jörn.addFollower(lorenzo);
			lorenzo.getFollowing().add(jörn);
			
			jörn.addFollower(dominik);
			dominik.getFollowing().add(jörn);
			
			Croak croak1 = new Croak(admin, "Erster Croak vom Admin", new Date());
			Croak croak2 = new Croak(lorenzo, "Hey Leute! Hab jetzt auch Croaky :)", new Date());
			Croak croak3 = new Croak(jörn, "Layout hab ich fertig, wie findet ihrs so?", new Date());
			Croak croak4 = new Croak(dominik, "Schaut mal bei meinem Profil vorbei!", new Date());
			Croak croak5 = new Croak(mats, "Hab heut verschlafen, was habt ihr alle so gemacht?", new Date());
			Croak croak6 = new Croak(paul, "Fahren jetzt zum Froschloch, will sich noch wer anschlie�en?", new Date());
			Croak croak7 = new Croak(dieter, "Froschloch klingt gut, komme mit!", new Date());
			
			Notification note1 = new Notification(admin.getName(), new Date());
			Notification note2 = new Notification(dominik.getName(), new Date());
			
			jan.getNotifications().add(note1);
			jörn.getNotifications().add(note2);
			
			session.persist(admin);
			session.persist(paul);
			session.persist(jan);
			session.persist(dieter);
			session.persist(mats);
			session.persist(lorenzo);
			session.persist(dominik);
			session.persist(jörn);
			
			session.persist(croak1);
			session.persist(croak2);
			session.persist(croak3);
			session.persist(croak4);
			session.persist(croak5);
			session.persist(croak6);
			session.persist(croak7);
			hibernateSessionManager.commit();
		}catch(IllegalArgumentException ex) {
			ex.printStackTrace();
		}
	}
}
