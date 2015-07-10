package tu.dortmund.de.webtec2.services;

import java.util.Collections;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.tapestry5.hibernate.HibernateSessionManager;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import tu.dortmund.de.webtec2.entities.Croak;
import tu.dortmund.de.webtec2.entities.Notification;
import tu.dortmund.de.webtec2.entities.User;

@SuppressWarnings("unchecked")
public class GlobalCtrl {

	private HibernateSessionManager hibernateSessionManager;
	
	private RegisterCtrl registerCtrl;

	public GlobalCtrl(HibernateSessionManager hibernateSessionManager, RegisterCtrl registerCtrl) {
		this.hibernateSessionManager = hibernateSessionManager;
		this.registerCtrl = registerCtrl;
	}
	
	/**
	 * The current user is logged out.
	 * 
	 * @throws AuthenticationException if this functions gets called while no user is logged in.
	 */
	@RequiresAuthentication
	public void logout() {
		SecurityUtils.getSubject().logout();
	}

	/**
	 * Retrieves the followers from the user that is currently logged in.
	 * 
	 * @throws AuthenticationException
	 *             if no user is currently logged in
	 * @return the list of followers for the current user
	 * 
	 */
	public List<User> loadFollower() {
		return loadFollower(getCurrentUser());
	}

	/**
	 * Retrieves the followers from the user identified by the userName.
	 * 
	 * @param userName
	 *            the identifier for the user
	 * @return the list of followers for the user with the given name
	 */
	public List<User> loadFollower(String userName) {

		User user = findUserByName(userName);
		if (user == null)
			throw new IllegalArgumentException("User with name " + userName
					+ " does not exist.");

		return loadFollower(user);

	}

	/**
	 * Retrieves the followers from the given user.
	 * 
	 * @param user
	 *            the given user
	 * @return the followers of the given user
	 */
	public List<User> loadFollower(User user) {
		return user.getFollowers();
	}

	/**
	 * Retrieves the croaks from the user that is currently logged in.
	 * 
	 * @return the croaks from the current user
	 */
	public List<Croak> loadCroaks() {
		return loadCroaks(getCurrentUser());
	}

	/**
	 * Retrieves the croaks from the user with the given username.
	 * 
	 * @param userName
	 *            the name of the user
	 * 
	 * @throws IllegalArgumentException
	 *             if the user does not exist
	 * @return the croaks of the user if exists
	 */
	public List<Croak> loadCroaks(String userName) {
		User user = findUserByName(userName);
		if (user == null)
			throw new IllegalArgumentException("User with name " + userName
					+ " does not exist.");
		return loadCroaks(user);
	}

	/**
	 * Retrieves the croaks from the user.
	 * 
	 * @param user the user the croaks belong to
	 * @return the croaks of the user
	 */
	public List<Croak> loadCroaks(User user) {
		Session session = this.hibernateSessionManager.getSession();
		
		// Create the query
		Criteria criteria = session.createCriteria(Croak.class);

		// Sets the WHERE clause in the query
		criteria.add(Restrictions.eq("user", user));

		// Execute the query and return it
		List<Croak> result = criteria.list();
		Collections.sort(result);
		return result;
	}
	
	/**
	 * Deletes all users and followers.
	 */
	@RequiresRoles("admin")
	public void deleteAllUser() {
		Session session = this.hibernateSessionManager.getSession();
		User currentUser = getCurrentUser();
		for(User user : getAllUser()) {
			
			if(user.getName().equals(currentUser.getName())) {
				for(User follower : user.getFollowers()) {
					follower.getFollowing().remove(user);
				}
				user.getFollowers().clear();
				for(User following : user.getFollowing()) {
					following.getFollowers().remove(user);
				}
				user.getFollowing().clear();
				for(Notification note : user.getNotifications()) {
					session.delete(note);
				}
				user.getNotifications().clear();
				session.update(user);
			}else {
				registerCtrl.deleteUser(user.getName());
			}
		}
	}
	
	/**
	 * Returns all users currently in the database
	 * 
	 * @return the list of all users
	 */
	public List<User> getAllUser() {
		Session session = this.hibernateSessionManager.getSession();
		
		// Create the query
		Criteria criteria = session.createCriteria(User.class);

		// Execute the query and return it
		List<User> result = criteria.list();
		return result;
	}

	/**
	 * Returns the current user if logged in.
	 * 
	 * @return the current user
	 * @throws AuthenticationException if the user is not logged in
	 */
	public User getCurrentUser() {
		User currentUser = (User) SecurityUtils.getSubject().getPrincipal();
		if (currentUser == null) {
			throw new AuthenticationException("User is not logged in.");
		}
		User result = (User) this.hibernateSessionManager.getSession().createCriteria(User.class)
				.add(Restrictions.eq("name", currentUser.getName()))
				.uniqueResult();

		return result;
	}

	/**
	 * Finds the user with the given username in the database and returns the object.
	 * 
	 * @param userName the name of the user
	 * @return the user object for the given name
	 */
	public User findUserByName(String userName) {
		Session session = this.hibernateSessionManager.getSession();
		
		// Create the query
		Criteria criteria = session.createCriteria(User.class);

		// Sets the WHERE clause in the query
		criteria.add(Restrictions.eq("name", userName));

		// Execute the query and return it
		User result = (User) criteria.uniqueResult();
		return result;
	}
	
	/**
	 * Finds all users matching the given regex in the database and returns the list.
	 * 
	 * @param regex the regex which should be matched against the user names.
	 * @return the list of users matching the regex
	 */
	public List<User> findUserByRegex(String regex) {
		Session session = this.hibernateSessionManager.getSession();
		
		// Create the query
		Criteria criteria = session.createCriteria(User.class);

		// Sets the WHERE clause in the query
		criteria.add(Restrictions.ilike("name", regex + "%"));

		// Execute the query and return it
		List<User> result = criteria.list();
		return result;
	}
	
	/**
	 * Returns the index of the user in the list of users or -1 if not found.
	 * 
	 * @param users the list of users
	 * @param user the user which index should be found
	 * @return the index of the user or -1
	 */
	public int getIndexOfUser(List<User> users, User user) {
		for(int i=0; i<users.size(); i++){
			if(users.get(i).getName().equals(user.getName())){
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Returns the index of the notification for the toUser in the
	 * list of notifications of the fromUser or -1 if not found.
	 * 
	 * @param fromUser the user which stores the list of notifications
	 * @param toUser the user which notification should be found
	 * @return the index of the user or -1
	 */
	public int getIndexOfNote(User fromUser, User toUser) {
		List<Notification> notes = toUser.getNotifications();
		for(int i=0; i<notes.size(); i++) {
			if(notes.get(i).getFromUser().equals(fromUser.getName()))
				return i;
		}
		return -1;
	}
}
