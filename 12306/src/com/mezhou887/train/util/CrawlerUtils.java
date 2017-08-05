package com.mezhou887.train.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

public class CrawlerUtils {
	
	public static Set<String> set = new HashSet<String>();
	
	static {
		set.add("佛山西");
		set.add("金山卫");
		set.add("车墩");
		set.add("九龙");
		set.add("梅江");
		set.add("张家川");
		set.add("元龙");
		set.add("南山北");
		set.add("鱼卡");
		set.add("马海");
		set.add("南山北");
		set.add("南山北");
		set.add("南山北");		
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
    
}
