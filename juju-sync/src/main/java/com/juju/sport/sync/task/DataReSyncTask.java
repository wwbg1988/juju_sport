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
 * 错误数据重新发送
 * @author rkzhang
 */
@Service
public class DataReSyncTask  extends TimerTask {

	protected static final Log logger = LogFactory.getLog(DataSyncTask.class);

	private static final Integer MAX_RECORD = 100;
	
	@Autowired
	private IDataSyncService syncService;

	@Override
	public void run() {
		logger.debug("DBSync resend task start");
		try {
			send();
		} catch (Throwable e) {
			throw new SystemException(e);
		}
		logger.debug("DBSync resend task end");
	}
	
	private void send(){		
		int all = syncService.countNeedReSendData();
		int start = 0;
		List<DataInfo> infos = syncService.findNeedReSendData(start, MAX_RECORD);
		while(!CollectionUtils.isEmpty(infos)){
			process(infos);
			start = start + MAX_RECORD;
			if(start < all){
				infos = syncService.findNeedReSendData(start, MAX_RECORD);
			}else{
				break;
			}
		}
	}

	private void process(List<DataInfo> infos) {
		CountDownLatch countDownLatch = new CountDownLatch(infos.size());
		DataInfoQueue.setResendQueueLatch(countDownLatch);
		for(DataInfo info : infos){
			DataInfoQueue.putNeedToReSendData(info);
		}
		try {
			DataInfoQueue.getResendQueueLatch().await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		DataInfoQueue.setResendQueueLatch(null);
	}
}
