package tu.dortmund.de.webtec2.services;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import tu.dortmund.de.webtec2.entities.User;

public class HibernateRealm extends AuthorizingRealm{

	private static Logger logger = Logger.getLogger(HibernateRealm.class);
	
	private Session session;
	
	public static HibernateRealm build(Session session) {
		return new HibernateRealm(session);
	}
	
	public HibernateRealm(Session session){
		super();
		this.session = session;
		logger.info("HibernateRealm constructed.");
		this.setName("Webtec2 HibernateRealm");
		this.setAuthenticationTokenClass(UsernamePasswordToken.class);
	}
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		AuthorizationInfo info = new SimpleAuthorizationInfo();
		if(principals.getPrimaryPrincipal() instanceof User) {
			User user = (User)principals.getPrimaryPrincipal();
			for(String permission : user.getPermissions()) {
				info.getStringPermissions().add(permission);
			}
		}else {
			logger.warn("Primary principal is not of type User - no permissions added.");
		}
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		logger.info("doGetAuthenticationInfo() called.");
		
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;

		String userName = upToken.getUsername();
		
		User user = findUserByName(userName);
		
		if (user == null) {
			
			throw new AuthenticationException("User " + userName + " not found.");
		}
		return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
	}

	private User findUserByName(String userName){
		Criteria criteria = session.createCriteria(User.class);
		criteria = criteria.add(Restrictions.eq("name", userName));
		User user = (User) criteria.uniqueResult();
		return user;
	}
	
}

