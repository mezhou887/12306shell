package com.mezhou887.train.crawler;

import com.mezhou887.train.BaseCrawler;

/**
 * 查询车次票价
 * @author Administrator
 *
 */
public class TicketPriceCrawler extends BaseCrawler {

	private String query_ticket_price = "https://kyfw.12306.cn/otn/leftTicket/queryTicketPrice?train_no={0}&from_station_no={1}&to_station_no={2}&seat_types={3}&train_date={4}";

	@Override
	public void saveCSVFile(Object o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveDatabase() {
		// TODO Auto-generated method stub
		
	}

}
