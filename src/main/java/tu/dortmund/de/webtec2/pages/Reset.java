package tu.dortmund.de.webtec2.pages;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.tapestry5.hibernate.HibernateSessionManager;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.Session;

import tu.dortmund.de.webtec2.entities.Croak;
import tu.dortmund.de.webtec2.entities.Notification;
import tu.dortmund.de.webtec2.entities.User;
import tu.dortmund.de.webtec2.services.GlobalCtrl;
import tu.dortmund.de.webtec2.services.RegisterCtrl;
import tu.dortmund.de.webtec2.services.TestService;

public class Reset {
	
	@Inject
	private HibernateSessionManager hibernateSessionManager;
	
	@Inject
	private RegisterCtrl registerCtrl;
	
	public Reset(HibernateSessionManager hibernateSessionManager) {
		this.hibernateSessionManager = hibernateSessionManager;
	}
	
	void onActivate(){
		Session session = hibernateSessionManager.getSession();
		List<User> users = session.createCriteria(User.class).list();
		List<Croak> croaks = session.createCriteria(Croak.class).list();
		List<Notification> notes = session.createCriteria(Notification.class).list();
		for(User user: users) {
			session.delete(user);
		}
		for(Croak croak: croaks) {
			session.delete(croak);
		}
		for(Notification note: notes) {
			session.delete(note);
		}
		SecurityUtils.getSubject().logout();
		TestService ts = new TestService(registerCtrl, hibernateSessionManager);
	}
}
