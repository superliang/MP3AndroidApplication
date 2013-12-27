package com.liang.mp3androidapplication;

import com.liang.utils.URLDownLoad;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class IndexActivity extends ListActivity {
	private static final int MENU_UPDATE = 1;
	private static final int MENU_ABOUT = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.add(0, MENU_UPDATE, 1, R.string.updateLIst_menu);
		menu.add(0, MENU_ABOUT, 2, R.string.about_menu);
		getMenuInflater().inflate(R.menu.index, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId()==MENU_UPDATE){
			downloadXML("http://192.68.69.35:8080/liangMP3Server/resource.xml");
		}else if(item.getItemId()==MENU_ABOUT){
			
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	private String downloadXML(String urlStr){
		URLDownLoad urlDownLoad = new URLDownLoad();
		String result = urlDownLoad.downLoadFile(urlStr);
		return result;
	}
}
