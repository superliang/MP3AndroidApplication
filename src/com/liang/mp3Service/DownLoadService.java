package com.liang.mp3Service;

import com.liang.entity.MP3Info;
import com.liang.utils.URLDownLoad;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DownLoadService extends Service{

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		MP3Info mp3Info = (MP3Info) intent.getSerializableExtra("mp3Info");
		DownloadThread downloadThread = new DownloadThread(mp3Info);
		Thread thread = new Thread(downloadThread);
		thread.start();
		return super.onStartCommand(intent, flags, startId);
	}
	
	class DownloadThread implements Runnable{
		private MP3Info mp3Info = null;
		public DownloadThread(MP3Info mp3Info) {
			super();
			this.mp3Info = mp3Info;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println(mp3Info.getMp3Name());
			String mp3Url = "http://192.68.69.35:8080/liangMP3Server/music/"+"akon%20-%20angel.mp3";
			URLDownLoad urlDownloader = new URLDownLoad();
			boolean result = urlDownloader.downloadFile(mp3Url, "mp3/"+mp3Info.getMp3Name());
			System.out.println(result);
		}
	}
	
}
