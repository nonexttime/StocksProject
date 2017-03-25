package com.Util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.log4j.Logger;

/**
 * 文件工具类
 * @author liwei17.zh
 *
 */
public class FileUtil {
	private static final Logger LOGGER = Logger.getLogger(FileUtil.class);
	/**
	 * 把文件重命名为.后缀suffix的文件
	 * @param source 被重命名的文件
	 * @param suffix 后缀
	 * @return 重命名后的文件
	 */
	public static File rename(File source,String suffix){
		String fname = source.getName();
		int lastDot = fname.lastIndexOf(".");
		lastDot = (lastDot == -1) ? fname.length() : lastDot;
		String newName = fname.substring(0, lastDot) + "."+suffix;
		File dest = new File(source.getParent() + File.separatorChar + newName);
		source.renameTo(dest);
		
		return dest;
	}
	/**
	 * 把文件重命名为.后缀suffix的文件
	 * @param source 被重命名的文件
	 * @param newname 新名字
	 * @return 重命名后的文件
	 */
	public static File newname(File source,String newname){
		File dest = new File(source.getParent() + File.separatorChar + newname);
		source.renameTo(dest);
		return dest;
	}
	
	/**
	 * 文件重命名
	 * @param file 
	 * @param suffix 后缀
	 */
	public static void renameTo(String pathName ,String suffix){
		try {
			File reNameOkfile = new File(pathName);
			String isOk = pathName.substring(pathName.length()-2);
			if("ok".equals(isOk)){
				boolean exists = reNameOkfile.exists();
				if(!exists){
					pathName = pathName.substring(0,pathName.length()-2)+"OK";
					reNameOkfile = new File(pathName);
				}
			}
			reNameOkfile.renameTo(new File(reNameOkfile.getPath()+"."+suffix));
		} catch (Exception e) {
			LOGGER.info("文件："+pathName+"重命名失败。");
		}
	}
	/**
     * gzip 压缩，跟源文件在相同目录中生成.gz文件
     *
     * @param source 源文件
     */
    public static void gzip(File source) {
        LOGGER.info("开始对源文件[" + source.getName() + "]压缩成.gz包");
        String dir = source.getParent();
        File target = new File(dir + File.separatorChar + source.getName() + ".gz");
        FileInputStream fis = null;
        FileOutputStream fos = null;
        GZIPOutputStream gzipOS = null;
        try {
            fis = new FileInputStream(source);
            fos = new FileOutputStream(target);
            gzipOS = new GZIPOutputStream(fos);
            int count;
            byte[] buffer = new byte[1024];
            while ((count = fis.read(buffer, 0, buffer.length)) != -1) {
                gzipOS.write(buffer, 0, count);
            }
            gzipOS.flush();
            gzipOS.close();
            LOGGER.info("成功把源文件[" + source.getName() + "]压缩为.gz包[" + target.getName() + "]");
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
            try {
                if (gzipOS != null) {
                    gzipOS.close();
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
            source.delete();//删除源文件
        }
    }

    /**
     * 解压.gz包
     *
     * @param source 源.gz包
     */
    public static boolean ungzip(File source) {
    	boolean isUngzip = false;
        GZIPInputStream gzipIS = null;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;

        String fileName = source.getName();
        try {
        	fileName = fileName.substring(0, fileName.lastIndexOf("."));
            fis = new FileInputStream(source);
            gzipIS = new GZIPInputStream(fis);
            File target = new File(source.getParent() + File.separatorChar + fileName);
            fos = new FileOutputStream(target);
            bos = new BufferedOutputStream(fos);

            int count;
            byte[] buffer = new byte[1024];
            while ((count = gzipIS.read(buffer, 0, buffer.length)) != -1) {
                bos.write(buffer, 0, count);
            }
            bos.flush();
            isUngzip = true;
        } catch (FileNotFoundException e) {
            LOGGER.error("解压文件【"+source.getPath()+"】找不到", e);
        } catch (Exception e) {
        	 LOGGER.error("解压文件【"+source.getPath()+"】出错", e);
		} finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
            try {
                if (gzipIS != null) {
                    gzipIS.close();
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        
        return isUngzip;
    }
    /**
     * 创建新文件
     * @param dirName 目录路径
     * @param fileName 文件名
     */
    public static void createNewFile(String dirName,String fileName){
    	File newFile = new File(dirName + File.separatorChar + fileName);
    	try {
			newFile.createNewFile();
		} catch (IOException e) {
			LOGGER.error("创建文件失败!"+e.getMessage());
		}
    }
	/**
	 * 移除文件
	 * @param pathName 
	 */
	public static void deleteFile(String pathName  ){
		try {
			File deleteFile = new File(pathName);
			if(deleteFile.isFile()){
				deleteFile.delete();
			}
		} catch (Exception e) {
			LOGGER.info("刪除文件："+pathName+"失败。");
		}
	}
	
	/**
	 * 移除文件目录
	 * @param pathName 
	 */
	public static void deleteDir(String pathName  ){
		try {
			File deleteDir = new File(pathName);
			if(deleteDir.isDirectory()){
				File[] delFiles = deleteDir.listFiles();
				//删除文件目录下的文件
				for (int i = 0; i < delFiles.length; i++) {
					File file = delFiles[i];
					deleteFile(file.getAbsolutePath());
				}
				//删除文件目录
				deleteDir.delete();
			}
		} catch (Exception e) {
			LOGGER.info("刪除文件目录："+pathName+"失败。");
		}
	}
}
