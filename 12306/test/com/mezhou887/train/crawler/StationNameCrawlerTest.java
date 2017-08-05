package com.mezhou887.train.crawler;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import com.mezhou887.train.entity.StationEntity;

public class StationNameCrawlerTest {

	@Test
	public void testGetAllStationName() {
		Map<String, StationEntity> result = new StationNameCrawler().getAllStationName();
		assertEquals("NNZ", result.get("南宁").getCode_id());
		assertEquals("LZZ", result.get("柳州").getCode_id());
		assertEquals("SYT", result.get("沈阳").getCode_id());
		assertEquals("IZQ", result.get("广州南").getCode_id());
		
		assertEquals("bjb", result.get("北京北").getShortname1());
		assertEquals("北京北", result.get("北京北").getName());
		assertEquals("VAP", result.get("北京北").getCode_id());
		assertEquals("beijingbei", result.get("北京北").getFullname());
		assertEquals("bjb", result.get("北京北").getShortname2());
		assertEquals("0", result.get("北京北").getNo());
		
//		assertEquals("SYT", result.get("佛山西").getCode_id());
//		assertEquals("IZQ", result.get("车墩").getCode_id());
		
	}

}
