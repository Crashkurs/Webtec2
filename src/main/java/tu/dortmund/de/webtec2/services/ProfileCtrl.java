package tu.dortmund.de.webtec2.services;

import java.util.Date;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.tapestry5.hibernate.HibernateSessionManager;
import org.hibernate.Session;

import tu.dortmund.de.webtec2.entities.Croak;
import tu.dortmund.de.webtec2.entities.Notification;
import tu.dortmund.de.webtec2.entities.User;

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
	
	public List<Croak> loadCroaks(User user) {
		return globalCtrl.loadCroaks(user);
	}
	
	public List<User> loadFollowers(User user) {
		return user.getFollowers();
	}
	
	public boolean followMe(String userName) {
		Session session = hibernateSessionManager.getSession();
		User fromUser = globalCtrl.getCurrentUser();
		User toUser = globalCtrl.findUserByName(userName);
		
		List<User> fromUserFollowers = fromUser.getFollowers();
		//If the toUser already follows fromUser, no notification necessary
		if(getIndexOfUser(fromUserFollowers, toUser) != -1)
			return false;
		
		//If toUser already has a notification from fromUser, don't create another one
		if(getIndexOfNote(fromUser, toUser) != -1)
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
		if(getIndexOfUser(toUserFollowers, fromUser) != -1)
			return false;
		fromUser.getFollowing().add(toUser);
		toUser.getFollowers().add(fromUser);
		int noteIndex = getIndexOfNote(toUser, fromUser);
		if(noteIndex != -1) {
			fromUser.getNotifications().remove(noteIndex);
		}
		session.update(fromUser);
		session.update(toUser);
		return true;
	}
	
	public boolean unfollow(String userName) {
		Session session = hibernateSessionManager.getSession();
		User fromUser = globalCtrl.getCurrentUser();
		User toUser = globalCtrl.findUserByName(userName);
		int fromUserIndex = getIndexOfUser(toUser.getFollowers(), fromUser);
		int toUserIndex = getIndexOfUser(fromUser.getFollowing(), toUser);
		if(fromUserIndex != -1 && toUserIndex != -1){
			toUser.getFollowers().remove(fromUserIndex);
			fromUser.getFollowing().remove(toUserIndex);
			session.merge(fromUser);
			session.merge(toUser);
			return true;
		}
		return false;
	}
	
	public int getIndexOfUser(List<User> users, User user) {
		for(int i=0; i<users.size(); i++){
			if(users.get(i).getName().equals(user.getName())){
				return i;
			}
		}
		return -1;
	}
	
	public int getIndexOfNote(User fromUser, User toUser) {
		List<Notification> notes = toUser.getNotifications();
		for(int i=0; i<notes.size(); i++) {
			if(notes.get(i).getFromUser().equals(fromUser.getName()))
				return i;
		}
		return -1;
	}
}
