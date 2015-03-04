package au.com.aussie.chapter03.hibernate.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import au.com.aussie.chapter03.hibernate.dao.PersonDAO;
import au.com.aussie.chapter03.hibernate.dao.PersonDAOImpl;
import au.com.aussie.chapter03.hibernate.dao.SkillDAO;
import au.com.aussie.chapter03.hibernate.dao.SkillDAOImpl;
import au.com.aussie.chapter03.hibernate.domain.Person;
import au.com.aussie.chapter03.hibernate.domain.Ranking;
import au.com.aussie.chapter03.hibernate.domain.Skill;
import au.com.aussie.util.SessionUtil;

public class HibernateRankingService implements RankingService {

	@Override
	public int getRankingFor(String subject, String skill) {
		
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		int avg = getRankingFor(session, subject, skill);
		
		tx.commit();
		session.close();
		
		return avg;
	}

	@SuppressWarnings("unchecked")
	private int getRankingFor(Session session, String subject, String skill) {
		Query query = session.createQuery(" from Ranking r where r.subject.name=:subject and r.skill.name=:skill ");
		query.setParameter("subject", subject);
		query.setParameter("skill", skill);
		
		int sum = 0;
		int count = 0;
		
		for(Ranking rank : (List<Ranking>)query.list()){
			count++;
			sum += rank.getRanking();
			
			System.out.println(sum);
		}
		
		return count == 0 ? 0 : sum / count;
	}

	@Override
	public void addRanking(String subjectName, String observerName, String skillName, int rank) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		addRanking(session, subjectName, observerName, skillName, rank);
		
		tx.commit();
		session.clear();
		
	}

	private void addRanking(Session session, String subjectName, String observerName, String skillName, int rank) {
		PersonDAO personDAO = new PersonDAOImpl();
		SkillDAO skillDAO = new SkillDAOImpl();
		
		Person subject = personDAO.savePerson(session, subjectName);
		Person observer = personDAO.savePerson(session, observerName);
		Skill skill = skillDAO.saveSkill(session, skillName);
		
		Ranking ranking = new Ranking();
		ranking.setSubject(subject);
		ranking.setObserver(observer);
		ranking.setSkill(skill);
		ranking.setRanking(rank);
		
		System.out.println();
		System.out.println(ranking);
		System.out.println();
		
		session.save(ranking);
	}

	@Override
	public void updateRanking(String subjectName, String observerName, String skillName, int rank) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		Ranking ranking = findRanking(session, subjectName, observerName, skillName);
		
		if(ranking == null){
			addRanking(subjectName, observerName, skillName, rank);
		}else {
			ranking.setRanking(rank);
		}
		
		tx.commit();
		session.close();
	}

	private Ranking findRanking(Session session, String subjectName, String observerName, String skillName) {
		Query query = session.createQuery(" from Ranking r "
				+ " where r.subject.name=:subject "
				+ "   and r.observer.name=:observer"
				+ "   and r.skill.name=:skill ");
		query.setParameter("subject", subjectName);
		query.setParameter("observer", observerName);
		query.setParameter("skill", skillName);
		
		return (Ranking)query.uniqueResult();
	}

	@Override
	public void removeRanking(String subject, String observer, String skill) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		
        removeRanking(session, subject, observer, skill);
		
		tx.commit();
		session.close();
	}

	private void removeRanking(Session session, String subject, String observer, String skill) {

		Ranking ranking = findRanking(session, subject, observer, skill);
		
		if(ranking != null){
			session.delete(ranking);
		}
	}

	@Override
	public Map<String, Integer> findRankingsFor(String subject) {
		Map<String, Integer> results;
		
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		results = findRankingsFor(session, subject);
		
		tx.commit();
		session.close();
		
		return results;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Integer> findRankingsFor(Session session, String subject) {
		Map<String, Integer> results = new HashMap<String, Integer>();
		
		Query query = session.createQuery(" from Ranking r where r.subject.name=:subject order by r.skill.name ");
		query.setParameter("subject", subject);
		
		List<Ranking> rankings = query.list();
		String lastSkillName = "";
		
		int sum = 0;
		int count = 0;
//		int avg = 0;
		
		for(Ranking rank : rankings){
			if(!lastSkillName.equals(rank.getSkill().getName())){
				sum = 0;
				count = 0;
				lastSkillName = rank.getSkill().getName();
			}
			
			sum += rank.getRanking();
			count++;
//			avg = sum / count;
//
//			System.out.println();
//			System.out.println("Average : " + avg);
//			System.out.println();
//			
//			results.put(lastSkillName, avg);
			results.put(lastSkillName, sum/count);
		}
		
		return results;
	}

	@Override
	public Person findBestPersonFor(String skill) {
		Person person = null;
		
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		person = findBestPersonFor(session, skill);
		
		tx.commit();
		session.close();
		
		return person;
	}

	private Person findBestPersonFor(Session session, String skill) {
		
		Query query = session.createQuery("select r.subject.name, avg(r.ranking) "
				+ " from Ranking r "
				+ " where r.skill.name=:skill "
				+ "group by r.subject.name "
				+ "order by avg(r.ranking) desc ");
		query.setParameter("skill", skill);
		
		List<Object[]> result = query.list();
		
		if(result.size() > 0){
			System.out.println();
			System.out.println();
			System.out.println(findPerson((String)result.get(0)[0]).getName());
			System.out.println();
			System.out.println();
			return findPerson((String)result.get(0)[0]);
		}
		
		return null;
	}

	@Override
	public Person findPerson(String subject) {
		Person person = null;
		
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		person = findPerson(session, subject);
		
		tx.commit();
		session.close();
		
		return person;
	}

	private Person findPerson(Session session, String subject) {
		PersonDAO personDAO = new PersonDAOImpl();
		
		return personDAO.findPerson(session, subject);
	}
	
	

}
