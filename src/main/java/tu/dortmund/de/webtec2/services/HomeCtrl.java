package tu.dortmund.de.webtec2.services;

import java.util.Date;
import java.util.LinkedList;

import org.apache.shiro.authc.AuthenticationException;
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
	 * Returns a new croak containing the given text and 
	 * persists it to the current session
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
	
	/**
	 * Returns a list of all selfmade croaks
	 * 
	 * @return returns all selfmade croaks
	 */
	public LinkedList<Croak> loadOwnCroaks() {
		LinkedList<Croak> result = new LinkedList<Croak>();

		try {
			result.addAll(globalctrl.loadCroaks());	
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return result;
	}
	
	/**
	 * Deletes the notification the logged in user received from the given user.
	 * 
	 * @param userName
	 * 			name of the sender of the notification
	 * @return true if the notification was deleted, false if the user doesn't exist, there was no notification or the user wasn't logged in
	 */
	public boolean deleteNote(String userName) {
		try {
			User currentUser = globalctrl.getCurrentUser();
			User fromUser = globalctrl.findUserByName(userName);
			if(fromUser != null) {
				int index = globalctrl.getIndexOfNote(fromUser, currentUser);
				if(index != -1) {
					currentUser.getNotifications().remove(index);
					hibernateSessionManager.getSession().merge(currentUser);
					return true;
				}
			}
		} catch(AuthenticationException e) {}
		return false;
	}
}
