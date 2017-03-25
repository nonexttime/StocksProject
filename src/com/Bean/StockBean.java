package com.Bean;

import java.sql.Timestamp;
import java.util.Date;

public class StockBean {
	private String stockCode;			//股票代码
	private String stockName;			//股票名字
	private double stockOpenPrice;		//今日开盘价
	private double stockYesClosePrice;	//昨日收盘价
	private double currentPrice;		//当前价格
	private double todayHighestPrice;	//今日最高价
	private double todayLowestPrice;	//今日最低价
	private double bidBuyPrice;			//竟买价
	private double bidSellPrice;		//竞卖价
	private double dealStockAmount;		//成交的股票数
	private double dealPrice;			//成交金额(元)
	private double Buy1;				//买1(价)
	private double Buy1_Num;			//买1(量)
	private double Buy2;				//买2(价)
	private double Buy2_Num;			//买2(量)
	private double Buy3;				//买3(价)s
	private double Buy3_Num;			//买3(量)
	private double Buy4;				//买4(价)
	private double Buy4_Num;			//买4(量)
	private double Buy5;				//买5(价)
	private double Buy5_Num;			//买5(量)
	private double Sell1;				//卖1
	private double Sell1_Num;			//卖1(量)
	private double Sell2;				//卖2
	private double Sell2_Num;			//卖2(量)
	private double Sell3;				//卖3
	private double Sell3_Num;			//卖3(量)
	private double Sell4;				//卖4
	private double Sell4_Num;			//卖4(量)
	private double Sell5;				//卖5
	private double Sell5_Num;			//卖5(量)
	private Timestamp currentDate;			//当前日期
	private String currentDay;			//当前日 YYYYMMDD
	private String currentTime;			//当前时间HH:MM:SS
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	
	public double getDealStockAmount() {
		return dealStockAmount;
	}
	public void setDealStockAmount(double dealStockAmount) {
		this.dealStockAmount = dealStockAmount;
	}
	public double getDealPrice() {
		return dealPrice;
	}
	public void setDealPrice(double dealPrice) {
		this.dealPrice = dealPrice;
	}
	public double getBuy1() {
		return Buy1;
	}
	public void setBuy1(double buy1) {
		Buy1 = buy1;
	}
	public double getBuy1_Num() {
		return Buy1_Num;
	}
	public void setBuy1_Num(double buy1_Num) {
		Buy1_Num = buy1_Num;
	}
	public double getBuy2() {
		return Buy2;
	}
	public void setBuy2(double buy2) {
		Buy2 = buy2;
	}
	public double getBuy2_Num() {
		return Buy2_Num;
	}
	public void setBuy2_Num(double buy2_Num) {
		Buy2_Num = buy2_Num;
	}
	public double getBuy3() {
		return Buy3;
	}
	public void setBuy3(double buy3) {
		Buy3 = buy3;
	}
	public double getBuy3_Num() {
		return Buy3_Num;
	}
	public void setBuy3_Num(double buy3_Num) {
		Buy3_Num = buy3_Num;
	}
	public double getBuy4() {
		return Buy4;
	}
	public void setBuy4(double buy4) {
		Buy4 = buy4;
	}
	public double getBuy4_Num() {
		return Buy4_Num;
	}
	public void setBuy4_Num(double buy4_Num) {
		Buy4_Num = buy4_Num;
	}
	public double getBuy5() {
		return Buy5;
	}
	public void setBuy5(double buy5) {
		Buy5 = buy5;
	}
	public double getBuy5_Num() {
		return Buy5_Num;
	}
	public void setBuy5_Num(double buy5_Num) {
		Buy5_Num = buy5_Num;
	}

	public double getSell1() {
		return Sell1;
	}
	public void setSell1(double sell1) {
		Sell1 = sell1;
	}
	public double getSell1_Num() {
		return Sell1_Num;
	}
	public void setSell1_Num(double sell1_Num) {
		Sell1_Num = sell1_Num;
	}
	public double getSell2() {
		return Sell2;
	}
	public void setSell2(double sell2) {
		Sell2 = sell2;
	}
	public double getSell2_Num() {
		return Sell2_Num;
	}
	public void setSell2_Num(double sell2_Num) {
		Sell2_Num = sell2_Num;
	}
	public double getSell3() {
		return Sell3;
	}
	public void setSell3(double sell3) {
		Sell3 = sell3;
	}
	public double getSell3_Num() {
		return Sell3_Num;
	}
	public void setSell3_Num(double sell3_Num) {
		Sell3_Num = sell3_Num;
	}
	public double getSell4() {
		return Sell4;
	}
	public void setSell4(double sell4) {
		Sell4 = sell4;
	}
	public double getSell4_Num() {
		return Sell4_Num;
	}
	public void setSell4_Num(double sell4_Num) {
		Sell4_Num = sell4_Num;
	}
	public double getSell5() {
		return Sell5;
	}
	public void setSell5(double sell5) {
		Sell5 = sell5;
	}
	public double getSell5_Num() {
		return Sell5_Num;
	}
	public void setSell5_Num(double sell5_Num) {
		Sell5_Num = sell5_Num;
	}
	public String getCurrentDay() {
		return currentDay;
	}
	public void setCurrentDay(String currentDay) {
		this.currentDay = currentDay;
	}
	public String getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}
	public String getStockCode() {
		return stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	public double getStockOpenPrice() {
		return stockOpenPrice;
	}
	public void setStockOpenPrice(double stockOpenPrice) {
		this.stockOpenPrice = stockOpenPrice;
	}
	public double getStockYesClosePrice() {
		return stockYesClosePrice;
	}
	public void setStockYesClosePrice(double stockYesClosePrice) {
		this.stockYesClosePrice = stockYesClosePrice;
	}
	public double getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}
	public double getTodayHighestPrice() {
		return todayHighestPrice;
	}
	public void setTodayHighestPrice(double todayHighestPrice) {
		this.todayHighestPrice = todayHighestPrice;
	}
	public double getTodayLowestPrice() {
		return todayLowestPrice;
	}
	public void setTodayLowestPrice(double todayLowestPrice) {
		this.todayLowestPrice = todayLowestPrice;
	}
	public double getBidBuyPrice() {
		return bidBuyPrice;
	}
	public void setBidBuyPrice(double bidBuyPrice) {
		this.bidBuyPrice = bidBuyPrice;
	}
	public double getBidSellPrice() {
		return bidSellPrice;
	}
	public void setBidSellPrice(double bidSellPrice) {
		this.bidSellPrice = bidSellPrice;
	}
	public Timestamp getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(Timestamp currentDate) {
		this.currentDate = currentDate;
	}

}
