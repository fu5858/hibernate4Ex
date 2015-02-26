package hibernate;

import org.testng.annotations.Test;

import au.com.aussie.simple.Person;
import au.com.aussie.simple.Ranking;
import au.com.aussie.simple.Skill;

public class ModelTest {

	@Test
	public void testModelCreation(){
		Person subject = new Person();
		subject.setName("J. C. Smell");
		
		Person observer = new Person();
		observer.setName("Drew Lombardo");
		
		Skill skill = new Skill();
		skill.setName("java");
		
		Ranking ranking = new Ranking();
		ranking.setSubject(subject);
		ranking.setObserver(observer);
		ranking.setSkill(skill);
		ranking.setRanking(8);
		
		System.out.println(ranking);
		
	}
}
