package hibernate;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import java.util.Map;

import org.testng.annotations.Test;

import au.com.aussie.hibernate.domain.Person;
import au.com.aussie.hibernate.service.HibernateRankingService;
import au.com.aussie.hibernate.service.RankingService;

public class RankingServiceTest {

	RankingService rankingService = new HibernateRankingService();
	
	@Test
	public void addRanking(){
		rankingService.addRanking("J. C. Smell", "Drew Lombardo", "Mule", 8);
		
		assertEquals(rankingService.getRankingFor("J. C. Smell", "Mule"), 8);
	}
	
	@Test
	public void updateExistingRanking(){
		rankingService.addRanking("Gene Showrama", "Scottball Most", "Ceylon", 6);
		assertEquals(rankingService.getRankingFor("Gene Showrama", "Ceylon"), 6);
		
		rankingService.updateRanking("Gene Showrama", "Scottball Most", "Ceylon", 7);
		assertEquals(rankingService.getRankingFor("Gene Showrama", "Ceylon"), 7);
	}
	
	@Test
	public void updateNonExistingRanking(){
		assertEquals(rankingService.getRankingFor("Gene Showrama", "Ceylon"), 0);
		
		rankingService.updateRanking("Gene Showrama", "Scottball Most", "Ceylon", 7);
		assertEquals(rankingService.getRankingFor("Gene Showrama", "Ceylon"), 7);
	}
	
	@Test
	public void findAllRankingsEmptySet(){
		assertEquals(rankingService.getRankingFor("Nobody", "java"), 0);
		assertEquals(rankingService.getRankingFor("Nobody", "python"), 0);
		
		Map<String, Integer> rankings = rankingService.findRankingsFor("Nobody");
		
		assertEquals(rankings.size(), 0);
	}
	
	@Test
	public void findAllRankings(){
		assertEquals(rankingService.getRankingFor("Nobody", "java"), 0);
		assertEquals(rankingService.getRankingFor("Nobody", "python"), 0);
		rankingService.addRanking("Somebody", "Nobody", "java", 9);
		rankingService.addRanking("Somebody", "Nobody", "java", 7);
		rankingService.addRanking("Somebody", "Nobody", "python", 7);
		rankingService.addRanking("Somebody", "Nobody", "python", 5);
		
		Map<String, Integer> rankings = rankingService.findRankingsFor("Somebody");
		
		assertEquals(rankings.size(), 2);
		assertNotNull(rankings.get("java"));
		assertEquals(rankings.get("java"), new Integer(8));
		assertNotNull(rankings.get("python"));
		assertEquals(rankings.get("python"), new Integer(6));
	}
	
	@Test
	public void findBestForNonexistentSkill(){
		Person person = rankingService.findBestPersonFor("no skill");
		assertNull(person);
	}
	
	@Test
	public void findBestForSkill(){
		rankingService.addRanking("S1", "01", "Sk1", 6);
		rankingService.addRanking("S1", "02", "Sk1", 8);
		rankingService.addRanking("S2", "01", "Sk1", 5);
		rankingService.addRanking("S2", "02", "Sk1", 7);
		rankingService.addRanking("S3", "01", "Sk1", 7);
		rankingService.addRanking("S3", "02", "Sk1", 9);
		rankingService.addRanking("S3", "01", "Sk2", 2);
		
		Person person = rankingService.findBestPersonFor("Sk1");
		assertEquals(person.getName(), "S3");
	}
	
	@Test
	public void removeRanking(){
		rankingService.addRanking("R1", "R2", "RS1", 8);
		assertEquals(rankingService.getRankingFor("R1", "RS1"), 8);
		rankingService.removeRanking("R1", "R2", "RS1");
		assertEquals(rankingService.getRankingFor("R1", "RS1"), 0);
		
		
	}
}
