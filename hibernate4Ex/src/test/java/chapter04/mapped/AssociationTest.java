package chapter04.mapped;

import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.Test;

import au.com.aussie.chapter04.mapped.Email;
import au.com.aussie.chapter04.mapped.Message;
import au.com.aussie.util.SessionUtil;

public class AssociationTest {

	@Test
	public void ImpliedRelationship(){
		Long emailID;
		Long messageID;
		
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		Email email = new Email("Inverse Email");
		Message message = new Message("Inverse Message");
		
		message.setEmail(email);
		
		session.save(email);
		session.save(message);
		
		emailID = email.getId();
		messageID = message.getId();
		
		tx.commit();
		session.close();
		
		System.out.println();
		System.out.println("### After 1st Setp ###");
		System.out.println(message);
		System.out.println(email);
		System.out.println();
		
		assertEquals(email.getSubject(), "Inverse Email");
		assertEquals(message.getContent(), "Inverse Message");
		assertNotNull(message.getEmail());
		assertNull(email.getMessage());
		
		session = SessionUtil.getSession();
		tx = session.beginTransaction();
		
		email = (Email)session.get(Email.class, emailID);
		message = (Message)session.get(Message.class, messageID);
		
		System.out.println();
		System.out.println("### After 2nd Setp ###");
		System.out.println(message);
		System.out.println(email);
		System.out.println();
		
		tx.commit();
		session.close();
		
		assertNotNull(email.getMessage());
		assertNotNull(message.getEmail());
	}
}
