package com.mezhou887.train.crawler;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class TrainRelationshipCrawlerTest {

	@Test
	public void test() {
		String trainDate = "2017-08-30";	
		String fromStation = "SHH";
		String toStation = "TJP";
		TrainRelationshipCrawler crawler = new TrainRelationshipCrawler(trainDate, fromStation, toStation);
		List<String> result = crawler.queryTrainRelationship();
		assertEquals(36, result.size());
	}

}
