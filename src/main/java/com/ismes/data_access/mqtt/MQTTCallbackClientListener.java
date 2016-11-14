package com.ismes.data_access.mqtt;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MQTTCallbackClientListener implements ServletContextListener {  
    private MQTTCallbackClientThread  myThread;  
    
    @Override
    public void contextDestroyed(ServletContextEvent e) {  
        if (myThread != null && myThread.isInterrupted()) {  
            myThread.interrupt();  
        }  
    }  
    
    @Override
    public void contextInitialized(ServletContextEvent e) {  
        String str = null;  
        if (str == null && myThread == null) {  
            myThread = new MQTTCallbackClientThread();  
            myThread.start(); // servlet 上下文初始化时启动 socket  
        }  
    }

}  