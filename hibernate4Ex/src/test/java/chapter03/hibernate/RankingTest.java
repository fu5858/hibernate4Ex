package chapter03.hibernate;


import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertEquals;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mchange.util.AssertException;

import au.com.aussie.chapter03.hibernate.dao.PersonDAO;
import au.com.aussie.chapter03.hibernate.dao.PersonDAOImpl;
import au.com.aussie.chapter03.hibernate.dao.SkillDAO;
import au.com.aussie.chapter03.hibernate.dao.SkillDAOImpl;
import au.com.aussie.chapter03.hibernate.domain.Person;
import au.com.aussie.chapter03.hibernate.domain.Ranking;
import au.com.aussie.chapter03.hibernate.domain.Skill;

public class RankingTest {

	SessionFactory factory;
	
	@BeforeMethod
	public void setUp(){
		Configuration config = new Configuration();
		config.configure();
		
		ServiceRegistryBuilder builder = new ServiceRegistryBuilder();
		builder.applySettings(config.getProperties());
		
		ServiceRegistry registry = builder.buildServiceRegistry();
		factory = config.buildSessionFactory(registry);
	}
	
	@AfterMethod
	public void shutDown(){
		factory.close();
	}

	@Test
	public void testSaveRanking(){
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		
		PersonDAO personDAO = new PersonDAOImpl();
		SkillDAO skillDAO = new SkillDAOImpl();
		
		Person subject = personDAO.savePerson(session, "J. C. Smell");
		Person observer = personDAO.savePerson(session, "Drew Lombardo");
		Skill skill = skillDAO.saveSkill(session, "java");
		
		Ranking ranking = new Ranking();
		ranking.setSubject(subject);
		ranking.setObserver(observer);
		ranking.setSkill(skill);
		ranking.setRanking(8);
		
		System.out.println();
		System.out.println();
		System.out.println(ranking);
		System.out.println();
		System.out.println();
		
		session.save(ranking);
		
		tx.commit();
		session.close();
	}
	
	@Test
	public void testRankings(){
		populateRankingData();
		
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		
		Query query = session.createQuery(" from Ranking r"
				+ " where r.subject.name = :name "
				+ "   and r.skill.name = :skill ");
		query.setParameter("name", "J. C. Smell");
		query.setParameter("skill", "Java");
		
		int sum = 0;
		int count = 0;
		
		System.out.println("#### testRanking Method ####");
		
		for(Ranking ranking : (List<Ranking>)query.list()){
			count++;
			sum += ranking.getRanking();
			System.out.println();
			System.out.println();
			System.out.println("## Ranking ##");
			System.out.println(ranking);
			System.out.println();
			System.out.println();
		}
		
		tx.commit();
		session.close();
		
	}
	
	@Test
	public void changeRangking(){
		populateRankingData();
		
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		
		String selectSQL = " from Ranking r where r.subject.name = :subject and r.observer.name = :observer and r.skill.name = :skill";
		
		Query query = session.createQuery(selectSQL);
		query.setString("subject", "J. C. Smell");
		query.setString("observer", "Gene Showrama");
		query.setString("skill", "java");
		
		Ranking ranking = (Ranking)query.uniqueResult();
		
		assertNotNull(ranking, "Could not find matching ranking.");
		ranking.setRanking(9);
		tx.commit();
		session.close();
		
//		assertEquals(getAverage("J. C. Smell", "java"), 8);
	}
	
	@Test
	public void removeRanking(){
		populateRankingData();
		
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		
		Ranking ranking = findRanking(session, "J. C. Smell", "Gene Showrama", "java");
		
		assertNotNull(ranking, "Ranking not found");
		session.delete(ranking);
		
		tx.commit();
		session.close();
//		assertEquals(getAverage("J. C. Smell", "java"), 7);
	}
	
	
	private Ranking findRanking(Session session, String subject, String observer, String skill) {
		Query query = session.createQuery("");
		query.setParameter("subject", subject);
		query.setParameter("observer", observer);
		query.setParameter("skill", skill);
		
		return (Ranking)query.uniqueResult();
	}

	private int getAverage(String subject, String observer) {
		// TODO Auto-generated method stub
		int avg = 0;
		
		return avg;
	}

	private void populateRankingData(){
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		
		createData(session, "J. C. Smell", "Gene Showrama", "java", 6);
		createData(session, "J. C. Smell", "Scottball Most", "java", 7);
		createData(session, "J. C. Smell", "Drew Lombardo", "java", 8);
		
		tx.commit();
		session.close();
	}

	private void createData(Session session, String subjectName, String observerName, String skillName, int rank) {
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
	
		session.save(ranking);
	}
	
}
