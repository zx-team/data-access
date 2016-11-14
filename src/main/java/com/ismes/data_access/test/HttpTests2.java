package com.ismes.data_access.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.ismes.data_access.util.JsonPluginsUtil;

public class HttpTests2 {

	/**
	 * @param args
	 * @throws IOException
	 * @throws Exception
	 */
	public static void main(String[] args) throws IOException {
//		CloseableHttpClient httpclient = HttpClients.createDefault();
//		HttpPost httpPost = new HttpPost("http://localhost:8333/demo/catchFiles2");
//		try {
//			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
//			HttpResponse rs = httpclient.execute(httpPost);
//			int n = (int)(Math.random()*100);
//			File file = new File("D:\\MyWorkDoc\\"+n+".rar");
//			inputstreamtofile(rs.getEntity().getContent(),file);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			httpclient.close();
//		}
		
		String content="{\"sbbh1\": {\"gdbh1\": {\"chengxu1\": {\"url\": \"http://\"},\"chengxu2\": {\"url\": \"http://\"}}},\"sbbh2\": {\"gdbh1\": {\"chengxu1\": { \"url\": \"http://\"},\"chengxu2\": {\"url\": \"http://\"}}}}";
		Map m = JsonPluginsUtil.jsonToMap(content);
		System.out.println("");
		
//		Map murl1 = new HashMap();
//		murl1.put("mainfileurl", "http://192.168.1.1:8080/xxx/xxx/xxx");
//		murl1.put("fileurl", "http://192.168.1.1:8080/xxx/xxx/xxx");
//		murl1.put("fileurl", "http://192.168.1.1:8080/xxx/xxx/xxx");
//		
//		Map murl2 = new HashMap();
//		murl2.put("mainfileurl", "http://192.168.2.2:8080/xxx/xxx/xxx");
//		murl2.put("fileurl", "http://192.168.2.2:8080/xxx/xxx/xxx");
//		murl2.put("fileurl", "http://192.168.2.2:8080/xxx/xxx/xxx");
//		
//		Map murl3 = new HashMap();
//		murl3.put("mainfileurl", "http://192.168.3.3:8080/xxx/xxx/xxx");
//		murl3.put("fileurl", "http://192.168.3.3:8080/xxx/xxx/xxx");
//		murl3.put("fileurl", "http://192.168.3.3:8080/xxx/xxx/xxx");
//		
//		Map murl4 = new HashMap();
//		murl4.put("mainfileurl", "http://192.168.4.4:8080/xxx/xxx/xxx");
//		murl4.put("fileurl", "http://192.168.4.4:8080/xxx/xxx/xxx");
//		murl4.put("fileurl", "http://192.168.4.4:8080/xxx/xxx/xxx");
//		
//		List l1 = new ArrayList();
//		l1.add(murl1);
//		l1.add(murl2);
//		
//		List l2 = new ArrayList();
//		l2.add(murl1);
//		l2.add(murl2);
//		
//		
//		Map mgd = new HashMap();
//		mgd.put("gdbh1", l1);
//		mgd.put("gdbh2", l2);
//		
//		Map msb = new HashMap();
//		msb.put("sbbh1", l1);
		
		
		
	}

	public static void inputstreamtofile(InputStream ins, File file) throws IOException {
		OutputStream os = new FileOutputStream(file);
		int bytesRead = 0;
		byte[] buffer = new byte[8192];
		while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
			os.write(buffer, 0, bytesRead);
		}
		os.close();
		ins.close();
	}

}
