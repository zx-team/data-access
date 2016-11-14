package com.ismes.data_access.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class HttpTests {

	/**
	 * @param args
	 * @throws IOException
	 * @throws Exception
	 */
	public static void main(String[] args) throws IOException {
		for (int i = 0; i < 10 ; i++) {
			CloseableHttpClient httpclient = HttpClients.createDefault();
//			HttpPost httpPost = new HttpPost("http://localhost:8080/data_access/customPostJson");
			HttpPost httpPost = new HttpPost("http://223.223.1.111:8080/data_access/customPostJson");
			
			try {

				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				nvps.add(new BasicNameValuePair("machineNo", "A131420096"));
				nvps.add(new BasicNameValuePair("type", "2221"));
				nvps.add(new BasicNameValuePair("content",
						"{\"sys_time\":\"2016-08-29 10:52:2"+ i +"\",\"MODESTATUS\":\"3\",\"CNCRUNLEVEL\":\"0\"}"));
				httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
				HttpResponse rs = httpclient.execute(httpPost);
				BufferedReader br = new BufferedReader(new InputStreamReader(rs.getEntity().getContent()));
				String line = "";
				while ((line = br.readLine()) != null) {
					System.out.println(line);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				httpclient.close();
			}
		}

	}

}
