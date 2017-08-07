package com.mezhou887.train.crawler;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mezhou887.train.BaseCrawler;

/**
 * 查询车次票价
 * @author Administrator
 *
 */
public class TicketPriceCrawler extends BaseCrawler {

	private String query_ticket_price = "https://kyfw.12306.cn/otn/leftTicket/queryTicketPrice?train_no={0}&from_station_no={1}&to_station_no={2}&seat_types={3}&train_date={4}";
	private String trainNo;
	private String fromStationNo;
	private String toStationNo;
	private String seat_types;
	private String trainDate;
	
	public TicketPriceCrawler(String trainNo, String fromStationNo, String toStationNo, String seat_types,
			String trainDate) {
		super();
		this.trainNo = trainNo;
		this.fromStationNo = fromStationNo;
		this.toStationNo = toStationNo;
		this.seat_types = seat_types;
		this.trainDate = trainDate;
	}

	public Map<String, String> queryTicketPrice() {
		String url = MessageFormat.format(query_ticket_price, trainNo, fromStationNo, toStationNo, seat_types, trainDate);
		Map<String, String> result = new HashMap<String, String>();
		try {
			Map<String, Object> map = getHttpResponse(url);
			String content = map.get("content").toString();
			JsonParser parse =new JsonParser();
			JsonObject json = (JsonObject) parse.parse(content);
			JsonObject priceObj = json.get("data").getAsJsonObject();
			for(Entry<String,JsonElement> typeObj: priceObj.entrySet()) {
				String key = typeObj.getKey();
				String value = null;
				JsonElement element = typeObj.getValue();
				if(element.isJsonArray()){
					if(element.getAsJsonArray().size() >0) {
						value = element.getAsJsonArray().get(0).toString();
					}
				} else {
					value = element.getAsString();
				}
				result.put(key, value);
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
