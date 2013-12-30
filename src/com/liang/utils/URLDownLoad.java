package com.liang.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class URLDownLoad {
	
	public String downLoadFile(String urlStr){
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader buffer = null;
		URL url;
		try {
			url = new URL(urlStr);
			HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
			buffer = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
			while((line = buffer.readLine())!=null){
				sb.append(line);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				buffer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	private FileUtils mFileUtils;
    
    private int progress;    

    public int getProgress() {
        return progress;
    }
    
    public enum DownloadState {
        NOTSTART,DOWNLOADING,DOWNLOADFAILED,DOWNLOADSUCCESS
    };
    private DownloadState downloadState=DownloadState.NOTSTART;
    public DownloadState getDownloadState(){
        return downloadState;
    }
        
    

    public URLDownLoad(){
        progress=0;
        downloadState=DownloadState.NOTSTART;
        mFileUtils=new FileUtils();
    }
    
    
    public boolean downloadFile(String url, String filePath) {
        return downloadFile(url, null, null, null, null, null, filePath);
    }

    
    public boolean downloadFile(String url, String session, String method,
                    String contentType, String charset, byte[] postData,
                    String filePath) {
        
        downloadState=DownloadState.DOWNLOADING;
        
        
        
        final String GETMETHOD = "GET";
        final String POSTMETHOD = "POST";
        // final String UTF8 = "utf-8";
        // final String APPLICATION =
        // "application/x-www-form-urlencoded";
        // final String MULTIPART = "multipart/form-data; boundary=";

        boolean result = false;
        HttpURLConnection conn = null;
        InputStream is = null;

        if (method == null) {
            method = GETMETHOD;
        }

        try {
            conn = (HttpURLConnection) new URL(url)
                            .openConnection();

            conn.setRequestMethod(method);
            if (session != null
                            && !(session = session.trim())
                                            .equals("")) {
                conn.setRequestProperty("Cookie",
                                "ASP.NET_SessionId=" + session);
            }
            if (contentType != null
                           && !(contentType = contentType.trim())
                                            .equals("")) {
                conn.setRequestProperty("Content-Type",
                                contentType);
            }
            if (method.equals(POSTMETHOD))
                conn.setDoOutput(true);

            conn.connect();
            if (postData != null && method.equals(POSTMETHOD)) {
                DataOutputStream out = new DataOutputStream(
                                conn.getOutputStream());
                out.write(postData);

                out.flush();
                out.close();
                out = null;
            }
            is = conn.getInputStream();
            int fileSize = conn.getContentLength();
            if (fileSize <= 0) {
                throw new Exception("无法获取文件大小");
            }
            if (is == null) {
                throw new Exception("无法获取InputStream");
            }

            System.out.println("filePath:"+filePath+"/n"+"fileSize:"+fileSize);
            result = writeToFileFromInputStream(filePath,
                            is,fileSize);
            System.out.println("writeToFile:"+result);

        } catch (Exception e) {
            e.printStackTrace();            
            result = false;
        } finally {
            try {
                if (conn != null) {
                    conn.disconnect();
                    conn = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (result) {
            downloadState=DownloadState.DOWNLOADSUCCESS;
            progress=100;
        } else {
            downloadState=DownloadState.DOWNLOADFAILED;
            progress=-1;
        }
        return result;

    }
    
    
    
    public boolean writeToFileFromInputStream(String fileName,
                    InputStream is,int fileSize) {
        
        progress=0;
        
        if (fileName == null || (fileName = fileName.trim()).equals("")) {
            progress=-1;
            return false;
        }
        if (is==null){
            progress=-1;
            return false;
        }
        
        if (fileSize<=0) {
            progress=-1;
            return false;
        }

        // 先创建文件夹，否则不会成功
        File parentFile = new File(fileName).getParentFile();
        System.out.println((new File("liang")).isFile());
        System.out.println(parentFile.isFile());
        if (!mFileUtils.createOrExistsFolder(parentFile)) {
            // 如果父文件夹创建不成功，返回false
        	System.out.println("不能创建文件夹");
            progress=-1;
            return false;
        }
        System.out.println("文件夹创建成功");
        boolean result = false;
        File file = null;
        OutputStream os = null;
        try {            
            file = new File(fileName);
            os = new FileOutputStream(file);
            byte buffer[] = new byte[4 * 1024];
            long downloadFileSize=0;
            
            int length = 0;
            while ((length = is.read(buffer)) != -1) {
                os.write(buffer, 0, length);
                downloadFileSize+=length;
                long _progress=downloadFileSize*100/fileSize;    
                if (_progress>=100) {
                    _progress=100;
                }
                progress=(int)_progress;
                
            }
            os.flush();
            result = true;            
        } catch (Exception e) {
            e.printStackTrace();
            file = null;
            result = false;            
        } finally {
            try {
                os.close();

            } catch (Exception e2) {
                e2.printStackTrace();
                file = null;

            }
        }
        if (result) {
            progress=100;
        } else {
            progress=-1;
        }
        return result;
    }

}
