package com.liang.mp3androidapplication;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.liang.entity.MP3Info;
import com.liang.mp3Service.DownLoadService;
import com.liang.utils.URLDownLoad;
import com.liang.xml.MP3ListContentHandly;

public class IndexActivity extends ListActivity {
	private static final int MENU_UPDATE = 1;
	private static final int MENU_ABOUT = 1;
	
	private List<MP3Info> mp3Infos = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);
		updateListView();
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
			updateListView();
		}else if(item.getItemId()==MENU_ABOUT){
			
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	private String downloadXML(String urlStr){
		URLDownLoad urlDownLoad = new URLDownLoad();
		String result = urlDownLoad.downLoadFile(urlStr);
		return result;
	}
	
	private List<MP3Info> parse(String xmlStr){
		SAXParserFactory factory = SAXParserFactory.newInstance();
		List<MP3Info> infos = new ArrayList<MP3Info>();
		try {
			XMLReader xmlReader = factory.newSAXParser().getXMLReader();
			MP3ListContentHandly mp3ListContentHanly = new MP3ListContentHandly(infos);
			xmlReader.setContentHandler(mp3ListContentHanly);
			xmlReader.parse(new InputSource(new StringReader(xmlStr)));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return infos;
	}
	
	private void updateListView(){
		String menuListXML = downloadXML("http://192.68.69.35:8080/liangMP3Server/resource.xml");
		mp3Infos = parse(menuListXML);
		List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		for(Iterator<MP3Info> iterator = mp3Infos.iterator();iterator.hasNext();){
			MP3Info mp3Info = iterator.next();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("mp3_name", mp3Info.getMp3Name());
			map.put("mp3_size", mp3Info.getMp3Size());
			list.add(map);
		}
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, R.layout.activity_musiclist, new String[]{"mp3_name","mp3_size"}, new int[]{R.id.mp3_name,R.id.mp3_size});
		setListAdapter(simpleAdapter);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		MP3Info mp3Info = mp3Infos.get(position);
		Intent intent = new Intent();
		intent.putExtra("mp3Info", mp3Info);
		intent.setClass(this, DownLoadService.class);
		startService(intent);
	}
}
