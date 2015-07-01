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
	
	public User loadUser(String name){
		return globalCtrl.findUserByName(name);
	}
	
	public User loadUser(){
		try {
			return globalCtrl.getCurrentUser();
		} catch (AuthenticationException ex){
			return null;
		}
	}
	
	public List<Croak> loadCroaks(User user){
		return globalCtrl.loadCroaks(user);
	}
	
	public List<User> loadFollowers(User user){
		return globalCtrl.loadFollower(user);
	}
	
	public boolean followMe(String userName){
		Session session = hibernateSessionManager.getSession();
		User fromUser = globalCtrl.getCurrentUser();
		User toUser = globalCtrl.findUserByName(userName);
		
		List<User> fromUserFollowers = globalCtrl.loadFollower(fromUser);
		//If the toUser already follows fromUser, no notification necessary
		if(containsUser(fromUserFollowers, toUser))
			return false;
		
		//If toUser already has a notification from fromUser, don't create another one
		List<Notification> toUserNotes = globalCtrl.getNotifications(toUser);
		for(Notification note: toUserNotes){
			if(note.getFromUser().getName().equals(fromUser.getName()))
				return false;
		}

		Notification note = new Notification(fromUser,
								    toUser,
								    new Date());
		session.persist(note);
		return true;
	}
	
	public boolean follow(String userName){
		Session session = hibernateSessionManager.getSession();
		User fromUser = globalCtrl.getCurrentUser();
		User toUser = globalCtrl.findUserByName(userName);
		List<User> toUserFollowers = toUser.getFollowers();
		if(containsUser(toUserFollowers, fromUser))
			return false;
		toUser.getFollowers().add(fromUser);
		fromUser.getFollowing().add(toUser);
		List<Notification> notes = globalCtrl.getNotifications();
		for(Notification note: notes){
			if(note.getFromUser().getName().equals(fromUser.getName())) {
				session.delete(note);
			}
		}
		session.update(toUser);
		session.update(fromUser);
		return true;
	}
	
	public boolean containsUser(List<User> users, User user){
		for(User u: users){
			if(u.getName().equals(user.getName())){
				return true;
			}
		}
		return false;
	}
	
	public boolean notesContainUser(User fromUser, User toUser){
		List<Notification> notes = globalCtrl.getNotifications(toUser);
		for(Notification note: notes){
			if(note.getToUser().getName().equals(toUser.getName()))
				return true;
		}
		return false;
	}
}
