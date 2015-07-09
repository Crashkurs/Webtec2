package tu.dortmund.de.webtec2.services;

import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.tapestry5.hibernate.HibernateSessionManager;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

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
	
	public User loadUser(String name) {
		return globalCtrl.findUserByName(name);
	}
	
	public User loadUser(){
		try {
			return globalCtrl.getCurrentUser();
		} catch (AuthenticationException ex){
			return null;
		}
	}
	
	public boolean followMe(String userName) {
		Session session = hibernateSessionManager.getSession();
		User fromUser = globalCtrl.getCurrentUser();
		User toUser = globalCtrl.findUserByName(userName);
		
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
	
	public boolean follow(String userName) {
		Session session = hibernateSessionManager.getSession();
		User fromUser = globalCtrl.getCurrentUser();
		User toUser = globalCtrl.findUserByName(userName);
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
	
	public boolean unfollow(String userName) {
		Session session = hibernateSessionManager.getSession();
		User fromUser = globalCtrl.getCurrentUser();
		User toUser = globalCtrl.findUserByName(userName);
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
