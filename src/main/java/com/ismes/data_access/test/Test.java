package com.ismes.data_access.test;

import java.util.HashMap;
import java.util.Map;

import com.ismes.data_access.util.JsonPluginsUtil;

public class Test {
	public static void main(String[] args) {
		Map<String, Map<String, Map<String, String>>> sbmap = new HashMap<String, Map<String, Map<String, String>>>();
		
		Map<String, Map<String, String>> gdmaps = new HashMap<String, Map<String, String>>();
		
		Map<String, String> urlmap_gdbh1 = new HashMap<String, String>();
		
		sbmap.put("sbbh1", gdmaps);
		
		gdmaps.put("gdbh1", urlmap_gdbh1);
		
		urlmap_gdbh1.put("MainUrl", "http://1.1.1.1");
		urlmap_gdbh1.put("url_1", "http://2.2.2.2");
		urlmap_gdbh1.put("url_2", "http://3.3.3.3");
		
		System.out.println(JsonPluginsUtil.mapToJson(sbmap, null, false));
	}
}
