package com.Test;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import com.Util.Constants;

public class File_CreateFilePath {
	private static String currentPath = System.getProperty("user.dir");
	private static String file_Target = Constants.CURRENT_PROJECTPATH + File.separator + "StocksInfo\\sina-stock.txt";

	public static void main(String[] args) throws SQLException {
		String fileName = "O:\\MyProjects\\StocksTest\\StocksInfo\\20150810\\sina-stock.txt";
    	String[] array = fileName.split("\\\\");
    	System.out.println(array[1]);
    	
    	System.out.println(fileName.lastIndexOf("\\"));
    	//$$44
    	System.out.println(fileName.substring(0, fileName.lastIndexOf("\\")));
    	//$$O:\MyProjects\StocksTest\StocksInfo\20150810
		File file = new File(fileName);
    	if(!file.exists()){
    		try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}	
    	
    	
    	
    	File file2=new File("C:\\Users\\QPING\\Desktop\\JavaScript\\2.htm");  
        if(!file.exists())  
        {  
            try {  
                file.createNewFile();  
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }  
        
        File file3 =new File("C:\\Users\\QPING\\Desktop\\JavaScript");  
      //如果文件夹不存在则创建  
      if  (!file .exists()  && !file .isDirectory())    
      {     
          System.out.println("//不存在");
          file .mkdir();  
      } else 
      {
          System.out.println("//目录存在");
      }
    	
    	

	}
}
