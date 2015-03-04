package chapter07.validated;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.fail;

import javax.validation.ConstraintViolationException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.Test;

import au.com.aussie.chapter07.validated.ValidatedSimplePerson;
import au.com.aussie.util.SessionUtil;

public class ValidatedTest {

	@Test
	public void createValidPerson(){
		persist(ValidatedSimplePerson.builder()
				.age(15)
				.fname("Johnny")
				.lname("McYoungster")
				.build()
				);
	}
	
	@Test(expectedExceptions=ConstraintViolationException.class)
	public void createValidUnderagePerson(){
		persist(ValidatedSimplePerson.builder()
				.age(12)
				.fname("Johnny")
				.lname("McYoungster")
				.build()
				);
		fail("Should have failed validation");
	}
	
	@Test(expectedExceptions=ConstraintViolationException.class)
	public void createValidPoorFNamePerson(){
		persist(ValidatedSimplePerson.builder()
				.age(14)
				.fname("J")
				.lname("McYoungster")
				.build()
				);
		fail("Should have failed validation");
	}
	
	@Test(expectedExceptions=ConstraintViolationException.class)
	public void createValidNoFNamePerson(){
		persist(ValidatedSimplePerson.builder()
				.age(14)
				.lname("McYoungster")
				.build()
				);
		fail("Should have failed validation");
	}
	
	private ValidatedSimplePerson persist(ValidatedSimplePerson person){
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		session.persist(person);
		tx.commit();
		session.close();
		
		return person;
	}
}
