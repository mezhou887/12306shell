package com.mezhou887.train.crawler;

import com.mezhou887.train.BaseCrawler;

/**
 * 车站之间的关系
 * @author Administrator
 *
 */
public class TrainRelationshipCrawler extends BaseCrawler {
	
	private String query_train_relationship = "https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date={0}&leftTicketDTO.from_station={1}&leftTicketDTO.to_station={2}&purpose_codes=ADULT";

	@Override
	public void saveCSVFile(Object o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveDatabase() {
		// TODO Auto-generated method stub
		
	}

}
