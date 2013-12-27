package com.liang.xml;

import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.liang.entity.MP3Info;

public class MP3ListContentHandly extends DefaultHandler{
	private List<MP3Info> infos = null;
	public MP3ListContentHandly(List<MP3Info> infos) {
		super();
		this.infos = infos;
	}

	public List<MP3Info> getInfos() {
		return infos;
	}

	public void setInfos(List<MP3Info> infos) {
		this.infos = infos;
	}

	private MP3Info mp3Info = null;
	private String tagName= null;
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		String temp = new String(ch,start,length);
		if(tagName.equals("id")){
			mp3Info.setId(temp);
		}else if(tagName.equals("mp3.name")){
			mp3Info.setMp3Name(temp);
		}else if(tagName.equals("mp3.size")){
			mp3Info.setMp3Size(temp);
		}else if(tagName.equals("lrc.name")){
			mp3Info.setMp3Lrc(temp);
		}else if(tagName.equals("lrc.size")){
			mp3Info.setMp3LrcSize(temp);
		}
	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		if(qName.equals("resource")){
			infos.add(mp3Info);
		}
		tagName = "";
	}

	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		this.tagName = localName;
		if(tagName.equals("resource")){
			mp3Info = new MP3Info();
		}
	}
	
}
