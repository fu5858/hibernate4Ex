package chapter04.broken;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertNotNull;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.Test;

import au.com.aussie.chapter04.broken.Email;
import au.com.aussie.chapter04.broken.Message;
import au.com.aussie.util.SessionUtil;

public class MappingBrokenTest {

	@Test
	public void testBrokenInversionCode(){
		
		Long emailID;
		Long msgID;
		
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		Email email = new Email("Broken");
		Message message = new Message("Broken");
		
		email.setMessage(message);
		
		session.save(email);
		session.save(message);
		
		emailID = email.getId();
		msgID = message.getId();
		
		tx.commit();
		session.close();
		
		assertNotNull(email.getMessage());
		assertNull(message.getEmail());
		
		session = SessionUtil.getSession();
		tx = session.beginTransaction();
		email = (Email)session.get(Email.class, emailID);
		System.out.println(email);
		
		message = (Message)session.get(Message.class, msgID);
		System.out.println(message);
		
		tx.commit();
		session.close();
		
		assertNotNull(email.getMessage());
		assertNull(message.getEmail());
	}
	
	@Test
	public void testProperSimpleInversionCode(){
		Long emailID;
		Long msgID;
		
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		Email email = new Email("Proper");
		Message message = new Message("Proper");
		
		email.setMessage(message);
		message.setEmail(email);
		
		session.save(email);
		session.save(message);
		
		emailID = email.getId();
		msgID = message.getId();
		
		tx.commit();
		session.close();
		
		assertNotNull(email.getMessage());
		assertNotNull(message.getEmail());
		
		session = SessionUtil.getSession();
		tx = session.beginTransaction();
		
		System.out.println();
		
		email = (Email)session.get(Email.class, emailID);
		System.out.println(email);
		message = (Message)session.get(Message.class, msgID);
		System.out.println(message);
		System.out.println();
		
		tx.commit();
		session.close();
		
		assertNotNull(email.getMessage());
		assertNotNull(message.getEmail());
	}
}
