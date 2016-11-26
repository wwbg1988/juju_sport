package com.juju.sport.sync.task;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.juju.sport.common.exception.SystemException;
import com.juju.sport.sync.model.DataInfo;
import com.juju.sport.sync.queue.DataInfoQueue;
import com.juju.sport.sync.service.IDataSyncService;

public class DataSyncThread implements Runnable{
	
	protected static final Log logger = LogFactory.getLog(DataSyncThread.class);

	private static final long SYNC_SPEED = 100;

	public DataSyncThread(IDataSyncService syncService) {
		 this.syncService = syncService;
	}
	
	@Setter
	@Getter
	private IDataSyncService syncService;
	
	@Override
	public void run() {
		while(true){
			try{
				sendDate();
			} catch (Throwable e) {
				e.printStackTrace();
				throw new SystemException(e);
			}
		}
	}


	private void sendDate() {
		DataInfo dataInfo = DataInfoQueue.getNeedToSendData();
		if(dataInfo != null){
			try {
				syncService.syncData(dataInfo);
				logger.debug(" send task record --- " + dataInfo);
				
			} catch (Throwable e) {
				e.printStackTrace();
				throw new SystemException(e);
			}finally{
				DataInfoQueue.getSendQueueLatch().countDown();
				try {
					Thread.sleep(SYNC_SPEED);
				} catch (InterruptedException e) {
					throw new SystemException(e);
				}
			}
		}else{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				throw new SystemException(e);
			}
		}
	}

}
