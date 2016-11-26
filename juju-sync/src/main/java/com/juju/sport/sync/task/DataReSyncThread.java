package com.juju.sport.sync.task;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.juju.sport.common.exception.SystemException;
import com.juju.sport.sync.model.DataInfo;
import com.juju.sport.sync.queue.DataInfoQueue;
import com.juju.sport.sync.service.IDataSyncService;

public class DataReSyncThread implements Runnable{

	protected static final Log logger = LogFactory.getLog(DataReSyncThread.class);

	private static final long SYNC_SPEED = 100;
	
	public DataReSyncThread(IDataSyncService syncService) {
		 this.syncService = syncService;
	}

	@Setter
	@Getter
	private IDataSyncService syncService;
	
	@Override
	public void run() {
		while(true){
			try{
				sendData();
			} catch (Throwable e) {
				throw new SystemException(e);
			}
		}
		
	}

	private void sendData() {
		
		DataInfo dataInfo = DataInfoQueue.getNeedToReSendData();
		if(dataInfo != null){
			try {
				syncService.syncData(dataInfo);
				logger.debug(" send task record --- " + dataInfo);
				
			} catch (Throwable e) {
				throw new SystemException(e);
			}finally{
				DataInfoQueue.getResendQueueLatch().countDown();
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
