package chapter09;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import au.com.aussie.chapter09.Product;
import au.com.aussie.chapter09.Software;
import au.com.aussie.chapter09.Supplier;
import au.com.aussie.util.SessionUtil;

public class QueryTest {
	
	Session session;
	Transaction tx;
	
	@BeforeMethod
	public void populateData(){
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		Supplier supplier = new Supplier("Hardware, Inc.");
		supplier.getProducts().add(new Product(supplier, "Optical Wheel Mouse", "Mouse", 5.00));
		supplier.getProducts().add(new Product(supplier, "Trackball Mouse", "Mouse", 22.00));
		session.save(supplier);
		
		supplier = new Supplier("Supplier 2");
		supplier.getProducts().add(new Software(supplier, "SuperDetect", "Antivirus", 14.95, "1.0"));
		supplier.getProducts().add(new Software(supplier, "Wildcat", "Browser", 19.95, "2.2"));
		supplier.getProducts().add(new Product(supplier, "AxeGrinder", "Gaming Mouse", 42.00));
		session.save(supplier);
		
		tx.commit();
		session.close();
		
		this.session = SessionUtil.getSession();
		this.tx = this.session.beginTransaction();
	}
	
	@AfterMethod
	public void closeSession(){
		session.createQuery("delete from Product").executeUpdate();
		session.createQuery("Delete from Supplier").executeUpdate();
		
		if(tx.isActive()){
			tx.commit();
		}
		
		if(session.isOpen()){
			session.close();
		}
	}
	
	@Test
	public void testNamedQuery(){
		Query query = session.getNamedQuery("supplier.findAll");
		List<Supplier> suppliers = query.list();
		
		assertEquals(suppliers.size(), 2);
	}

	@Test
	public void testSimpleQuery(){
		Query query = session.createQuery(" from Product");
		query.setComment("This is only a query for product");
		
		List<Product> products = query.list();
		
		assertEquals(products.size(), 5);
	}
}
