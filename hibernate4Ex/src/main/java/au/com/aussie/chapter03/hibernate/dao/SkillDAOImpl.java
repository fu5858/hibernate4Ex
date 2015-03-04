package au.com.aussie.chapter03.hibernate.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import au.com.aussie.chapter03.hibernate.domain.Skill;

public class SkillDAOImpl implements SkillDAO {

	@Override
	public Skill findSkill(Session session, String name) {
		Query query = session.createQuery(" from Skill s where s.name=:name");
		query.setParameter("name", name);
		
		Skill skill = (Skill)query.uniqueResult();
		
		return skill;
	}

	@Override
	public Skill saveSkill(Session session, String name) {
		Skill skill = findSkill(session, name);
		
		if(skill == null){
			skill = new Skill();
			skill.setName(name);
			session.save(skill);
		}
		
		return skill;
	}

}
