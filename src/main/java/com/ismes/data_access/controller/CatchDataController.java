package com.ismes.data_access.controller;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.isesol.ismes.platform.api.sdk.ApiSDK;
import com.isesol.ismes.platform.api.sdk.SDKException;
import com.ismes.data_access.checkvalue.CheckDate;
import com.ismes.data_access.checkvalue.CheckDouble;
import com.ismes.data_access.checkvalue.CheckInt;
import com.ismes.data_access.checkvalue.CheckString;
import com.ismes.data_access.checkvalue.CheckUtil;
import com.ismes.data_access.customexception.CheckException;
import com.ismes.data_access.customexception.CheckJsonException;
import com.ismes.data_access.customexception.CxmcException;
import com.ismes.data_access.customexception.ParseJsonException;
import com.ismes.data_access.customexception.callSDKException;
import com.ismes.data_access.result.ConstantEnum;
import com.ismes.data_access.result.Result;
import com.ismes.data_access.util.JsonPluginsUtil;
import com.ismes.data_access.util.ReschedulableTimerTask;

import net.sf.json.JSONObject;

@RestController
public class CatchDataController {
	@Value("${appid}")
	private String appid;
	@Value("${appkey}")
	private String appkey;
	@Value("${server}")
	private String server;
	@Value("${protocol}")
	private String protocol;
	@Value("${api}")
	private String api;
	@Value("${api_5004}")
	private String api_5004;
	@Value("${api_timeout}")
	private String api_timeout;
	@Value("${updateTablesbyid_arr}")
	private String updateTablesbyid_arr;
	@Value("${json_all}")
	private String json_all;
	@Value("${apitype2table}")
	private String apitype2table;
	@Value("${subscribe}")
	private String subscribe;
	// type.xml配置信息
	public HashMap<String, HashMap<String, PropertyField>> hm = new HashMap<String, HashMap<String, PropertyField>>();
	public HashMap<String, List<String>> hmdata = new HashMap<String, List<String>>();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// log4j
	private static Logger logger = Logger.getLogger(CatchDataController.class);
	StringBuffer logsb;
	// int,String的校验map
	Map<String, CheckUtil> m = new HashMap<String, CheckUtil>();
	// type与table的对照map
	Map<String, String> type2table = new HashMap<String, String>();
	Map<String, Timer> timeout_map = new HashMap<String, Timer>();
	Map<String, String> subscribe_map = new HashMap<String, String>();

	// 初始化功能
	@PostConstruct
	public void init() {
		m.put("Integer", new CheckInt());
		m.put("String", new CheckString());
		m.put("Date", new CheckDate());
		m.put("Double", new CheckDouble());
		String[] arr_subscribe = subscribe.split(",");
		for (int i = 0; i < arr_subscribe.length; i++) {
			subscribe_map.put(arr_subscribe[i], arr_subscribe[i]);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/customPostJson", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String customPostJson(String machineNo, String type, String content,HttpServletRequest request) {
		//2222 实时数据
		//5004 自动报工
		//5555 状态变化
		//5556 状态变化
		if(!"5555".equals(type) && !"2222".equals(type) && !"5004".equals(type) && !"5556".equals(type)){
			return JsonPluginsUtil.beanToJson(new Result(ConstantEnum.BACK_999, machineNo, type));
		}
		
//		synchronized (timeout_map) {
//			if (timeout_map.containsKey(machineNo)) {
//				logger.info("olddddddddddddddddddd" + machineNo);
//				Timer oldtime = timeout_map.get(machineNo);
//				oldtime.cancel();
//				ReschedulableTimerTask tt = new ReschedulableTimerTask(machineNo, api_timeout, appid, appkey, server,
//						protocol);
//				Timer newtimer = new Timer();
//				newtimer.schedule(tt, 5 * 60 * 1000, 5 * 60 * 1000);
//				timeout_map.put(machineNo, newtimer);
//			} else {
//				logger.info("newwwwwwwwwwwwwwwwwwwwww" + machineNo);
//				ReschedulableTimerTask tt = new ReschedulableTimerTask(machineNo, api_timeout, appid, appkey, server,
//						protocol);
//				Timer timer = new Timer();
//				timer.schedule(tt, 5 * 60 * 1000, 5 * 60 * 1000);
//				timeout_map.put(machineNo, timer);
//			}
//		}
		
		//设置请求的唯一标识
		String uuid = UUID.randomUUID().toString();
		logger.info("本次请求唯一标识,uuid===" + uuid + ";;machineNo == " + machineNo + ";;type == " +  type + ";; content == " + content );
		
		// 判断是否存在type.xml配置信息，不存在则进行解析
		try {
			String tablename = "";
			if (!subscribe_map.containsKey(type)) {
				tablename = queryBysdk(type,uuid);
				logger.info("uuid:"+uuid+";;tablename==="+tablename+";;;");
				if ("".equals(tablename)) {
					return JsonPluginsUtil.beanToJson(new Result(ConstantEnum.BACK_101, machineNo, type,uuid));
				}
			}
			
			if (!hm.containsKey(json_all)) {
				hm.put(json_all, parseXmlFile());
				doValidate(tablename, machineNo, content, type,uuid);
			} else {
				doValidate(tablename, machineNo, content, type,uuid);
			}
		} catch (ClassNotFoundException e) {
			// 解析xml出错
			logger.info("ClassNotFoundException");
			logger.info(e.getMessage());
			return JsonPluginsUtil.beanToJson(new Result(ConstantEnum.BACK_999, machineNo, type,uuid));
		} catch (InstantiationException e) {
			// 解析xml出错
			logger.info("InstantiationException");
			logger.info(e.getMessage());
			return JsonPluginsUtil.beanToJson(new Result(ConstantEnum.BACK_999, machineNo, type,uuid));
		} catch (IllegalAccessException e) {
			// 解析xml出错
			logger.info("IllegalAccessException");
			logger.info(e.getMessage());
			return JsonPluginsUtil.beanToJson(new Result(ConstantEnum.BACK_999, machineNo, type,uuid));
		} catch (NoSuchFieldException e) {
			logger.info("NoSuchFieldException");
			logger.info(e.getMessage());
			return JsonPluginsUtil.beanToJson(new Result(ConstantEnum.BACK_999, machineNo, type,uuid));
		} catch (SecurityException e) {
			// 解析xml出错
			logger.info("SecurityException");
			logger.info(e.getMessage());
			return JsonPluginsUtil.beanToJson(new Result(ConstantEnum.BACK_999, machineNo, type,uuid));
		} catch (ParserConfigurationException e) {
			// 解析xml出错
			logger.info("ParserConfigurationException");
			logger.info(e.getMessage());
			return JsonPluginsUtil.beanToJson(new Result(ConstantEnum.BACK_999, machineNo, type,uuid));
		} catch (SAXException e) {
			// 解析xml出错
			logger.info("SAXException");
			logger.info(e.getMessage());
			logger.info("uuid===" + uuid);
			return JsonPluginsUtil.beanToJson(new Result(ConstantEnum.BACK_999, machineNo, type,uuid));
		} catch (IOException e) {
			// 解析xml出错
			logger.info("IOException");
			logger.info(e.getMessage());
			logger.info("uuid===" + uuid);
			return JsonPluginsUtil.beanToJson(new Result(ConstantEnum.BACK_999, machineNo, type,uuid));
		} catch (SDKException e) {
			// 调用sdk异常
			logger.info("SDKException");
			logger.info(e.getMessage());
			logger.info("uuid===" + uuid);
			return JsonPluginsUtil.beanToJson(new Result(ConstantEnum.BACK_999, machineNo, type,uuid));
		} catch (CheckJsonException e) {
			// 数据传递的字段在xml中未定义
			logger.info("CheckJsonException");
			logger.info(e.getMessage());
			return JsonPluginsUtil.beanToJson(new Result(ConstantEnum.BACK_999, machineNo, type,uuid));
		} catch (CheckException e) {
			// 不满足范围校验规则
			logger.info("CheckException");
			logger.info(e.getMessage());
			logger.info("uuid===" + uuid);
			return JsonPluginsUtil.beanToJson(new Result(ConstantEnum.BACK_999, machineNo, type,uuid));
		} catch (callSDKException e) {
			logger.info("callSDKException");
			logger.info(e.getMessage());
			logger.info("uuid===" + uuid);
			return JsonPluginsUtil.beanToJson(new Result(ConstantEnum.BACK_999, machineNo, type,uuid));
		} catch (CxmcException e) {
			logger.info("CxmcException");
			logger.info(e.getMessage());
			logger.info("uuid===" + uuid);
			return JsonPluginsUtil.beanToJson(new Result(ConstantEnum.BACK_999, machineNo, type,uuid));
		} catch (Exception e) {
			logger.info("Exception");
			logger.info(e.getMessage());
			logger.info("uuid===" + uuid);
			return JsonPluginsUtil.beanToJson(new Result(ConstantEnum.BACK_999, machineNo, type,uuid));
		}
		return JsonPluginsUtil.beanToJson(new Result(ConstantEnum.SUCCESS, machineNo, type,uuid));
	}


	// 对jsonValue进行校验，符合条件后调用sdk
	private void doValidate(String tablename, String machineNo, String content, String type,String uuid) throws SDKException,
			ParseJsonException, CheckJsonException, CheckException, callSDKException, CxmcException {
		JSONObject jsonobj = JSONObject.fromObject(content);
		HashMap<String, PropertyField> typemap = hm.get(json_all);
		// 遍历map中的键
		Map<String, Object> convert_map = new HashMap<String, Object>();
		StringBuffer updatefieldsb = new StringBuffer();
		StringBuffer datefieldsb = new StringBuffer();
		StringBuffer dateFormatsb = new StringBuffer();

		Set<String> jsonobjset = jsonobj.keySet();
		for (String key : jsonobjset) {
			logger.info("uuid ==" + uuid + ";; 对比" + key);
			// System.out.println(jsonobj.getString(key));
			PropertyField pf = typemap.get(key);
			if (!typemap.containsKey(key)) {
				logsb = new StringBuffer();
				logsb.append("该字段");
				logsb.append(key);
				logsb.append("在");
				logsb.append(json_all);
				logsb.append("中不存在");
				// System.out.println(logsb.toString());
				convert_map.clear();
				updatefieldsb.setLength(0);
				datefieldsb.setLength(0);
				dateFormatsb.setLength(0);
				throw new CheckJsonException("校验json字段异常");
				// return;
			} else {
				CheckUtil i = m.get(pf.getType());
				if (!i.publicCheck(pf, jsonobj.getString(key))) {
					// System.out.println(key + "不符合规则");
					convert_map.clear();
					updatefieldsb.setLength(0);
					datefieldsb.setLength(0);
					dateFormatsb.setLength(0);
					throw new CheckException("校验json字段异常");
					// return;
				}
				String param = jsonobj.getString(key);
				if (pf.getType().equals("Integer")) {
					if (!"".equals(param)) {
						int value = Integer.parseInt(param);
						convert_map.put(pf.getSqlField(), value);
					} else {
						convert_map.put(pf.getSqlField(), 0);
					}
				} else if (pf.getType().equals("Double")) {
					if (!"".equals(param)) {
						double value = Double.parseDouble(param);
						convert_map.put(pf.getSqlField(), value);
					} else {
						convert_map.put(pf.getSqlField(), 0D);
					}
				} else if (pf.getType().equals("Float")) {
					if (!"".equals(param)) {
						Float value = Float.parseFloat(param);
						convert_map.put(pf.getSqlField(), value);
					} else {
						convert_map.put(pf.getSqlField(), 0F);
					}
				} else if (pf.getType().equals("Long")) {
					if (!"".equals(param)) {
						long value = Long.parseLong(param);
						convert_map.put(pf.getSqlField(), value);
					} else {
						convert_map.put(pf.getSqlField(), 0L);
					}
				} else {
//					if (pf.getType().equals("Date")) {
//						convert_map.put(pf.getSqlField(), sdf.format(new Date()));
//					} else {
//						if (!"".equals(param)) {
//							convert_map.put(pf.getSqlField(), param);
//						}
//					}
					 if(!"".equals(param)){
						 convert_map.put(pf.getSqlField(), param);
					 }
				}
				if (null != pf.getDateformat() && !"".equals(pf.getDateformat())) {

					datefieldsb.append(pf.getSqlField());
					datefieldsb.append(",");
					dateFormatsb.append(pf.getDateformat());
					dateFormatsb.append(",");
				}
			}
		}
		convert_map.put("uuid", uuid);
		JSONObject jsonObject = JSONObject.fromObject(convert_map);
		// System.out.println("符合规则");

		//自动报工
		if ("5004".equals(type)) {
			if (null != jsonObject.get("cxmc")) {
				passSdk_5004(machineNo, jsonObject.get("cxmc").toString(),jsonObject.get("pl_parcount").toString()
						,jsonObject.get("uuid").toString());
			} else {
				throw new CxmcException("程序名称为空异常");
			}
		} else {
			//除了报工以外的 业务
			passSdk(tablename, jsonObject.toString(), datefieldsb.toString(), dateFormatsb.toString(), machineNo
					,jsonObject.get("uuid").toString());
		}
	}
	
	//mqtt 超时

	private void passSdk_5004(String machineNo, String cxmc, String pl_parcount,String uuid) throws SDKException, ParseJsonException {
		String temps = "";
		HashMap<String, String> params = new HashMap<String, String>();
		params.clear();
		params.put("sbbh", machineNo);
		params.put("pass_cxmc", cxmc);
		params.put("pl_parcount", pl_parcount);
		params.put("uuid", uuid);
		

		temps = callSdk(params, api_5004);

		if (null != temps && !"".equals(temps)) {
			Map tempm = JsonPluginsUtil.jsonToMap(temps);
			if (tempm.containsKey("code") && tempm.get("code").equals("0")) {
				if (tempm.containsKey("data")) {
					// System.out.println(JsonPluginsUtil.jsonToMap(tempm.get("data").toString()).get("count"));
					temps = JsonPluginsUtil.jsonToMap(tempm.get("data").toString()).get("count").toString();
				} else {
					// System.out.println(machineNo + "报工失败");
				}
			} else {
				// System.out.println(machineNo + "报工失败");
			}
		} else {
			throw new ParseJsonException("返回json异常");
		}
	}

	// 表名，更新后的jsonstr，更新的字段
	private void passSdk(String tablename, String convert_concent, String datefields, String dateFormats,
			String machineNo,String uuid) throws SDKException, ParseJsonException, callSDKException {
		String temps = "";
		HashMap<String, String> params = new HashMap<String, String>();
		params.clear();
		params.put("tablename", tablename);
		params.put("sbbh", machineNo);
		params.put("convert_concent", convert_concent);
		params.put("uuid", uuid);

		if (!"".equals(datefields)) {
			params.put("datefields", datefields.substring(0, datefields.length() - 1));
		}
		if (!"".equals(dateFormats)) {
			params.put("dateFormats", dateFormats.substring(0, dateFormats.length() - 1));
		}
		if (!"".equals(updateTablesbyid_arr)) {
			params.put("updateTablesbyid_arr", updateTablesbyid_arr);
		}
		temps = callSdk(params, api);

		if (null != temps && !"".equals(temps)) {
			Map tempm = JsonPluginsUtil.jsonToMap(temps);
			if (tempm.containsKey("code") && tempm.get("code").equals("0")) {
				if (tempm.containsKey("data") && !"{}".equals(tempm.get("data"))) {
					String str = JsonPluginsUtil.jsonToMap(tempm.get("data").toString()).get("ErrorMsg").toString();
					if (null != str || "".equals(str)) {
						throw new callSDKException(str);
					}
				}
			} else {
				throw new ParseJsonException("返回json异常");
			}
		} else {
			throw new ParseJsonException("返回json异常");
		}
	}

	private HashMap<String, PropertyField> parseXmlFile()
			throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException,
			InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException {
		// step 1:获得DOM解析器工厂
		// 工厂的作用是创建具体的解析器
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		// step 2：获得具体的dom解析器
		DocumentBuilder db = dbf.newDocumentBuilder();
		// step 3:解析一个xml文档，获得Document对象（根节点）
		// 此文档放在项目目录下即可
		StringBuffer folder = new StringBuffer();
		folder.append("/customxml/");
		folder.append(json_all);
		folder.append(".xml");
		Document document = db.parse(this.getClass().getResourceAsStream(folder.toString()));
		// json-key与属性的对照map
		HashMap<String, PropertyField> jsoninfomap = new HashMap<String, PropertyField>();
		// 根据标签名访问节点
		Element jsoninfoElement = (Element) document.getElementsByTagName("jsoninfo").item(0);
		NodeList jsoninfoNodes = jsoninfoElement.getChildNodes();

		for (int i = 0; i < jsoninfoNodes.getLength(); i++) {
			if (jsoninfoNodes.item(i) instanceof Element) {
				Element jsonfieldElement = (Element) jsoninfoNodes.item(i);
				// System.out.println(jsonfieldElement.getNodeName());

				NodeList jsonfieldNodes = jsonfieldElement.getChildNodes();

				Class clazz = Class.forName("com.ismes.data_access.controller.PropertyField");
				Object obj = clazz.newInstance();// 通过反射创建对象
				Field f;
				for (int j = 0; j < jsonfieldNodes.getLength(); j++) {
					if (jsonfieldNodes.item(j) instanceof Element) {
						Element propertyElement = (Element) jsonfieldNodes.item(j);
						// System.out.println(propertyElement.getNodeName());
						// System.out.println(propertyElement.getFirstChild().getNodeValue());
						if (null != propertyElement.getFirstChild()) {
							// 调用getDeclaredField("name") 取得name属性对应的Field对象
							f = clazz.getDeclaredField(propertyElement.getNodeName());
							// 取消属性的访问权限控制，即使private属性也可以进行访问。
							f.setAccessible(true);
							// 调用get()方法取得对应属性值。
							// System.out.println(f.get(obj)); //
							// 相当于obj.getName();
							// 调用set()方法给对应属性赋值。
							f.set(obj, propertyElement.getFirstChild().getNodeValue()); // 相当于obj.setName("lkl");
							// 调用get()方法取得对应属性修改后的值。
							// System.out.println(f.get(obj));
						}
					}
				}
				jsoninfomap.put(jsonfieldElement.getNodeName().replace("-", " "), (PropertyField) obj);
			}
		}
		return jsoninfomap;
	}

	public String queryBysdk(String type,String uuid ) throws SDKException {
		String temps = "";
		HashMap<String, String> params = new HashMap<String, String>();
		params.clear();
		params.put("type", type);
		params.put("uuid", uuid);
		temps = callSdk(params, apitype2table);
		if (null != temps && !"".equals(temps)) {
			Map tempm = JsonPluginsUtil.jsonToMap(temps);
			if (tempm.containsKey("code") && tempm.get("code").equals("0")) {
				if (tempm.containsKey("data")) {
					// System.out.println(JsonPluginsUtil.jsonToMap(tempm.get("data").toString()).get("interftablename"));
					temps = JsonPluginsUtil.jsonToMap(tempm.get("data").toString()).get("interftablename").toString();
				}
			}
		}
		return temps;
	}

	public String callSdk(HashMap<String, String> params, String apiflag) throws SDKException {
		ApiSDK openApi = new ApiSDK(server, appid, appkey);
		String json = null;
		logger.info("call api : " + apiflag);
//		System.out.println("call api : " + apiflag);
		long begin = System.currentTimeMillis();
		String uuid = "";
		if(params.get("uuid") != null){
			uuid = params.get("uuid").toString();
		}
		json = openApi.call(apiflag, params, protocol);
		logger.info("callSdk,uuid=" + uuid + ";;;jsonResult=" + json + ";;time : " + (System.currentTimeMillis() - begin) + " ms");
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "/customPostJson1", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String customPostJson1(String destination, String switchs, String frequence,String level, String orderType, String cmdId, String orderData) {
		System.out.println("destination="+destination);
		System.out.println("switchs="+switchs);
		System.out.println("frequence"+frequence);
		System.out.println("level="+level);
		System.out.println("orderType="+orderType);
		System.out.println("cmdId="+cmdId);
		System.out.println("orderData="+orderData);
		JSONObject jsonobj = JSONObject.fromObject(orderData);
		System.out.println("helloworld");
		return "helloworld";
	}
	
	public static void main(String[] args) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("a", new Date());
		map.put("c", 1);

		JSONObject json = JSONObject.fromObject(map);
		System.out.println(json.toString());
	}
}
