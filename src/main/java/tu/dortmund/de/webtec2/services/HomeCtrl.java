package tu.dortmund.de.webtec2.services;

import java.util.Date;
import java.util.LinkedList;

import org.apache.tapestry5.hibernate.HibernateSessionManager;
import org.hibernate.Session;

import tu.dortmund.de.webtec2.entities.Croak;
import tu.dortmund.de.webtec2.entities.Notification;
import tu.dortmund.de.webtec2.entities.User;

public class HomeCtrl {

	private HibernateSessionManager hibernateSessionManager;
	private GlobalCtrl globalctrl;

	public HomeCtrl(HibernateSessionManager hibernateSessionManager,
			GlobalCtrl globalctrl) {
		this.globalctrl = globalctrl;
		this.hibernateSessionManager = hibernateSessionManager;
	}

	/**
	 * Returns a new croak containing the given text
	 * 
	 * @param text the text from the croak
	 * @return a new croak with the given text
	 */
	public Croak createCroak(String text) {
		Session session = this.hibernateSessionManager.getSession();
		Croak c = new Croak(globalctrl.getCurrentUser(), text, new Date());
		session.persist(c);
		hibernateSessionManager.commit();
		return c;
	}

	/**
	 * Returns a list of all croaks by followed users
	 * 
	 * @return list of croaks by followed users
	 */
	public LinkedList<Croak> loadFollowedCroaks() {
		LinkedList<User> user = new LinkedList<User>();
		LinkedList<Croak> result = new LinkedList<Croak>();

		user.addAll(globalctrl.getCurrentUser().getFollowing());
		try {
			for (int i = 0; i < user.size(); i++) 
				result.addAll(globalctrl.loadCroaks(user.get(i)));		
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return result;
	}
	
	public LinkedList<Notification> loadNotifications() {
		LinkedList<Notification> result = new LinkedList<Notification>();
		result.addAll(globalctrl.getCurrentUser().getNotifications());
		return result;
	}
	
	/**
	 * only for testing
	 * @return returns all selfmade croaks
	 */
	public LinkedList<Croak> loadOnwCroaks() {
		LinkedList<Croak> result = new LinkedList<Croak>();

		try {
			result.addAll(globalctrl.loadCroaks());	
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return result;
	}
}
