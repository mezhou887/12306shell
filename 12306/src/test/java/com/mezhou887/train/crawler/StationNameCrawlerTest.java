package com.mezhou887.train.crawler;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import com.mezhou887.train.entity.StationEntity;

public class StationNameCrawlerTest {

	@Test
	public void testGetAllStationName() {
		Map<String, StationEntity> result = new StationNameCrawler().getAllStationName();
		assertEquals("NNZ", result.get("����").getCode_id());
		assertEquals("LZZ", result.get("����").getCode_id());
		assertEquals("SYT", result.get("����").getCode_id());
		assertEquals("IZQ", result.get("������").getCode_id());
		
		assertEquals("bjb", result.get("������").getShortname1());
		assertEquals("������", result.get("������").getName());
		assertEquals("VAP", result.get("������").getCode_id());
		assertEquals("beijingbei", result.get("������").getFullname());
		assertEquals("bjb", result.get("������").getShortname2());
		assertEquals("0", result.get("������").getNo());
		
//		assertEquals("SYT", result.get("��ɽ��").getCode_id());
//		assertEquals("IZQ", result.get("����").getCode_id());
		
	}

}
