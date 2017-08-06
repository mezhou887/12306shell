package com.mezhou887.train.crawler;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;

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

	public void queryTicketPrice() {
		String url = MessageFormat.format(query_ticket_price, trainNo, fromStationNo, toStationNo, seat_types, trainDate);
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
