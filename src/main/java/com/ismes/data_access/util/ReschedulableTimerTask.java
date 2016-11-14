package com.ismes.data_access.util;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Value;

import com.isesol.ismes.platform.api.sdk.ApiSDK;
import com.isesol.ismes.platform.api.sdk.SDKException;
import com.ismes.data_access.customexception.ParseJsonException;

public class ReschedulableTimerTask extends TimerTask {
	private String api_timeout = "";
	private String appid = "";
	private String appkey = "";
	private String server = "";
	private String protocol = "";
	private String machineNo = "";

	public ReschedulableTimerTask(String machineNo,String api_timeout,String appid,String appkey,String server,String protocol) {
		super();
		this.machineNo = machineNo;
		this.api_timeout = api_timeout;
		this.appid = appid;
		this.appkey = appkey;
		this.server = server;
		this.protocol = protocol;
	}

	@Override
	public void run() {
		try {
			setTimeOut(machineNo);
		} catch (SDKException e) {
			e.printStackTrace();
			//记录日志，超时调用sdk异常
		} catch (ParseJsonException e) {
			e.printStackTrace();
			//记录日志，超时解析json异常
		}	
	}
	
	private void setTimeOut(String machineNo) throws SDKException, ParseJsonException {
		String temps = "";
		HashMap<String, String> params = new HashMap<String, String>();
		params.clear();
		params.put("sbbh", machineNo);

		temps = callSdk(params, api_timeout);

		if (null != temps && !"".equals(temps)) {
			Map tempm = JsonPluginsUtil.jsonToMap(temps);
			if (tempm.containsKey("code") && tempm.get("code").equals("0")) {
			} else {
				throw new ParseJsonException("返回json异常");
			}
		} else {
			throw new ParseJsonException("返回json异常");
		}
	}
	
	public String callSdk(HashMap<String, String> params, String api) throws SDKException {
		ApiSDK openApi = new ApiSDK(server, appid, appkey);
		String json = null;
		System.out.println("call api : " + api);
		long begin = System.currentTimeMillis();
		json = openApi.call(api, params, protocol);
		System.out.println("result : " + json);
		System.out.println("time : " + (System.currentTimeMillis() - begin) + " ms");
		return json;
	}
} 