package com.mezhou887.train.client;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mezhou887.train.crawler.*;
import com.mezhou887.train.entity.StationEntity;

public class TrainClient {
	
    private static String executeDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    
	public static void main(String[] args) {
		StationNameCrawler snCrawler = new StationNameCrawler();
		Map<String, StationEntity> stationMap = snCrawler.getAllStationName();
		
		TrainListCrawler trCrawler = new TrainListCrawler(executeDate);
		String trainList = trCrawler.getTrainList();
		
		List<String> trainLines = trCrawler.parseTrainJson(trainList, stationMap);
		System.out.println(trainLines.get(1));
		
	}

}
