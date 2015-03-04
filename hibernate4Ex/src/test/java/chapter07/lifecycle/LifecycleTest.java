package chapter07.lifecycle;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.Test;

import au.com.aussie.chapter07.lifecyle.LifecycleThing;
import au.com.aussie.util.JPASessionUtil;

public class LifecycleTest {

	@Test
	public void testLifeCycle(){
		Integer id;
		Session session = JPASessionUtil.getSession("utiljpa");
		Transaction tx = session.beginTransaction();

		LifecycleThing thing1 = new LifecycleThing();
		thing1.setName("Thing 1");
		
		session.save(thing1);
		
		id = thing1.getId();

		tx.commit();
		session.close();
		
		session = JPASessionUtil.getSession("utiljpa");
		tx = session.beginTransaction();
		
		LifecycleThing thing2 = (LifecycleThing)session.byId(LifecycleThing.class).load(-1);
		
		assertNull(thing2);
		
		thing2 = (LifecycleThing)session.byId(LifecycleThing.class).getReference(id);
		
		assertNotNull(thing2);
		assertEquals(thing1, thing2);
		
		thing2.setName("Thing 2");
		
		
		tx.commit();
		session.close();

		session = JPASessionUtil.getSession("utiljpa");
		tx = session.beginTransaction();
		
		LifecycleThing thing3 = (LifecycleThing)session.byId(LifecycleThing.class).getReference(id);
		
		assertNotNull(thing3);
		assertEquals(thing2, thing3);
		
		session.delete(thing3);
		
		tx.commit();
		session.close();
		
		System.out.println(LifecycleThing.lifecycleCalls.nextClearBit(0));
		
		assertEquals(LifecycleThing.lifecycleCalls.nextClearBit(0), 7);
		
	}
}
