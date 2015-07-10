package tu.dortmund.de.webtec2.services;

import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.tapestry5.hibernate.HibernateSessionManager;
import org.hibernate.Criteria;
import org.hibernate.Session;

import tu.dortmund.de.webtec2.entities.Croak;
import tu.dortmund.de.webtec2.entities.Notification;
import tu.dortmund.de.webtec2.entities.User;

@SuppressWarnings("unchecked")
public class ProfileCtrl {
	private HibernateSessionManager hibernateSessionManager;
	private GlobalCtrl globalCtrl;
	
	public ProfileCtrl(HibernateSessionManager hibernateSessionManager,
			GlobalCtrl globalctrl){
		this.hibernateSessionManager = hibernateSessionManager;
		this.globalCtrl = globalctrl;
		
	}
	
	/**
	 * Finds the user with the given username by calling findUserByName from GlobalCtrl
	 * 
	 * @param userName
	 * 			name of the user to be loaded
	 * 
	 * @return the requested user or null if he doesn't exist
	 */
	public User loadUser(String userName) {
		return globalCtrl.findUserByName(userName);
	}
	
	/**
	 * Retrieves the currently logged in user
	 * 
	 * @return the user currently logged in or null if he isn't logged in
	 */
	public User loadUser(){
		try {
			return globalCtrl.getCurrentUser();
		} catch (AuthenticationException ex){
			return null;
		}
	}
	
	/**
	 * The given user gets a notification from the logged in user to follow him.
	 * Will return false if the given user already follows the logged in user or already has a notification.
	 * 
	 * @param userName
	 * 			the user to be followed
	 * @return true if following was successful, false otherwise
	 */
	public boolean followMe(String userName) {
		Session session = hibernateSessionManager.getSession();
		User fromUser = globalCtrl.getCurrentUser();
		User toUser = globalCtrl.findUserByName(userName);
		if(fromUser.getName().equals(userName) || toUser == null)
			return false;
		List<User> fromUserFollowers = fromUser.getFollowers();
		//If the toUser already follows fromUser, no notification necessary
		if(globalCtrl.getIndexOfUser(fromUserFollowers, toUser) != -1)
			return false;
		
		//If toUser already has a notification from fromUser, don't create another one
		if(globalCtrl.getIndexOfNote(fromUser, toUser) != -1)
			return false;
		
		Notification note = new Notification(fromUser.getName(), new Date());
		toUser.getNotifications().add(note);
		session.update(toUser);
		session.persist(note);
		return true;
	}
	
	/**
	 * The logged in user will follow the given user
	 * and if the logged in user has a notification from the given user
	 * the notification will be deleted.
	 * Will return false if the logged in user already follows the given user.
	 * 
	 * @param userName
	 * 			the user to be followed
	 * @return true if following was successful, false otherwise
	 */
	public boolean follow(String userName) {
		Session session = hibernateSessionManager.getSession();
		User fromUser = globalCtrl.getCurrentUser();
		User toUser = globalCtrl.findUserByName(userName);
		if(fromUser.getName().equals(userName) || toUser == null)
			return false;
		List<User> toUserFollowers = toUser.getFollowers();
		if(globalCtrl.getIndexOfUser(toUserFollowers, fromUser) != -1)
			return false;
		fromUser.getFollowing().add(toUser);
		toUser.getFollowers().add(fromUser);
		int noteIndex = globalCtrl.getIndexOfNote(toUser, fromUser);
		if(noteIndex != -1) {
			fromUser.getNotifications().remove(noteIndex);
		}
		session.merge(fromUser);
		session.merge(toUser);
		return true;
	}
	
	/**
	 * The logged in user will no longer follow the given user.
	 * Will return false if the logged in user doesn't follow the given user.
	 * 
	 * @param userName
	 * 			the to be unfollowed user
	 * @return true if unfollowing was successful
	 */
	public boolean unfollow(String userName) {
		Session session = hibernateSessionManager.getSession();
		User fromUser = globalCtrl.getCurrentUser();
		User toUser = globalCtrl.findUserByName(userName);
		if(fromUser.getName().equals(userName) || toUser == null)
			return false;
		int fromUserIndex = globalCtrl.getIndexOfUser(toUser.getFollowers(), fromUser);
		int toUserIndex = globalCtrl.getIndexOfUser(fromUser.getFollowing(), toUser);
		if(fromUserIndex != -1 && toUserIndex != -1){
			toUser.getFollowers().remove(fromUserIndex);
			fromUser.getFollowing().remove(toUserIndex);
			session.merge(fromUser);
			session.merge(toUser);
			return true;
		}
		return false;
	}
	
	/**
	 * The logged in user will be deleted and logged out
	 * 
	 * @return false if the user is not logged in, true if deleting and logging out was successful
	 */
	public boolean deleteUser() {
		try{
			Session session = hibernateSessionManager.getSession();
			User currentUser = globalCtrl.getCurrentUser();
			for(Croak croak: globalCtrl.loadCroaks()){
				session.delete(croak);
			}
			for(User follower: currentUser.getFollowers()) {
				List<User> users = follower.getFollowing();
				int index = globalCtrl.getIndexOfUser(users, currentUser);
				users.remove(index);
				session.merge(follower);
			}
			for(User following: currentUser.getFollowing()) {
				List<User> users = following.getFollowers();
				int index = globalCtrl.getIndexOfUser(users, currentUser);
				users.remove(index);
				session.merge(following);
			}
			deleteNotes();
			session.delete(session.merge(currentUser));
			SecurityUtils.getSubject().logout();
			return true;
		} catch(AuthenticationException e) {
			return false;
		}
	}
	
	/**
	 * Deletes all notifications the logged in user has sent.
	 */
	private void deleteNotes() {
		Session session = hibernateSessionManager.getSession();
		Criteria criteria = session.createCriteria(User.class);
		List<User> result = criteria.list();
		User currentUser = globalCtrl.getCurrentUser();
		for(User toUser: result){
			int index = globalCtrl.getIndexOfNote(currentUser, toUser);
			if(index != -1){
				Notification note = toUser.getNotifications().remove(index);
				session.delete(note);
				session.merge(toUser);
			}
		}
	}
}
