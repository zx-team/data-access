package com.ismes.data_access.sqlmerge;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SqlMergeListener implements ServletContextListener {  
    private SqlMergeThread  myThread;  
    
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
            myThread = new SqlMergeThread();  
            myThread.start(); // servlet 上下文初始化时启动 socket  
        }  
    }

}  