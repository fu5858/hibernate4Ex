package au.com.aussie.hibernate.dao;

import org.hibernate.Session;

import au.com.aussie.hibernate.domain.Person;

public interface PersonDAO {

	/**
	 * A Method to locate a specific Person Instance
	 * @param session
	 * @param name
	 * @return
	 */
	Person findPerson(Session session, String name);
	
	/**
	 * A Method to save a Person Instance, Given a name
	 * @param session
	 * @param name
	 * @return
	 */
	Person savePerson(Session session, String name);
}
