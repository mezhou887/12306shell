package com.mezhou887.train.crawler;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mezhou887.train.BaseCrawler;
import com.mezhou887.train.entity.TrainEntity;
import com.mezhou887.train.util.CRequest;

public class QueryTrainNoCrawler extends BaseCrawler {
	
	private String query_train_url = "https://kyfw.12306.cn/otn/czxx/queryByTrainNo?train_no={0}&from_station_telecode={1}&to_station_telecode={2}&depart_date={3}";
	private String executeDate;
	private TrainEntity e;
	
    public QueryTrainNoCrawler(TrainEntity e, String executeDate) {
		super();
		this.e = e;
		this.executeDate = executeDate;
	}

	/**
     * 获取所有的车次内容
     */
	public String getTrainNo() {
		try {
			String url = MessageFormat.format(query_train_url, e.getTrainNo(), e.getStartStationCode(), e.getEndStationCode(), e.getTrainDate());
			Map<String, Object> map = getHttpResponse(url);
			String content = map.get("content").toString();
			return content;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}	
	
	
	// 根据车次查询途径站点
	public List<String> queryByTrainNo(String content, String url) throws Exception {
		List<String> lines = new ArrayList<String>();
		JsonParser parse =new JsonParser();
		JsonObject json = (JsonObject) parse.parse(content);
		JsonElement dataEle = json.get("data").getAsJsonObject().get("data");
		JsonArray array = dataEle.getAsJsonArray();
		for(JsonElement element: array) {
			JsonObject trainObj = element.getAsJsonObject();
			String startStationName = trimToEmpty(trainObj.get("start_station_name"));
			String arriveTime = trimToEmpty(trainObj.get("arrive_time"));
			String stationTrainCode = trimToEmpty(trainObj.get("station_train_code"));
			String stationName = trimToEmpty(trainObj.get("station_name"));
			String trainClassName = trimToEmpty(trainObj.get("train_class_name"));
			String serviceType = trimToEmpty(trainObj.get("service_type"));
			String startTime = trimToEmpty(trainObj.get("start_time"));
			String stopoverTime = trimToEmpty(trainObj.get("stopover_time"));
			String end_stationName = trimToEmpty(trainObj.get("end_station_name"));
			String stationNo = trimToEmpty(trainObj.get("station_no"));
			String isEnabled = trimToEmpty(trainObj.get("isEnabled"));	
			Map<String, String> mapRequest = CRequest.URLRequest(url);
			String line = executeDate + "," + url + "," + mapRequest.get("train_no") + "," + mapRequest.get("from_station_telecode") + "," 
					+ mapRequest.get("to_station_telecode") + "," + mapRequest.get("depart_date") + "," + startStationName + "," 
					+ arriveTime + "," + stationTrainCode + "," + stationName + "," + trainClassName + "," + serviceType + "," 
					+ startTime + "," + stopoverTime + "," + end_stationName + "," + stationNo + "," + isEnabled + "\r\n";
			lines.add(line);
		}
		return lines;
	}

	@Override
	public void saveData() {
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
