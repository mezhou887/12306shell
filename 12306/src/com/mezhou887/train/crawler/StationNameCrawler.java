package com.mezhou887.train.crawler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import com.mezhou887.train.BaseCrawler;
import com.mezhou887.train.entity.StationEntity;

public class StationNameCrawler extends BaseCrawler {
	
    private String station_name_url = "https://kyfw.12306.cn/otn/resources/js/framework/station_name.js";
    
	/**
	 * 获取到全国所有车站的名称
	 * @return
	 */
    public Map<String, StationEntity> getAllStationName() {
		return getStationName(station_name_url,20);
	}    
	
	private Map<String, StationEntity> getStationName(String url,int distinct) {
		Map<String, StationEntity> result = new HashMap<String, StationEntity>();
		try {
			Map<String, Object> map = getHttpResponse(url);
			String content = map.get("content").toString();
			content = content.substring(distinct, content.length()-2);
			String[] list = content.split("@");
			for(String station: list) {
				if(station.length()>0) { // station：bjb|北京北|VAP|beijingbei|bjb|0 
					String[] code = station.split("\\|");
					String shortname1 = code[0];
					String name = code[1];
					String code_id = code[2];
					String fullname = code[3];
					String shortname2 = code[4];
					String no = code[5];
					StationEntity entity = new StationEntity(shortname1, name, code_id, fullname, shortname2, no);
					result.put(code[1], entity);	
				}
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void saveData() {
		// TODO Auto-generated method stub
		
	}	
	
}
