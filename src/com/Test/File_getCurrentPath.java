package com.Test;

import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.Util.FilePath;
import com.Util.Constants;
public class File_getCurrentPath {
	private static String currentPath = System.getProperty("user.dir");
	private static String file_Target = Constants.CURRENT_PROJECTPATH + File.separator + "StocksInfo\\sina-stock.txt";

	public static void main(String[] args) throws SQLException {
		//System.out.println( System.getProperty("java.class.path")); 
		/*
	      */ 
		System.out.println("CURRENT_PROJECT_PATH" + Constants.CURRENT_PROJECTPATH);
		System.out.println("CURRENT PATH:" + currentPath);
		System.out.println(System.getProperty("user.dir"));
		System.out.println(Constants.FETCH_DATA_SAVE);
		
		
		System.out.println("StockPath:" + file_Target);
		//FilePath fp = new FilePath();
		//System.out.println(fp.getCurrentPath());

	}
	

}
