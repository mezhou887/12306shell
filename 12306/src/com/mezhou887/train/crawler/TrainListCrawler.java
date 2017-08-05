package com.mezhou887.train.crawler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mezhou887.train.BaseCrawler;
import com.mezhou887.train.entity.StationEntity;
import com.mezhou887.train.entity.TrainEntity;
import com.mezhou887.train.util.CrawlerUtils;

public class TrainListCrawler extends BaseCrawler {

	private String train_list_url = "https://kyfw.12306.cn/otn/resources/js/query/train_list.js";
	
	
    public TrainListCrawler() {
		super();
    }

    /**
     * 获取所有的车次内容
     */
	public String getTrainList() {
		try {
			Map<String, Object> map = getHttpResponse(train_list_url);
			String content = map.get("content").toString().substring(16);
			return content;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 解析所有车次内容
	 */
	public List<TrainEntity> parseTrainJson(String content, Map<String, StationEntity> stationMap) {
		List<TrainEntity> results = new ArrayList<TrainEntity>();
		JsonParser parse =new JsonParser();
		JsonObject json = (JsonObject) parse.parse(content);
		
		for(Entry<String,JsonElement> entry: json.entrySet()) { //共31天
			String trainDate = entry.getKey();  
			JsonObject typesObj = entry.getValue().getAsJsonObject();
			for(Entry<String,JsonElement> typeObj: typesObj.entrySet()) { // D T G C O K Z
				JsonArray array = typeObj.getValue().getAsJsonArray();
				for(JsonElement element: array) {
					JsonObject trainObj = element.getAsJsonObject();
					String trainNo = trainObj.get("train_no").getAsString();
					String stationTrainCode = trainObj.get("station_train_code").getAsString();
					String trainCode = stationTrainCode.split("\\(")[0];
					String trainName = stationTrainCode.split("\\(")[1];
					String trainNameFix = trainName.substring(0, trainName.length()-1);
					String startStationName = trainNameFix.split("-")[0];
					String startStationCode = "";
					if(stationMap.containsKey(startStationName)) {
						startStationCode = stationMap.get(startStationName).getCode_id();												
					}
					String endStationName = trainNameFix.split("-")[1];	
					String endStationCode = "";
					if(stationMap.containsKey(endStationName)) {
						endStationCode = stationMap.get(endStationName).getCode_id();						
					}
					TrainEntity entity = new TrainEntity(CrawlerUtils.executeDate, trainDate, trainNo, trainCode, 
									startStationName, startStationCode, endStationName, endStationCode);
					results.add(entity);
				}
			}
		}
		return results;
	}

	@Override
	public void saveCSVFile(Object o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveDatabase() {
		// TODO Auto-generated method stub
		
	}
	
}
