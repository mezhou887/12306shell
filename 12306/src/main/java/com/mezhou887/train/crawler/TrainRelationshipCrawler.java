package com.mezhou887.train.crawler;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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

	public List<String> queryTrainRelationship() {
		List<String> result = new ArrayList<String>();
		String url = MessageFormat.format(query_train_relationship, trainDate, fromStation, toStation);
		try {
			Map<String, Object> map = getHttpResponse(url);
			String content = map.get("content").toString();
			JsonParser parse =new JsonParser();
			JsonObject json = (JsonObject) parse.parse(content);
			JsonElement dataEle = json.get("data").getAsJsonObject().get("result");
			JsonArray array = dataEle.getAsJsonArray();
			for(JsonElement element: array) { 
				String trainObj = element.getAsString().replace("\\|", ",");
				result.add(trainObj);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public void saveCSVFile(Object o) {
		
	}

	@Override
	public void saveDatabase() {
		
	}

}
