package tu.dortmund.de.webtec2.services;

import org.apache.tapestry5.hibernate.HibernateSessionManager;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import tu.dortmund.de.webtec2.entities.User;

public class RegisterCtrl {
	
	private HibernateSessionManager hibernateSessionManager;

	public RegisterCtrl(HibernateSessionManager hibernateSessionManager) {
		this.hibernateSessionManager = hibernateSessionManager;
	}
	
	/**
	 * Creates a new user with the given name and password.
	 * 
	 * @param userName the name of the new user
	 * @param pw the password the new user should have
	 * @param pwRepeat the repeated password
	 * @throws IllegalArgumentException if the passwords do not match or a user with the given name already exists
	 */
	public void createNewUser(String userName, String pw, String pwRepeat) throws IllegalArgumentException {
		Session session = hibernateSessionManager.getSession();
		if(!pw.equals(pwRepeat)) {
			throw new IllegalArgumentException("passwords do not match");
		}
		
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("name", userName));
		User user = (User) criteria.uniqueResult();
		
		if(user != null) {
			throw new IllegalArgumentException("username already exists");
		}
		
		User newUser = new User(userName, pw);
		session.persist(newUser);
		hibernateSessionManager.commit();
	}
	
	/**
	 * Deletes the user with the given username.
	 * 
	 * @param userName the name of the user
	 * @throws IllegalArgumentException if no user with the given name exists
	 */
	public void deleteUser(String userName) throws IllegalArgumentException {
		Session session = hibernateSessionManager.getSession();
		
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("name", userName));
		User user = (User) criteria.uniqueResult();
		if(user == null) {
			throw new IllegalArgumentException("user with this name does not exist");
		}
		
		session.delete(user);
		hibernateSessionManager.commit();
	}
}
