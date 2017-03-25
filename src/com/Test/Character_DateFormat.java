package com.Test;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Character_DateFormat {

	public static void main(String[] args) throws SQLException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String time = "2014-10-11";

		try {
		java.util.Date  timee =  sdf.parse(time);
		System.out.println(timee);
		String ntime = sdf.format(timee);
		System.out.println(ntime);
		} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}

	}
}


/**
结果：

Thu Oct 31 00:00:00 CST 2013
20131031
**/