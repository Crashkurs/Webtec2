package tu.dortmund.de.webtec2.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionProvider {

	private static Session session;
	private static SessionFactory sessionFactory;
	
	/**
	 * Returns singleton session for configured by "hibernate.cfg.xml".
	 * 
	 * @return session
	 */
	public static Session getSession(){
		if(sessionFactory == null){
			Configuration config = new Configuration();
			config.configure("hibernate.cfg.xml");
			StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(config.getProperties());
			sessionFactory = config.buildSessionFactory(builder.build());
		}
		if(session == null){
			session = sessionFactory.openSession();
		}
		return session;
		
	}
}