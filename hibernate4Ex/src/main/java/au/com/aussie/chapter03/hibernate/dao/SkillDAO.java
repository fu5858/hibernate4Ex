package au.com.aussie.chapter03.hibernate.dao;

import org.hibernate.Session;

import au.com.aussie.chapter03.hibernate.domain.Skill;

public interface SkillDAO {

	/**
	 * A Method to locate a specific Skill instance
	 * @param session
	 * @param name
	 * @return
	 */
	Skill findSkill(Session session, String name);
	
	/**
	 * A Method to save a Skill instance, Given a name
	 * @param session
	 * @param name
	 * @return
	 */
	Skill saveSkill(Session session, String name);
}
