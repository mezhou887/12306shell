package com.mezhou887.train.crawler;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class TicketPriceCrawlerTest {

	@Test
	public void testQueryTicketPrice() {
		String trainNo = "5l0000G21230";;
		String fromStationNo = "01";;
		String toStationNo = "10";
		String seat_types = "OM9";;
		String trainDate = "2017-08-30";		
		TicketPriceCrawler crawler = new TicketPriceCrawler(trainNo, fromStationNo, toStationNo, seat_types, trainDate);
		Map<String, String> result = crawler.queryTicketPrice();
		assertEquals("5l0000G21230", result.get("train_no"));
	}

}
