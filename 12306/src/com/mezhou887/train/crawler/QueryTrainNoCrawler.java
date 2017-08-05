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
import com.mezhou887.train.entity.TrainEntity;
import com.mezhou887.train.entity.TrainNoEntity;
import com.mezhou887.train.util.CRequest;
import com.mezhou887.train.util.CrawlerUtils;

public class QueryTrainNoCrawler extends BaseCrawler {
	
	private String query_train_url = "https://kyfw.12306.cn/otn/czxx/queryByTrainNo?train_no={0}&from_station_telecode={1}&to_station_telecode={2}&depart_date={3}";
	private TrainEntity e;
	
    public QueryTrainNoCrawler(TrainEntity e) {
		super();
		this.e = e;
	}

	/**
     * 获取所有的车次内容
     */
	public List<TrainNoEntity> queryByTrainNo() {
		String url = MessageFormat.format(query_train_url, e.getTrainNo(), e.getStartStationCode(), e.getEndStationCode(), e.getTrainDate());
		List<TrainNoEntity> lines = new ArrayList<TrainNoEntity>();
		try {
			Map<String, Object> map = getHttpResponse(url);
			String content = map.get("content").toString();
			JsonParser parse =new JsonParser();
			JsonObject json = (JsonObject) parse.parse(content);
			JsonElement dataEle = json.get("data").getAsJsonObject().get("data");
			JsonArray array = dataEle.getAsJsonArray();
			for(JsonElement element: array) {
				JsonObject trainObj = element.getAsJsonObject();
				Map<String, String> mapRequest = CRequest.URLRequest(url);
				String startStationName = trimToEmpty(trainObj.get("start_station_name"));
				String arriveTime = trimToEmpty(trainObj.get("arrive_time"));
				String stationTrainCode = trimToEmpty(trainObj.get("station_train_code"));
				String stationName = trimToEmpty(trainObj.get("station_name"));
				String trainClassName = trimToEmpty(trainObj.get("train_class_name"));
				String serviceType = trimToEmpty(trainObj.get("service_type"));
				String startTime = trimToEmpty(trainObj.get("start_time"));
				String stopoverTime = trimToEmpty(trainObj.get("stopover_time"));
				String endStationName = trimToEmpty(trainObj.get("end_station_name"));
				String stationNo = trimToEmpty(trainObj.get("station_no"));
				String isEnabled = trimToEmpty(trainObj.get("isEnabled"));	
				String trainNo = mapRequest.get("train_no");
				String fromStationTelecode = mapRequest.get("from_station_telecode");
				String toStationTelecode = mapRequest.get("to_station_telecode");
				String departDate = mapRequest.get("depart_date");
				
				TrainNoEntity entity = new TrainNoEntity(CrawlerUtils.executeDate, startStationName, arriveTime, stationTrainCode, stationName, trainClassName, serviceType, startTime, 
						stopoverTime, endStationName, stationNo, isEnabled, trainNo, fromStationTelecode, toStationTelecode,departDate);
				lines.add(entity);
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}

	@Override
	public void saveCSVFile(Object o) {
		
	}

	@Override
	public void saveDatabase() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
