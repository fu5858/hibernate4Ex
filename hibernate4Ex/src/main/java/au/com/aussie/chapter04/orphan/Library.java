package au.com.aussie.chapter04.orphan;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Library {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	
	@Column
	String name;
	
	@OneToMany(orphanRemoval=true, mappedBy="library")
	List<Book> books = new ArrayList<Book>();
	
	public Library(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	@Override
	public String toString() {
		return "Library [id=" + getId() + ", name=" + getName() + ", books=" + getBooks() + "]";
	}
	
}
