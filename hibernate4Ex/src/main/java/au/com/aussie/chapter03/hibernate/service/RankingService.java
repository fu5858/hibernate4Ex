package au.com.aussie.chapter03.hibernate.service;

import java.util.Map;

import au.com.aussie.chapter03.hibernate.domain.Person;

public interface RankingService {

	int getRankingFor(String subject, String skill);
	void addRanking(String subject, String observer, String skill, int ranking);
	void updateRanking(String subjct, String observer, String skill, int ranking);
	void removeRanking(String subject, String observer, String skill);
	Map<String, Integer> findRankingsFor(String subject);
	Person findBestPersonFor(String skill);
	Person findPerson(String subject);
}
