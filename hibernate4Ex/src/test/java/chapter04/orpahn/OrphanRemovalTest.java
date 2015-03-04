package chapter04.orpahn;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.Test;

import au.com.aussie.chapter04.orphan.Book;
import au.com.aussie.chapter04.orphan.Library;
import au.com.aussie.util.SessionUtil;

public class OrphanRemovalTest {

	@Test
	public void orphanRemovalTest(){

		Long id = createLibrary();
		
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();

		Library lib = (Library)session.load(Library.class, id);
		assertEquals(lib.getBooks().size(), 3);
		
		lib.getBooks().remove(0);
		assertEquals(lib.getBooks().size(), 2);
		
		tx.commit();
		session.close();
		
		session = SessionUtil.getSession();
		tx = session.beginTransaction();
		
		Library lib2 = (Library)session.load(Library.class, id);
		assertEquals(lib2.getBooks().size(), 2);
		
		Query query = session.createQuery(" from Book b ");
		
		List<Book> books = (List<Book>)query.list(); 
		assertEquals(books.size(), 2);
		
		tx.commit();
		session.close();
	}

	private Long createLibrary() {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		Library library = new Library();
		library.setName("ConcordLib");
		session.save(library);
		
		Book book = new Book();
		book.setLibrary(library);
		book.setTitle("book 1");
		session.save(book);
		library.getBooks().add(book);
		
		book = new Book();
		book.setLibrary(library);
		book.setTitle("book 2");
		session.save(book);
		library.getBooks().add(book);
		
		book = new Book();
		book.setLibrary(library);
		book.setTitle("book 3");
		session.save(book);
		library.getBooks().add(book);
		
		tx.commit();
		session.close();
		
		return library.getId();
	}
}
