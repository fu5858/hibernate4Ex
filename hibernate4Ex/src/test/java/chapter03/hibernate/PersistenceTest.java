package chapter03.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import au.com.aussie.chapter03.hibernate.domain.MessageCh03;

public class PersistenceTest {

	SessionFactory factory;
	
	@BeforeSuite
	public void setUp(){
		Configuration configuration = new Configuration();
		configuration.configure();
		
		ServiceRegistryBuilder builder = new ServiceRegistryBuilder();
		builder.applySettings(configuration.getProperties());
		
		ServiceRegistry registry = builder.buildServiceRegistry();
		factory = configuration.buildSessionFactory(registry);
	}
	
	@Test
	public void saveMessage(){
		MessageCh03 message = new MessageCh03("Hello, world!");
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(message);
		tx.commit();
		session.close();
	}
	
	@Test(dependsOnMethods="saveMessage")
	public void readMessage(){
		Session session = factory.openSession();
		
		@SuppressWarnings("unchecked")
		List<MessageCh03> list = (List<MessageCh03>)session.createQuery(" from Message").list();
		
		if(list.size() > 1){
			Assert.fail("Message configuration in error; "
					+ "table should contain only one."
					+ " Set ddl to drop-create."); 
		}
		
		if(list.size() == 0){
			Assert.fail("Read of initial message failed; "
					+ "check saveMessage() for errors."
					+ " How did this test run?");
		}
		
		for(MessageCh03 message : list){
			System.out.println(message);
		}
		
		session.close();
	}
}
