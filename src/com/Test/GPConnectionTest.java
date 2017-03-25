package com.Test;

import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.Basic.Main.FetchSinaStockThread;
import com.Service.FetchSinaStockALL;

import com.Util.DBConnectionPool;
import com.Util.Constants;
import com.Util.ConnectionDB;



public class GPConnectionTest {
	private static Log logger = LogFactory.getLog(GPConnectionTest.class);

	public static void main(String[] args) throws Exception{
		logger.info("------GPConnectio但是nTest Start--------");
		ConnectionDB conDB = null;
		Connection conn = null;
		Statement cnstmt = null;
		ResultSet cnrs = null;
		PreparedStatement cnpst = null;
		
		try {
			logger.info("[VistorSourceStatistic]获取数据库连接实例");
			conDB = new ConnectionDB();
			conn = conDB.getConnection(Constants.DB_POOL_P9);		
			cnstmt = conn.createStatement();
			
			String insertSql = "insert into dzyh_app.student values(?,?,?)";
			PreparedStatement ps = conn.prepareStatement(insertSql);
			ps.setString(1, "1");
			ps.setString(2, "zacks");
			ps.setTimestamp(3, new Timestamp(new java.util.Date().getTime()));
			
			//Boolean return_bool =  ps.execute();
			//logger.info("BOOL:" + return_bool);
			
			String pageSql = null;
			pageSql = "select * from dzyh_app.student";
			cnrs = cnstmt.executeQuery(pageSql);
			cnrs.next();
			String ID = cnrs.getString(1);
			String Name = cnrs.getString(2);
			String timedate = cnrs.getString(3);
			logger.info("ID:" + ID + "\tName:" + Name + "\tCurrentTime: " + timedate);

		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			cnrs.close();
			cnstmt.close();
			conn.close();
			conDB.free();
		}
	}
	
}
