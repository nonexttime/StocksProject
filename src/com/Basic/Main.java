package com.Basic;

import org.apache.log4j.Logger;

import com.Service.FetchSinaStock;
import com.Service.FetchSinaStockALL;
import com.dao.SinaStockDao;

import com.Util.Constants;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
	private static final Logger logger = Logger.getLogger(Main.class);

	
	public static void main(String[] args) throws Exception{
		//SinaStockDao sinastockDao = new SinaStockDao();

		//sinastockDao.insertStockInfo(, "abc");
		
		logger.info("--------------Main Started----------");
		FetchSinaStockThread thread_fetch = new FetchSinaStockThread();
		thread_fetch.start();
		
		
		/*
		logger.info("--------------Main Started----------");
		ExecutorService pool = Executors.newFixedThreadPool(Integer.parseInt(Constants.POOLSIZE_MAX));
		Thread t1 = new FetchSinaStock("sh600837");
		pool.execute(t1);
		*/
		
	}
	
	public static class FetchSinaStockThread extends Thread{
		public FetchSinaStockThread(){}
		public void run(){
			FetchSinaStockALL fetchStockThread = new FetchSinaStockALL();
			try{
				fetchStockThread.run();
			}catch(Exception e){
				
			}
		}
	}
	

}
