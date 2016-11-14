package com.ismes.data_access.mqtt;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Properties;
import java.util.UUID;

import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.Callback;
import org.fusesource.mqtt.client.CallbackConnection;
import org.fusesource.mqtt.client.Listener;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.isesol.ismes.platform.api.sdk.ApiSDK;
import com.isesol.ismes.platform.api.sdk.SDKException;

class MQTTCallbackClientThread extends Thread {
	private String appid;
	private String appkey;
	private String server;
	private String protocol;
	private String apiInsertSs;
	private String mqtt_connection_string;
	private MQTT mqtt;
	private static final Logger LOG = LoggerFactory.getLogger(MQTTCallbackClient.class);
	//private final static String CONNECTION_STRING = "tcp://10.24.11.103:1883";
	private final static boolean CLEAN_START = true;
	private final static short KEEP_ALIVE = 30;// 低耗网络，但是又需要及时获取数据，心跳30s
//	public static Topic[] topics = { new Topic("china/beijing", QoS.AT_MOST_ONCE),
//			new Topic("china/tianjin", QoS.AT_LEAST_ONCE), new Topic("china/henan", QoS.AT_MOST_ONCE) };
	public static Topic[] topics = { new Topic("MachineConnectionLost/#", QoS.AT_MOST_ONCE)};
	public final static long RECONNECTION_ATTEMPT_MAX = 6;
	public final static long RECONNECTION_DELAY = 2000;
	final String topic = "MachineConnectionLost/#";
	public final static int SEND_BUFFER_SIZE = 2 * 1024 * 1024;// 发送最大缓冲为2M

	public MQTTCallbackClientThread() {
		Properties p = new Properties();
		try {
			String propertiesstr = Thread.currentThread().getContextClassLoader().getResource("services.properties")
					.getPath();
			p.load(new FileInputStream(propertiesstr));
			this.appid = p.getProperty("appid");
			this.appkey = p.getProperty("appkey");
			this.server = p.getProperty("server");
			this.protocol = p.getProperty("protocol");
			this.apiInsertSs = p.getProperty("apiInsertSs");
			this.mqtt_connection_string = p.getProperty("mqtt_protocol") + "://" +
					p.getProperty("mqtt_ip")+ ":" + p.getProperty("mqtt_port") ;
			// 创建MQTT对象
			this.mqtt = new MQTT();
			// 设置mqtt broker的ip和端口
			try {
				//mqtt.setHost(CONNECTION_STRING);
				mqtt.setHost(mqtt_connection_string);
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}
			// 连接前清空会话信息
			mqtt.setCleanSession(CLEAN_START);
			// 设置重新连接的次数
			mqtt.setReconnectAttemptsMax(RECONNECTION_ATTEMPT_MAX);
			mqtt.setUserName("listener");
			mqtt.setPassword("123456");
			// 设置重连的间隔时间
			mqtt.setReconnectDelay(RECONNECTION_DELAY);
			// 设置心跳时间
			mqtt.setKeepAlive(KEEP_ALIVE);
			// 设置缓冲的大小
			mqtt.setSendBufferSize(SEND_BUFFER_SIZE);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void run() {
		// 获取mqtt的连接对象CallbackConnection
		final CallbackConnection connection = mqtt.callbackConnection();
		try {

			// 添加连接的监听事件
			connection.listener(new Listener() {
				@Override
				public void onDisconnected() {
					// 表示监听到断开连接
					LOG.info(" 断开连接  !");
				}

				@Override
				public void onConnected() {
					LOG.info(" 连接成功!");
				}

				@Override
				public void onPublish(UTF8Buffer topicmsg, Buffer msg, Runnable ack) {
					// utf-8 is used for dealing with the garbled
					String topic = topicmsg.utf8().toString();
					String timeoutmsg = msg.utf8().toString();
					System.out.println(topic);
					System.out.println(timeoutmsg);
					String uuid = UUID.randomUUID().toString();
					LOG.info("MQTT 事件监听 onPublish,uuid="+uuid +";;topic="+topic+";;timeoutmsg="+timeoutmsg);
					try {
						insertTimeoutSs(timeoutmsg,uuid);
					} catch (SDKException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// 表示监听成功
					ack.run();
				}

				@Override
				public void onFailure(Throwable value) {
					// 表示监听失败
					System.out.println(" 监听失败   !");
				}
			});
			// 添加连接事件
			connection.connect(new Callback<Void>() {
				/**
				 * 连接失败的操作
				 */
				public void onFailure(Throwable value) {
					// If we could not connect to the server.
					System.out.println("MQTTSubscribeClient.CallbackConnection.connect.onFailure  连接失败......"
							+ value.getMessage());
					value.printStackTrace();
				}

				/**
				 * 连接成功的操作
				 * 
				 * @param v
				 */
				public void onSuccess(Void v) {
					System.out.println("MQTTSubscribeClient.CallbackConnection.connect.onSuccess 订阅连接成功......");

					// 订阅相关的主题
					connection.subscribe(topics, new Callback<byte[]>() {
						public void onSuccess(byte[] qoses) {
							System.out.println(
									"MQTTSubscribeClient.CallbackConnection.connect.subscribe.onSuccess 订阅主题成功......");
						}

						public void onFailure(Throwable value) {
							// subscribe failed.
							System.out.println(
									"MQTTSubscribeClient.CallbackConnection.connect.subscribe.onSuccess 订阅主题失败！"
											+ value.getMessage());
							value.printStackTrace();
						}
					});

				}
			});
			Thread.sleep(10 * 365 * 24 * 60 * 60 * 1000L);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// //连接断开
			connection.disconnect(new Callback<Void>() {
				public void onSuccess(Void v) {
					// called once the connection is disconnected.
					System.out.println(
							"MQTTSubscribeClient.CallbackConnection.connect.disconnect.onSuccess called once the connection is disconnected.");
				}

				public void onFailure(Throwable value) {
					// Disconnects never fail.
					System.out.println(
							"MQTTSubscribeClient.CallbackConnection.connect.disconnect.onFailure  Disconnects never fail."
									+ value.getMessage());
					value.printStackTrace();
				}
			});
		}
	}

	public void insertTimeoutSs(String timeoutMsg,String uuid) throws SDKException {
		LOG.info("METHOD insertTimeoutSs,uuid = " + uuid +";;timeoutMsg=" + timeoutMsg
				+ ";;server=" + server +";;appid=" + appid
				+ ";;appkey=" + appkey +";;apiInsertSs=" + apiInsertSs
				+ ";;protocol=" + protocol);
		HashMap<String, String> params = new HashMap<String, String>();
		params.clear();
		params.put("timeoutMsg", timeoutMsg);
		params.put("uuid", uuid);
		ApiSDK openApi = new ApiSDK(server, appid, appkey);
		String json = null;
		json = openApi.call(apiInsertSs, params, protocol);
	}
}