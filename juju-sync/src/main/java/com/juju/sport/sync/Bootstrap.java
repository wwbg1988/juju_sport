package com.juju.sport.sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.juju.sport.sync.factory.ApplicationContextFactory;
import com.juju.sport.sync.service.IDataSyncService;
import com.juju.sport.sync.task.DataReSyncThread;
import com.juju.sport.sync.task.DataSyncTask;
import com.juju.sport.sync.task.DataSyncThread;

public class Bootstrap {
	
	protected static final Log logger = LogFactory.getLog(Bootstrap.class);

	public static void main(String[] args) {
		
		ApplicationContext context = ApplicationContextFactory.getApplicationContext();
		DataSyncTask syncTask = (DataSyncTask)context.getBean("dataSyncTask");
		IDataSyncService syncService = (IDataSyncService)context.getBean("dataSyncServiceImpl");

		ExecutorService es = Executors.newCachedThreadPool();
		es.execute(syncTask);		//数据同步线程
		
		int sendThreadNum = 10;
		int resendThreadNum = 3;
		logger.info(" ----- sync thread init ----- ");
		
		for(int i =0 ; i < sendThreadNum; i++){
			Thread t1 = new Thread(new DataSyncThread(syncService));
			es.execute(t1);
		}
		for(int i =0 ; i < resendThreadNum; i++){
			Thread t1 = new Thread(new DataReSyncThread(syncService));
			es.execute(t1);
		}
		logger.info(" ----- sync thread finished ----- ");
	}

}
