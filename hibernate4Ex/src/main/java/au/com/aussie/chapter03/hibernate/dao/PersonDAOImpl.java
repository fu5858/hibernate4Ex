package au.com.aussie.chapter03.hibernate.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import au.com.aussie.chapter03.hibernate.domain.Person;

public class PersonDAOImpl implements PersonDAO {

	@Override
	public Person findPerson(Session session, String name) {
		
		Query query = session.createQuery(" from Person p where p.name=:name");
		query.setParameter("name", name);
		Person person = (Person)query.uniqueResult();
		
		return person;
	}

	@Override
	public Person savePerson(Session session, String name) {
		Person person = findPerson(session, name);
		
		if(person == null){
			person = new Person();
			person.setName(name);
			session.save(person);
		}
		
		return person;
	}

}
