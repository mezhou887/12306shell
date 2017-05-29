package com.mezhou887.train;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
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

public class TrainClient {
	
    private static final String HTTP = "http";
    private static final String HTTPS = "https";
    private static SSLConnectionSocketFactory sslsf = null;
    private static PoolingHttpClientConnectionManager cm = null;
    private static SSLContextBuilder builder = null;
    private static Date date = new Date();
    private static String currentDate;
	
	static {
        try {
            builder = new SSLContextBuilder();
            // 全部信任 不做身份鉴定
            builder.loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            });
            sslsf = new SSLConnectionSocketFactory(builder.build(), new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register(HTTP, new PlainConnectionSocketFactory())
                    .register(HTTPS, sslsf)
                    .build();
            cm = new PoolingHttpClientConnectionManager(registry);
            cm.setMaxTotal(200);//max connection
            currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
    public static CloseableHttpClient getHttpClient() throws Exception {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .setConnectionManager(cm)
                .setConnectionManagerShared(true)
                .build();
        return httpClient;
    }
    
    public static Map<String, Object> readHttpResponse(HttpResponse httpResponse) throws ParseException, IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        HttpEntity entity = httpResponse.getEntity();
        map.put("status", httpResponse.getStatusLine());
        if (entity != null) {
            String responseString = EntityUtils.toString(entity);
            map.put("length", responseString.length());
            map.put("content", responseString.replace("\r\n", ""));
        }
        return map;
    }    
	

	public String getTrainList() {
		try {
			CloseableHttpClient httpclient = getHttpClient();
			HttpGet httpGet = new HttpGet("https://kyfw.12306.cn/otn/resources/js/query/train_list.js?scriptVersion=1.0");
			HttpResponse response = httpclient.execute(httpGet);
			Map<String, Object> map = readHttpResponse(response);
			String content = map.get("content").toString().substring(16);
			return content;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public  Map<String, String> getAllStationName() {
		String url = "https://kyfw.12306.cn/otn/resources/js/framework/station_name.js?station_version=1.9002";
		return getStationName(url,20);
	}
	
	public  Map<String, String> getFavoriteStationName() {
		String url = "https://kyfw.12306.cn/otn/resources/js/framework/favorite_name.js";
		return getStationName(url,22);
	}	
	
	private Map<String, String> getStationName(String url,int distinct) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			CloseableHttpClient httpclient = getHttpClient();
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = httpclient.execute(httpGet);
			Map<String, Object> map = readHttpResponse(response);
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
			BufferedOutputStream buff = new BufferedOutputStream(new FileOutputStream(new File("D:/train_list_" + date.getTime() + ".csv")));
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
							String requestLine = "https://kyfw.12306.cn/otn/czxx/queryByTrainNo?train_no=" + trainNo + "&from_station_telecode=" + startStationCode + "&to_station_telecode=" + endStationCode + "&depart_date="+trainDate;							
							requestLines.add(requestLine);
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

	private static void queryTrains(List<String> requestLines, BufferedOutputStream buff) {
		for(String requestLine: requestLines) {
			queryByTrainNo(requestLine, buff);
		}
		
	}
	
	private static String trimToEmpty(JsonElement element) {
		if(element == null) {
			return "";
		}
		return element.getAsString();
	}
	
	public static void queryByTrainNo(String url, BufferedOutputStream buff) {
		try {
			CloseableHttpClient httpclient = getHttpClient();
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = httpclient.execute(httpGet);
			Map<String, Object> map = readHttpResponse(response);
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

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	public static void main(String[] args) throws IOException{
		TrainClient client = new TrainClient();
		String trainList = client.getTrainList();
		Map<String, String> stationMap = client.getAllStationName();
		List<String> requestLines = client.parseTrainJson(trainList, stationMap);	
		BufferedOutputStream buff = new BufferedOutputStream(new FileOutputStream(new File("D:/trainno_list_" + date.getTime() + ".csv")));
		queryTrains(requestLines, buff);
		buff.flush();
		buff.close();
	}



}
