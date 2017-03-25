package com.Basic;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

public class StockInfo {
	public static void main (String[] args){
		String url = "http://hq.sinajs.cn/list=sh600151,sz000830";
		try{
			URL u = new URL(url);
			byte[] b = new byte[256];
			InputStream in = null;
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			while(true){
				try{
					in = u.openStream();
					int i;
					while((i = in.read(b))!= -1){
						bo.write(b,0,i);
					}
					String result = bo.toString();
					String[] stocks = result.split(";");
					for(String stock:stocks){
						String[] datas = stock.split(",");
						//根据对照自己对应数据
						System.out.println(datas.length);
//						System.out.println(datas[0].toString());
//						System.out.println(datas[1].toString());
//						System.out.println(datas[2].toString());
						
						for(int j =0 ;j < datas.length;i++){
							System.out.print(datas[j].toString() + "\t");
						}
						System.out.println();
						break;
					}
					bo.reset();
				}catch(Exception e){
					System.out.println(e.getStackTrace());
				}finally{
					if(in != null){
						in.close();
					}
				}
		}
		}catch(Exception e2){
			System.out.println(e2.getMessage());
		}
	}
}
