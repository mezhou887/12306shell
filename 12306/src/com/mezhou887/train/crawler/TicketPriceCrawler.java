package com.mezhou887.train.crawler;

import com.mezhou887.train.BaseCrawler;

public class TicketPriceCrawler extends BaseCrawler {

	private String query_ticket_price = "https://kyfw.12306.cn/otn/leftTicket/queryTicketPrice?train_no={0}&from_station_no={1}&to_station_no={2}&seat_types={3}&train_date={4}";

	@Override
	public void saveData() {
		// TODO Auto-generated method stub
		
	}

}
