package com.juju.sport.sync.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.juju.sport.sync.model.DataInfo;

public class DataInfoQueue {

	protected static final Log logger = LogFactory.getLog(DataInfoQueue.class);

	private static CountDownLatch sendQueueLatch;
	
	private static CountDownLatch resendQueueLatch;
	
	private static ArrayBlockingQueue<DataInfo> sendQueue = new ArrayBlockingQueue<DataInfo>(1000);
	
	private static ArrayBlockingQueue<DataInfo> reSendQueue = new ArrayBlockingQueue<DataInfo>(1000);
	
	public static DataInfo getNeedToSendData(){
		return  sendQueue.poll();
	}
	
	public static void putNeedToSendData(DataInfo record){
		try {
			sendQueue.put(record);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static DataInfo getNeedToReSendData(){
		return  reSendQueue.poll();
	}
	
	public static void putNeedToReSendData(DataInfo record){
		try {
			reSendQueue.put(record);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static CountDownLatch getSendQueueLatch() {
		return sendQueueLatch;
	}

	public static void setSendQueueLatch(CountDownLatch sendQueueLatch) {
		DataInfoQueue.sendQueueLatch = sendQueueLatch;
	}

	public static CountDownLatch getResendQueueLatch() {
		return resendQueueLatch;
	}

	public static void setResendQueueLatch(CountDownLatch resendQueueLatch) {
		DataInfoQueue.resendQueueLatch = resendQueueLatch;
	}

	public static ArrayBlockingQueue<DataInfo> getSendQueue() {
		return sendQueue;
	}

	public static void setSendQueue(ArrayBlockingQueue<DataInfo> sendQueue) {
		DataInfoQueue.sendQueue = sendQueue;
	}

	public static ArrayBlockingQueue<DataInfo> getReSendQueue() {
		return reSendQueue;
	}

	public static void setReSendQueue(ArrayBlockingQueue<DataInfo> reSendQueue) {
		DataInfoQueue.reSendQueue = reSendQueue;
	}
}
