package chapter06.naturalid;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.Test;

import au.com.aussie.chapter06.naturalid.Employee;
import au.com.aussie.chapter06.naturalid.SimpleNaturalIDEmployee;
import au.com.aussie.util.SessionUtil;

public class NaturalIDTest {

	@Test
	public void testSimpleNaturalID(){
		Integer id = createSimpleEmployee("Sorhed", 5401).getId();
		
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		SimpleNaturalIDEmployee employee = (SimpleNaturalIDEmployee)session
				.byId(SimpleNaturalIDEmployee.class)
				.load(id);

		assertNotNull(employee);
		
		SimpleNaturalIDEmployee badgedEmployee = (SimpleNaturalIDEmployee)session
				.bySimpleNaturalId(SimpleNaturalIDEmployee.class)
				.load(5401);
		
		assertEquals(badgedEmployee, employee);
		
		tx.commit();
		session.close();
		
	}

	private SimpleNaturalIDEmployee createSimpleEmployee(String name, int badge) {
		SimpleNaturalIDEmployee employee = new SimpleNaturalIDEmployee();
		employee.setName(name);
		employee.setBadge(badge);
		
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		session.save(employee);
		
		tx.commit();
		session.close();
		
		
		return employee;
	}
	
	@Test
	public void testLoadByNaturalID(){
		Employee initial = createEmployee("Arrowroot", 11, 291);
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		Employee arrowrrot = (Employee)session.byNaturalId(Employee.class)
							.using("section", 11)
							.using("department", 291)
							.load();
		
		assertNotNull(arrowrrot);
		assertEquals(initial, arrowrrot);
		
		tx.commit();
		session.close();
	}

	@Test
	public void testGetByNaturalID(){
		Employee initial = createEmployee("Eorwax", 11, 292);
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		Employee erowax = (Employee)session.byNaturalId(Employee.class)
				.using("section", 11)
				.using("department", 292)
				.getReference();
		
		assertEquals(initial, erowax);
		
		tx.commit();
		session.close();
	}
	
	private Employee createEmployee(String name, int section, int dept) {
		Employee employee = new Employee();
		employee.setName(name);
		employee.setSection(section);
		employee.setDepartment(dept);
		
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		session.save(employee);
		
		tx.commit();
		session.close();
		
		return employee;
	}
}
