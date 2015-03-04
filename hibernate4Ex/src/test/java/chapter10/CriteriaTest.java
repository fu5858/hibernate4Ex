package chapter10;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import au.com.aussie.chapter09.Product;
import au.com.aussie.chapter09.Software;
import au.com.aussie.chapter09.Supplier;
import au.com.aussie.util.SessionUtil;

public class CriteriaTest {
	Session session;
	Transaction tx;
	
	@BeforeMethod
	public void populateData(){
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		Supplier supplier = new Supplier("Hardware, Inc.");
		supplier.getProducts().add(new Product(supplier, "Optical Wheel Mouse", "Mouse", 5.00));
		supplier.getProducts().add(new Product(supplier, "Trackball Mouse", "Mouse", 22.00));
		supplier.getProducts().add(new Product(supplier, "Samsung SSD", "Harddrive", 152.00));
		supplier.getProducts().add(new Product(supplier, "Sony Camera", "Camera", 259.00));
		supplier.getProducts().add(new Product(supplier, "Canon Camera", "Camera", 399.00));
		supplier.getProducts().add(new Product(supplier, "IPod", "MP3", 189.00));
		supplier.getProducts().add(new Product(supplier, "IPod Mini", "Mp3", 149.00));
		supplier.getProducts().add(new Product(supplier, "Lonovo", "Laptop", 659.00));
		supplier.getProducts().add(new Product(supplier, "HP", "Laptop", 759.00));
		supplier.getProducts().add(new Product(supplier, "Samsung", "Laptop", 1059.00));
		session.save(supplier);
		
		supplier = new Supplier("MegaInc");
		supplier.getProducts().add(new Software(supplier, "SuperDetect", "Antivirus", 14.95, "1.0"));
		supplier.getProducts().add(new Software(supplier, "Wildcat", "Browser", 19.95, "2.2"));
		supplier.getProducts().add(new Software(supplier, "Windows 7", "OS", 122.00, "7.0"));
		supplier.getProducts().add(new Software(supplier, "Windows 8", "OS", 222.00, "8.0"));
		supplier.getProducts().add(new Software(supplier, "DiabloIII", "Game", 60.00, "3.0"));
		supplier.getProducts().add(new Software(supplier, "Total Commander", "Game", 49.00, "1.1"));
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
	public void testLikeCriteria(){
		Criteria cri = session.createCriteria(Product.class);
		cri.add(Restrictions.like("description", "Mou%"));
		List<Product> products = cri.list();
		
		System.out.println();
		System.out.println("##### Result List #####");
		for(Product product : products){
			System.out.println("Product's Name : " + product.getName() + " | Price : $" + product.getPrice());
		}

		System.out.println();
		
		assertEquals(products.size(), 2);
	}
	
	@Test
	public void testPagingResult(){
		Criteria cri = session.createCriteria(Product.class);
		cri.setFirstResult(1);
		cri.setMaxResults(2);
		List<Product> products = cri.list();
		
		System.out.println();
		System.out.println("##### Result List #####");
		for(Product product : products){
			System.out.println("Product's Name : " + product.getName() + " | Price : $" + product.getPrice());
		}
		
		System.out.println();
		
	}

	@Test
	public void testSortingResult(){
		Criteria cri = session.createCriteria(Product.class);
		cri.add(Restrictions.gt("price", 20.00));
		cri.addOrder(Order.desc("price"));
		
		List<Product> products = cri.list();
		
		System.out.println();
		System.out.println("##### Result List #####");
		for(Product product : products){
			System.out.println("Desc : " + product.getDescription() + " | Product's Name : " + product.getName() + " | Price : $" + product.getPrice());
		}
		
		System.out.println();
		
	}
	
	@Test
	public void testQueryByExampleForProduct(){
		Criteria cri = session.createCriteria(Product.class);
		Product exProduct = new Product();
//		exProduct.setName("Samsung");
		exProduct.setDescription("Laptop");
		Example example = Example.create(exProduct);
		example.excludeZeroes();
		cri.add(example);
		
		List<Product> products = cri.list();
		
		System.out.println();
		System.out.println("##### Result List #####");
		for(Product product : products){
			System.out.println("Desc : " + product.getDescription() + " | Product's Name : " + product.getName() + " | Price : $" + product.getPrice());
		}
		
		System.out.println();
		
	}
	
	@Test
	public void testQueryByExampleForSupplier(){
		Criteria cri = session.createCriteria(Supplier.class);
		Supplier supplier = new Supplier();
		supplier.setName("MegaInc");
		cri.add(Example.create(supplier));
		
		List<Supplier> suppliers = cri.list();
		
		System.out.println();
		System.out.println("##### Result List #####");
		for(Supplier suppl : suppliers){
			System.out.println("Name : " + suppl.getName() + " | Product : " + suppl.getProducts());
		}
		
		System.out.println();
		
	}
	
}
