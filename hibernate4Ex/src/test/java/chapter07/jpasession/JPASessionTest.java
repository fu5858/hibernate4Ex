package chapter07.jpasession;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.fail;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.Test;

import au.com.aussie.chapter07.lombok.Thing;
import au.com.aussie.util.JPASessionUtil;

public class JPASessionTest {

	@Test
	public void getEntityManager(){
		EntityManager em = JPASessionUtil.getEntityManager("utiljpa");
		em.close();
	}
	
	@Test(expectedExceptions={javax.persistence.PersistenceException.class})
	public void nonexistentEntityManagerName(){
		JPASessionUtil.getEntityManager("nonexistent");
		fail("We shouldn't be able to acquire an EntityManager here");
	}
	
	@Test
	public void getSession(){
		Session session = JPASessionUtil.getSession("utiljpa");
		session.close();
	}
	
	@Test(expectedExceptions={javax.persistence.PersistenceException.class})
	public void nonexistentSession(){
		JPASessionUtil.getSession("nonexistent");
		fail("We shouldn't be able to acquire an Session here");
		
	}
	
	@Test
	public void testEntityManager(){
		EntityManager em = JPASessionUtil.getEntityManager("utiljpa");
		em.getTransaction().begin();
		Thing t = new Thing();
		t.setName("Thing 1");
		
		em.persist(t);
		em.getTransaction().commit();
		em.close();
		
		em = JPASessionUtil.getEntityManager("utiljpa");
		em.getTransaction().begin();
		
		Query query = em.createQuery(" from Thing t where t.name=:name");
		query.setParameter("name", "Thing 1");
		
		Thing result = (Thing)query.getSingleResult();
		
		assertNotNull(result);
		assertEquals(result, t);
		
		em.remove(result);
		em.getTransaction().commit();
		em.close();
		
	}
	
	@Test
	public void testSession(){
		Session session = JPASessionUtil.getSession("utiljpa");
		Transaction tx = session.beginTransaction();
		Thing t = new Thing();
		t.setName("Thing 2");
		session.persist(t);
		tx.commit();
		session.close();
		
		session = JPASessionUtil.getSession("utiljpa");
		tx = session.beginTransaction();
		
		org.hibernate.Query query = session.createQuery(" from Thing t where t.name=:name");
		query.setParameter("name", "Thing 2");
		
		Thing result = (Thing)query.uniqueResult();
		
		assertNotNull(result);
		assertEquals(result, t);
		
		session.delete(result);
		tx.commit();
		session.close();
	}
}
