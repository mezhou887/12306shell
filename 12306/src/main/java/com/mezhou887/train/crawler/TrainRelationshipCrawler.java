package com.mezhou887.train.crawler;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mezhou887.train.BaseCrawler;

/**
 * 车站之间的关系
 * @author Administrator
 *
 */
public class TrainRelationshipCrawler extends BaseCrawler {
	
	private String query_train_relationship = "https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date={0}&leftTicketDTO.from_station={1}&leftTicketDTO.to_station={2}&purpose_codes=ADULT";
	private String trainDate;
	private String fromStation;
	private String toStation;
	
	public TrainRelationshipCrawler(String trainDate, String fromStation, String toStation) {
		super();
		this.trainDate = trainDate;
		this.fromStation = fromStation;
		this.toStation = toStation;
	}

	public void queryTrainRelationship() {
		String url = MessageFormat.format(query_train_relationship, trainDate, fromStation, toStation);
		try {
			Map<String, Object> map = getHttpResponse(url);
			String content = map.get("content").toString();
			JsonParser parse =new JsonParser();
			JsonObject json = (JsonObject) parse.parse(content);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void saveCSVFile(Object o) {
		
	}

	@Override
	public void saveDatabase() {
		
	}

}
