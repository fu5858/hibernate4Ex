package chapter04.general;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.Test;

import au.com.aussie.chapter04.model.SimpleObject;
import au.com.aussie.util.SessionUtil;

public class PersistingEntityesTest {

	@Test
	public void testSaveLoad(){
		Long id = null;
		
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		SimpleObject obj = new SimpleObject();
		obj.setKey("s1");
		obj.setValue(10L);
		
		session.save(obj);
		
		assertNotNull(obj);
		
		id = obj.getId();
		
		tx.commit();
		session.close();
	}
	
	@Test
	public void testSaveLoad2(){
		Long id = null;
		
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		SimpleObject obj = new SimpleObject();
		obj.setKey("s1");
		obj.setValue(10L);
		
		session.save(obj);
		
		assertNotNull(obj);
		
		id = obj.getId();
		
		tx.commit();
		session.close();

		session = SessionUtil.getSession();
		tx = session.beginTransaction();
		
		SimpleObject obj2 = (SimpleObject)session.load(SimpleObject.class, id);

		assertEquals(obj2.getKey(), "s1");
		assertNotNull(obj2.getValue());
		assertEquals(obj2.getValue().longValue(), 10L);
		
		SimpleObject obj3 = (SimpleObject)session.load(SimpleObject.class, id);

		assertEquals(obj2, obj3);
		assertEquals(obj, obj2);
		
		assertTrue(obj2 == obj3);
		assertFalse(obj2 == obj);
		
		tx.commit();
		session.close();
		
	}
	
	@Test
	public void testSavingEntitiesTwice(){
		Long id;
		
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		SimpleObject obj = new SimpleObject();
		obj.setKey("osas");
		obj.setValue(10L);
		
		session.save(obj);
		
		assertNotNull(obj);
		
		id = obj.getId();
		
		tx.commit();
		session.close();
		
		session = SessionUtil.getSession();
		tx = session.beginTransaction();
		
		obj.setValue(12L);
		
		session.save(obj);
		
		tx.commit();
		session.close();
		
		assertNotEquals(id, obj.getId());
	}
	
	@Test
	public void testSaveOrUpdateEntity(){
		Long id;
		
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		SimpleObject obj = new SimpleObject();
		obj.setKey("osas");
		obj.setValue(10L);
		
		session.save(obj);
		
		assertNotNull(obj);
		
		id = obj.getId();
		
		tx.commit();
		session.close();
		
		session = SessionUtil.getSession();
		tx = session.beginTransaction();
		
		obj.setValue(12L);
		
		session.saveOrUpdate(obj);
		
		tx.commit();
		session.close();
		
		assertEquals(id, obj.getId());
	}
	
	@Test
	public void testMerge(){
		Long id;
		
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		SimpleObject obj = new SimpleObject();
		obj.setKey("testMerge");
		obj.setValue(1L);
		
		session.save(obj);
		
		assertNotNull(obj);
		
		id = obj.getId();
		
		tx.commit();
		session.close();
		
		SimpleObject so = validateSimpleObject(id, 1L);
		
		so.setValue(2L);
		
		session = SessionUtil.getSession();
		tx = session.beginTransaction();
		
		session.merge(so);
		
		tx.commit();
		session.close();
		
		validateSimpleObject(id, 2L);

	}

	@Test
	public void testRefresh(){
		Long id;
		
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		SimpleObject obj = new SimpleObject();
		obj.setKey("testMerge");
		obj.setValue(1L);
		
		session.save(obj);
		
		assertNotNull(obj);
		
		id = obj.getId();
		
		tx.commit();
		session.close();
		
		SimpleObject so = validateSimpleObject(id, 1L);
		
		so.setValue(2L);
		
		session = SessionUtil.getSession();
		tx = session.beginTransaction();
		
		session.refresh(so);
		
		tx.commit();
		session.close();
		
		validateSimpleObject(id, 1L);
		
	}
	
	private SimpleObject validateSimpleObject(Long id, Long value) {
		Session session;
		Transaction tx;
		
		session = SessionUtil.getSession();
		tx = session.beginTransaction();
		
		SimpleObject object = (SimpleObject)session.load(SimpleObject.class, id);
		
		assertEquals(object.getKey(), "testMerge");
		assertEquals(object.getValue(), value);
		
		tx.commit();
		session.close();
		
		return object;
	}
}
