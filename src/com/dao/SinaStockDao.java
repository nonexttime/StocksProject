package com.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.Bean.StockBean;
import com.Util.DBConnectionPool;
import com.Util.Constants;
import com.Util.ConnectionDB;

public class SinaStockDao {
	private static final Logger logger = Logger.getLogger(SinaStockDao.class);
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat sdf_time = new SimpleDateFormat("HH:mm:ss");

	
	/**
	 * Function:在GP中插入StockBean，单条
	 * 
	 */
	public static boolean insertStockInfo(StockBean stockinfo)
	{
		ConnectionDB conDB = null;
		Connection conn = null;
		Statement cnstmt = null;
		ResultSet cnrs = null;
		PreparedStatement cnpst = null;
		
		try{
			conDB = new ConnectionDB();
			conn = conDB.getConnection(Constants.DB_POOL_P9);		
			cnstmt = conn.createStatement();
			
    		
			String insertSql = "insert into dzyh_app.tbs_stockinfo values("
					+ "?,?,?,?,?,?,"
					+ "?,?,?,?,?,"
					+ "?,?,?,?,?,"
					+ "?,?,?,?,?,"
					+ "?,?,?,?,?,"
					+ "?,?,?,?,?,"
					+ "to_timestamp(?,'yyyy-mm-dd hh24:mi:ss'),?,?)";
			PreparedStatement ps = conn.prepareStatement(insertSql);
			ps.setString(1, stockinfo.getStockCode());          //sh600837
			ps.setString(2, stockinfo.getStockName());			//海通证券
			ps.setDouble(3, stockinfo.getStockOpenPrice());		//16.58
			ps.setDouble(4, stockinfo.getStockYesClosePrice());
			ps.setDouble(5, stockinfo.getCurrentPrice());		//16.49
			ps.setDouble(6, stockinfo.getTodayHighestPrice());	//17.70
			ps.setDouble(7, stockinfo.getTodayLowestPrice());	//18.07
			ps.setDouble(8, stockinfo.getBidBuyPrice());		//16.40
			ps.setDouble(9, stockinfo.getBidSellPrice());		//17.69
			ps.setDouble(10, stockinfo.getDealStockAmount());	//269304470
			ps.setDouble(11, stockinfo.getDealPrice());			//4640131498
			ps.setDouble(12, stockinfo.getBuy1_Num());				//成交量4640131498
			ps.setDouble(13, stockinfo.getBuy1());
			ps.setDouble(14, stockinfo.getBuy2_Num());
			ps.setDouble(15, stockinfo.getBuy2());
			ps.setDouble(16, stockinfo.getBuy3_Num());
			ps.setDouble(17, stockinfo.getBuy3());
			ps.setDouble(18, stockinfo.getBuy4_Num());
			ps.setDouble(19, stockinfo.getBuy4());
			ps.setDouble(20, stockinfo.getBuy5_Num());
			ps.setDouble(21, stockinfo.getBuy5());
			ps.setDouble(22, stockinfo.getSell1_Num());
			ps.setDouble(23, stockinfo.getSell1());
			ps.setDouble(24, stockinfo.getSell2_Num());
			ps.setDouble(25, stockinfo.getSell2());
			ps.setDouble(26, stockinfo.getSell3_Num());
			ps.setDouble(27, stockinfo.getSell3());
			ps.setDouble(28, stockinfo.getSell4_Num());
			ps.setDouble(29, stockinfo.getSell4());
			ps.setDouble(30, stockinfo.getSell5_Num());
			ps.setDouble(31, stockinfo.getSell5());
			
			//转成 yyyy-MM-dd HH:mm:ss
			ps.setTimestamp(32,stockinfo.getCurrentDate());
			System.out.println("VAR:" + stockinfo.getCurrentDay());

			/**之前是 CurrentDay DATE, CurrentTime TIME，后改为VARCHAR **/
			ps.setString(33,stockinfo.getCurrentDay());
			//ps.setTime(33, new Time(sdf_time.parse(stockinfo.getCurrentTime()).getTime()));
			ps.setString(34, stockinfo.getCurrentTime());
			
			logger.info("SQL" + insertSql);
			Boolean return_bool =  ps.execute();
			logger.info("BOOL:" + return_bool);

    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	finally{
    		
    	}
		
    	
		return false;
	}
	
	
	
	/**
	 * Function:在GP中对StockBean的文件进行入库
	 * 
	 */

}
