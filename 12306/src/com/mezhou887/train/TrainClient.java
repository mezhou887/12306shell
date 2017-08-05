package com.mezhou887.train;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mezhou887.train.util.CrawlerUtils;

public class TrainClient extends BaseCrawler{
	

    private static Date date = new Date();
    private static String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    private static String filetime = new SimpleDateFormat("yyyy_MM_dd_HH").format(date);;
    private static String station_name_url = "https://kyfw.12306.cn/otn/resources/js/framework/station_name.js?station_version=1.9002";
    private static String favorite_name_url = "https://kyfw.12306.cn/otn/resources/js/framework/favorite_name.js";
    private static String train_list_url = "https://kyfw.12306.cn/otn/resources/js/query/train_list.js?scriptVersion=1.0";
    private static String query_train_url = "https://kyfw.12306.cn/otn/czxx/queryByTrainNo?train_no={0}&from_station_telecode={1}&to_station_telecode={2}&depart_date={3}";
    private static String query_train_relationship = "https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date={0}&leftTicketDTO.from_station={1}&leftTicketDTO.to_station={2}&purpose_codes=ADULT";
    private static String query_ticket_price= "https://kyfw.12306.cn/otn/leftTicket/queryTicketPrice?train_no={0}&from_station_no={1}&to_station_no={2}&seat_types={3}&train_date={4}";
    
   
	
    // 获取所有的车次内容
	public String getTrainList() {
		try {
			CloseableHttpClient httpclient = getHttpClient();
			HttpGet httpGet = new HttpGet(train_list_url);
			HttpResponse response = httpclient.execute(httpGet);
			Map<String, Object> map = CrawlerUtils.readHttpResponse(response);
			String content = map.get("content").toString().substring(16);
			return content;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// 获取所有的车站名称和编码
	public  Map<String, String> getAllStationName() {
		return getStationName(station_name_url,20);
	}
	
	public  Map<String, String> getFavoriteStationName() {
		return getStationName(favorite_name_url,22);
	}	
	
	private Map<String, String> getStationName(String url,int distinct) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			CloseableHttpClient httpclient = getHttpClient();
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = httpclient.execute(httpGet);
			Map<String, Object> map = CrawlerUtils.readHttpResponse(response);
			String content = map.get("content").toString();
			content = content.substring(distinct, content.length()-2);
			String[] list = content.split("@");
			for(String station: list) {
				if(station.length()>0) {
					String[] code = station.split("\\|");
					result.put(code[1], code[2]);	
				}
				
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}	
	
	public List<String> parseTrainJson(String content, Map<String, String> stationMap){
		List<String> requestLines = new ArrayList<String>();
		try {
			BufferedOutputStream buff = new BufferedOutputStream(new FileOutputStream(new File("train_list_" + filetime + ".csv")));
			JsonParser parse =new JsonParser();
			JsonObject json = (JsonObject) parse.parse(content);
			String maxDate = "";
			for(Entry<String,JsonElement> entry: json.entrySet()) { //共31天
				String trainDate = entry.getKey();  
				if(trainDate.compareTo(maxDate) > 0) {
					maxDate = trainDate;
				}
			}
			
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
						String startStationCode = stationMap.get(startStationName);
						String endStationName = trainNameFix.split("-")[1];			
						String endStationCode = stationMap.get(endStationName);
						String line = currentDate + "," + trainDate + "," + trainNo + "," + trainCode + "," + startStationName + "," + startStationCode + "," + endStationName + "," + endStationCode +"\r\n";
						buff.write(line.getBytes());
						if(maxDate.equals(trainDate)) {
							requestLines.add(MessageFormat.format(query_train_url, trainNo, startStationCode, endStationCode, trainDate));
						}
					}
				}
			}
			buff.flush();
			buff.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return requestLines;
	}

	private static void queryTrains(List<String> requestLines, BufferedOutputStream buff) throws IOException {
		for(String requestLine: requestLines) {
			try {
				queryByTrainNo(requestLine, buff);
			} catch (Exception e) {
				buff.flush();
			}
			
		}
		
	}
	
	private static String trimToEmpty(JsonElement element) {
		if(element == null) {
			return "";
		}
		return element.getAsString();
	}
	
	// 根据车次查询途径站点
	public static void queryByTrainNo(String url, BufferedOutputStream buff) throws Exception {
		CloseableHttpClient httpclient = getHttpClient();
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = httpclient.execute(httpGet);
		Map<String, Object> map = CrawlerUtils.readHttpResponse(response);
		String content = map.get("content").toString();
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
			String line = currentDate + "," + url + "," + mapRequest.get("train_no") + "," + mapRequest.get("from_station_telecode") + "," + mapRequest.get("to_station_telecode") + "," + mapRequest.get("depart_date") + "," + startStationName + "," + arriveTime + "," + stationTrainCode + "," + stationName + "," + trainClassName + "," + serviceType + "," + startTime + "," + stopoverTime + "," + end_stationName + "," + stationNo + "," + isEnabled + "\r\n";
			buff.write(line.getBytes());
		}
	}

	public static void saveFile(String fileName, Map<String, String> content) throws IOException {
		BufferedOutputStream buff = new BufferedOutputStream(new FileOutputStream(new File(fileName + "_" + filetime + ".csv")));
		for (Map.Entry<String, String> m :content.entrySet())  {  
			buff.write(m.getValue().getBytes());			
		} 
		buff.flush();
		buff.close();			
	}

	public static void main(String[] args) throws IOException {
		TrainClient client = new TrainClient();
		String trainList = client.getTrainList();
		Map<String, String> stationMap = client.getAllStationName();
		saveFile("all_station", stationMap);
		List<String> requestLines = client.parseTrainJson(trainList, stationMap);	
		BufferedOutputStream buff = new BufferedOutputStream(new FileOutputStream(new File("trainno_list_" + filetime + ".csv")));
		queryTrains(requestLines, buff);
		buff.flush();
		buff.close();			
	}
	

}

class CRequest {

	private static String TruncateUrlPage(String strURL) {
		String strAllParam = null;
		String[] arrSplit = null;

		strURL = strURL.trim().toLowerCase();

		arrSplit = strURL.split("[?]");
		if (strURL.length() > 1) {
			if (arrSplit.length > 1) {
				if (arrSplit[1] != null) {
					strAllParam = arrSplit[1];
				}
			}
		}

		return strAllParam;
	}

	public static Map<String, String> URLRequest(String URL) {
		Map<String, String> mapRequest = new HashMap<String, String>();

		String[] arrSplit = null;

		String strUrlParam = TruncateUrlPage(URL);
		if (strUrlParam == null) {
			return mapRequest;
		}

		arrSplit = strUrlParam.split("[&]");
		for (String strSplit : arrSplit) {
			String[] arrSplitEqual = null;
			arrSplitEqual = strSplit.split("[=]");

			if (arrSplitEqual.length > 1) {

				mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

			} else {
				if (arrSplitEqual[0] != "") {

					mapRequest.put(arrSplitEqual[0], "");
				}
			}
		}
		return mapRequest;
	}

}
