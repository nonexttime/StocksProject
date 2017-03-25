package com.Service;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.Bean.StockBean;
import com.Util.Constants;
import com.Util.FileRWUtil;
import com.dao.SinaStockDao;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;

/**
 * 
 * @author lw
 * @version 1.0
 * @since 2012-03-29
 *
 */
public class FetchSinaStock extends Thread{
	private static final Logger logger = Logger.getLogger(FetchSinaStock.class);

	private static String db  = Constants.CURRENT_PROJECTPATH + File.separator + "sina-stock-codes.txt" ;
	private static String file_Target = Constants.CURRENT_PROJECTPATH + File.separator + "StocksInfo\\20150811\\sina-stock.txt";
	private static final int COLUMNS = 33;
	private static List<String> codes = new ArrayList<String>() ;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat sdf_day = new SimpleDateFormat("yyyyMMdd");
	private String stockCode_fetch;
	static{
		File in = new File(db) ;
		if(! in.exists()){
			// 从网络获取
			if(codes.size() < 1 )
				try {
					codes = getAllStackCodes() ;
				} catch (IOException e) {
					e.printStackTrace();
				}
		}else{
			// 从本地获取
			if(codes.size() < 1)
				try {
					codes = getAllStockCodesFromLocal() ;
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	public FetchSinaStock(String stockCode_fetch){
		this.stockCode_fetch = stockCode_fetch;
	}
	
	
	public void run(){
		Timer timer = new Timer();
		timer.schedule(new StockTimerTask(), 0,1*1000);
	}
	
	class StockTimerTask extends TimerTask{
		public void run(){
			try{
				FetchStockInfo();
			}catch (Exception e){
				
			}
			
		}
	}
	
	
	public void FetchStockInfo(){
		try {
			logger.info("Here");
			getStockInfoByCode(this.stockCode_fetch);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	// 解析一组股票代码字符串   把code中包括的所有股票代码放入List中
	private static List<String> handleStockCode(String code){
		List<String> codes = null ;
				
		int end = code.indexOf(";") ;
		    code = code.substring(0,end) ;
		int start = code.lastIndexOf("=") ;
		   code = code.substring(start) ;
		   code = code.substring(code.indexOf("\"")+1,code.lastIndexOf("\"")) ;
		   codes = Arrays.asList(code.split(",")) ;
		return codes ;
	}
	
	//   返回的值是一个js代码段  包括指定url页面包含的所有股票代码
	private static String getBatchStackCodes(URL url) throws IOException{
	 	 URLConnection connection = url.openConnection() ;
		 connection.setConnectTimeout(30000) ;
		 BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream())) ;
		 String line = null ;
	   	 StringBuffer sb = new StringBuffer() ;
		boolean flag =false ;
		 while((line = br.readLine()) != null ){
			 if(line.contains("<script type=\"text/javascript\">") || flag){
				 sb.append(line) ;
				 flag = true ;
			 }
			 if(line.contains("</script>")){
				 flag =false ;
				 if(sb.length() > 0 ){
					 if(sb.toString().contains("code_list") && sb.toString().contains("element_list")){
						 break ;
					 }else{
						 sb.setLength(0) ;
					 }
				 }
			 }
		 }
		 if(br != null ){
			 br.close() ;
			 br= null ;
		 }
		return sb.toString() ;
	}
	
	// 获取新浪38也的所有股票代码
	private static List<String> getAllStackCodes() throws IOException{
		List<String> codes = new ArrayList<String>() ;
		int i =1 ;
		URL url = null ;
		// 新浪 股票 好像目前为止就 38页
		for(; i < 39 ; i ++ ){
			 url = new URL("http://vip.stock.finance.sina.com.cn/q/go.php/vIR_CustomSearch/index.phtml?p="+i) ;
			 String code = getBatchStackCodes(url) ;
			 codes.addAll(handleStockCode(code)) ;
		}
		if(! ( new File(db) ).exists() )
			saveStockCodes(codes) ;
		return codes ;
	}
	
	//把新浪38页的所有股票代码存入本地文件
	private static void saveStockCodes(List<String> codes ) throws IOException{
		//将所有股票代码存入文件中
		File out = new File(db) ;
		if(! out.exists())
			out.createNewFile() ;
		BufferedWriter bw = new BufferedWriter(new FileWriter(out)) ;
		for(String code : codes ){
			bw.write(code) ;
			bw.newLine() ;
		}
		if(bw != null ){
			bw.close() ;
			bw = null ;
		}
	}
	
	private static List<String> getAllStockCodesFromLocal() throws IOException{
		List<String> codes = new ArrayList<String>() ;
		File in = new File(db) ;
		if(! in.exists())
			throw new IOException("指定数据文件不存在!");
		BufferedReader br = new BufferedReader(new FileReader(in)) ;
		String line = null ;
		while( ( line = br.readLine() ) != null ){
			codes.add(line) ;
		}
		// 删除最后一个空行
		codes.remove(codes.size()-1) ;
		if(br != null ){
			br.close() ;
			br = null ;
		}
		return codes ;
	}
	
	public static String[]  getStockInfoByCode(String stockCode) throws IOException{
		// 仅仅打印
		 String[] stockInfo = new String[COLUMNS] ; 
		 URL url = new URL("http://hq.sinajs.cn/?list="+stockCode) ;
		 URLConnection connection = url.openConnection() ;
		 connection.setConnectTimeout(16000) ;
		 BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream())) ;
		 String line = null ;
		 StringBuffer sb = new StringBuffer() ;
		 while(( line = br.readLine()) != null ){
			 sb.append(line) ;
		 }
		 if(sb.length() > 0 ){
			 String rs = sb.toString() ;
			 rs = rs.substring(rs.indexOf("\"")+1,rs.lastIndexOf("\"")) ;
			 String[] rss = rs.split(",") ;
			 
			 //rss[0] = URLEncoder.encode(rss[0],"UTF8");
			 //String str_utf8 = new String(rss[0].getBytes("UTF-8"),"UTF-8");
			 //String str_utf8 = URLEncoder.encode(rss[0],"GBK");

			 //byte[] convertBytes = rss[0].getBytes("UTF-8");
			 //String str_utf8 = new String(convertBytes,"UTF-8");
			 
/**
股票代码	|股票名字		|今日开盘价		|昨日收盘价	|当前价格		|今日最高价	|今日最低价	|竟买价	|竞卖价	|
sh600837	海通证券	16.58			16.49		17.70		18.07		16.40		17.69	17.70		 
成交的股票数	|成交金额(元)	|买一	|买一	|买二	|买二	|买三	|买三	|买四	|买四	|买五	|
269304470	4640131498	37100	17.69	243227	17.68	312514	17.67	117300	17.66	257558	
买五		|卖一	|卖一	|卖二	|卖二	|卖三	|卖三	|卖四	|卖四	|卖五	|卖五	|日期		|时间		|
17.65	172056	17.70	417736	17.71	156076	17.72	252300	17.73	136117	17.74	2015-08-10	15:04:11	00	
*/
			 StockBean stock = new StockBean();
			 stock.setStockCode(stockCode);
			 stock.setStockName(rss[0]);			//股票名字
			 stock.setStockOpenPrice(Double.parseDouble(rss[1]));		//今日开盘价
			 stock.setStockYesClosePrice(Double.parseDouble(rss[2]));	//昨日收盘价
			 stock.setCurrentPrice(Double.parseDouble(rss[3]));		//当前价格
			 stock.setTodayHighestPrice(Double.parseDouble(rss[4]));	//今日最高价
			 stock.setTodayLowestPrice(Double.parseDouble(rss[5]));	//今日最低价
			 stock.setBidBuyPrice(Double.parseDouble(rss[6]));			//竟买价
			 stock.setBidSellPrice(Double.parseDouble(rss[7]));		//竞卖价
			 stock.setDealStockAmount(Double.parseDouble(rss[8]));		//成交的股票数
			 stock.setDealPrice(Double.parseDouble(rss[9]));			//成交金额(元)
			 stock.setBuy1(Double.parseDouble(rss[10]));				//买1(价)
			 stock.setBuy1_Num(Double.parseDouble(rss[11]));			//买1(量)
			 stock.setBuy2(Double.parseDouble(rss[12]));				//买2(价)
			 stock.setBuy2_Num(Double.parseDouble(rss[13]));			//买2(量)
			 stock.setBuy3(Double.parseDouble(rss[14]));				//买3(价)
			 stock.setBuy3_Num(Double.parseDouble(rss[15]));			//买3(量)
			 stock.setBuy4(Double.parseDouble(rss[16]));				//买4(价)
			 stock.setBuy4_Num(Double.parseDouble(rss[17]));			//买4(量)
			 stock.setBuy5(Double.parseDouble(rss[18]));				//买5(价)
			 stock.setBuy5_Num(Double.parseDouble(rss[19]));			//买5(量)
			 stock.setSell1(Double.parseDouble(rss[20]));				//卖1
			 stock.setSell1_Num(Double.parseDouble(rss[21]));			//卖1(量)
			 stock.setSell2(Double.parseDouble(rss[22]));				//卖2
			 stock.setSell2_Num(Double.parseDouble(rss[23]));			//卖2(量)
			 stock.setSell3(Double.parseDouble(rss[24]));				//卖3
			 stock.setSell3_Num(Double.parseDouble(rss[25]));			//卖3(量)
			 stock.setSell4(Double.parseDouble(rss[26]));				//卖4
			 stock.setSell4_Num(Double.parseDouble(rss[27]));			//卖4(量)
			 stock.setSell5(Double.parseDouble(rss[28]));				//卖5
			 stock.setSell5_Num(Double.parseDouble(rss[29]));			//卖5(量)

			 stock.setCurrentDate(Timestamp.valueOf(rss[30] + " " + rss[31]));
			 /**之前是 CurrentDay DATE, CurrentTime TIME，后改为VARCHAR **/
			 //stock.setCurrentDay(sdf_day.format(sdf_day.parse(rss[30])));
			 //stock.setCurrentTime(rss[31]);
			 stock.setCurrentDay(rss[30]);
			 stock.setCurrentTime(rss[31]);

			 
			 String content = "";
			 
			 content += stockCode + "|";
			 for(int i = 0 ;  i< rss.length ; i++ ){
				 content += rss[i] + "|";
			 }
			 content+="\n";
			 //System.out.println(content);
			 
			 if("0".equals(Constants.FETCH_DATA_SAVE)){
				 FileRWUtil.appendMethodA(file_Target, content);
			 }else if("1".equals(Constants.FETCH_DATA_SAVE)){
				 SinaStockDao.insertStockInfo(stock);
			 }
			 
			 
		 }
		 return stockInfo ;
	}
	
	public static void getAllStockInfo() throws IOException{
		String[] header = getHeaders() ;
		/*
		System.out.println(header.length);
		for(int i = 0 ; i < header.length ;  i++ ){
			System.out.print(header[i]+"\t|");
		}*/
		getStockInfoByCode("sh600837");
		/*
		for(String code : codes ){
			System.out.println("Code:" + code);
			//getStockInfoByCode(code) ;
		}*/
		
	}
	
	/**
	 * 
	 * @param first 从0开始
	 * @param last  不包括 last
	 * @return
	 */
	public static List<String[]> getStockInfo(int first , int last , int recoeds)throws Exception{
		List<String[]> stockInfo = new ArrayList<String[]>() ;
		first = first < 0 ? 0 : first ;
		if(first > last )
			throw new Exception("参数不合法!") ;
		int i = 0 ;
		while(last > codes.size()  ){
			if(first + recoeds < codes.size()+1 ){
				last = first +  recoeds ;
				break ;
			}else{
				last = first + recoeds +(--i) ;
			}
		}
		for( i = first ; i <= last ; i ++ ){
			stockInfo.add(getStockInfoByCode(codes.get(i))) ;
		}
		return stockInfo ;
	}
	
	public static String[] getHeaders(){
		String[] header = {"股票名字","今日开盘价	","昨日收盘价","当前价格","今日最高价","今日最低价","竟买价","竞卖价","成交的股票数","成交金额(元)","买一","买一","买二","买二","买三","买三","买四","买四","买五","买五","卖一","卖一","卖二","卖二","卖三","卖三","卖四","卖四","卖五","卖五","日期","时间"} ;
		return header ;
	}	
	
	public static List<String> getStockCodes(){
		return codes ;
	} 
	
}