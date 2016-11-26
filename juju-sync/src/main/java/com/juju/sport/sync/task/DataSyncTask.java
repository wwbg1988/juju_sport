package com.juju.sport.sync.task;

import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.common.exception.SystemException;
import com.juju.sport.sync.model.DataInfo;
import com.juju.sport.sync.queue.DataInfoQueue;
import com.juju.sport.sync.service.IDataSyncService;


/**
 * 正常数据发送任务
 * @author rkzhang
 */
@Service
public class DataSyncTask  extends TimerTask {

	protected static final Log logger = LogFactory.getLog(DataSyncTask.class);
	
	private static final int MAX_RECORD = 100;
	
	@Autowired
	private IDataSyncService syncService;

	@Override
	public void run() {
		while(true){
		logger.debug("DBSync send task start");		
			try {
				send();
			} catch (Throwable e) {
				throw new SystemException(e);
			} finally {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		logger.debug("DBSync send task end");
		}
	}
	
	private void send(){
		List<DataInfo> infos = syncService.findNeedSendData(MAX_RECORD);
		while(!CollectionUtils.isEmpty(infos)){
			process(infos);
			infos = syncService.findNeedSendData(MAX_RECORD);
		}
	}

	private void process(List<DataInfo> infos) {
		CountDownLatch countDownLatch = new CountDownLatch(infos.size());
		DataInfoQueue.setSendQueueLatch(countDownLatch);
		for(DataInfo info : infos){
			DataInfoQueue.putNeedToSendData(info);
		}
		try {
			DataInfoQueue.getSendQueueLatch().await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		DataInfoQueue.setSendQueueLatch(null);
	}
}
