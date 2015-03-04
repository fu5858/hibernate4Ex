package au.com.aussie.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class SessionUtil {

	private final static SessionUtil instance = new SessionUtil();
	private final SessionFactory factory;
	
	private SessionUtil(){
		Configuration config = new Configuration();
		config.configure();
		
		ServiceRegistryBuilder builder = new ServiceRegistryBuilder();
		builder.applySettings(config.getProperties());
		
		ServiceRegistry registry = builder.buildServiceRegistry();
		factory = config.buildSessionFactory(registry);
	}
	
	public static Session getSession(){
		return getInsatance().factory.openSession();
	}
	
	private static SessionUtil getInsatance(){
		return instance;
	}
}
