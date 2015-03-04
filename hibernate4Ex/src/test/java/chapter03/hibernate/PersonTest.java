package chapter03.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import au.com.aussie.chapter03.hibernate.domain.Person;

public class PersonTest {

	SessionFactory factory;
	
	@BeforeMethod
	public void setUp(){
		Configuration config = new Configuration();
		config.configure();
		
		ServiceRegistryBuilder builder = new ServiceRegistryBuilder();
		builder.applySettings(config.getProperties());
		
		ServiceRegistry registry = builder.buildServiceRegistry();
		factory = config.buildSessionFactory(registry);
	}
	
	@AfterMethod
	public void shutDown(){
		factory.close();
	}
	
	@Test
	public void testSavePerson(){
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		
		Person person = new Person();
		person.setName("J. C. Smell");
		
		System.out.println(person);
		
		session.save(person);
		
		tx.commit();
		session.close();
	}
}
