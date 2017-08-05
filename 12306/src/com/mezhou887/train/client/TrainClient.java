package com.mezhou887.train.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mezhou887.train.crawler.*;
import com.mezhou887.train.entity.StationEntity;
import com.mezhou887.train.entity.TrainEntity;
import com.mezhou887.train.entity.TrainNoEntity;

public class TrainClient {
	
	public static void main(String[] args) {
		StationNameCrawler snCrawler = new StationNameCrawler();
		Map<String, StationEntity> stationMap = snCrawler.getAllStationName();
		snCrawler.saveCSVFile(stationMap);
		
		TrainListCrawler trCrawler = new TrainListCrawler();
		String trainList = trCrawler.getTrainList();
		
		List<TrainEntity> trainEntitys = trCrawler.parseTrainJson(trainList, stationMap);
		trCrawler.saveCSVFile(trainEntitys);
		
		List<TrainNoEntity> trainNoList = new ArrayList<TrainNoEntity>();
		for(TrainEntity entity: trainEntitys){
			QueryTrainNoCrawler qtCrawler = new QueryTrainNoCrawler(entity);
			List<TrainNoEntity> list = qtCrawler.queryByTrainNo();
			if(list != null) {
				trainNoList.addAll(list);				
			}
		}
		new QueryTrainNoCrawler(null).saveCSVFile(trainNoList);
		
		
	}

}
