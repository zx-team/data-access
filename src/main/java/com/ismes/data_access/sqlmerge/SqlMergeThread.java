package com.ismes.data_access.sqlmerge;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;

import com.isesol.ismes.platform.api.sdk.ApiSDK;
import com.isesol.ismes.platform.api.sdk.SDKException;
import com.ismes.data_access.util.JsonPluginsUtil;

class SqlMergeThread extends Thread {
	private String appid;
	private String appkey;
	private String server;
	private String protocol;
	private String apiUpdateSs; 
	
	public SqlMergeThread(){
		Properties p = new Properties();
		try {
			String propertiesstr = Thread.currentThread().getContextClassLoader().getResource("services.properties").getPath();
			p.load(new FileInputStream(propertiesstr));
			this.appid = p.getProperty("appid");
			this.appkey = p.getProperty("appkey");
			this.server = p.getProperty("server");
			this.protocol = p.getProperty("protocol");
			this.apiUpdateSs = p.getProperty("apiUpdateSs");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
	}
	
	
	
    public void run() {  
        while (!this.isInterrupted()) {// 线程未中断执行循环  
        	try {
				mergeHzTable();
			} catch (SDKException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println();
			} 
        }  
    }  
    
	public void mergeHzTable() throws SDKException{
		HashMap<String, String> params = new HashMap<String, String>();
		params.clear();
		ApiSDK openApi = new ApiSDK(server, appid, appkey);
		String json = null;
		json = openApi.call(apiUpdateSs, params, protocol);
	}
}  