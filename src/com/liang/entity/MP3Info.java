package com.liang.entity;

import java.io.Serializable;

public class MP3Info implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String mp3Name;
	private String mp3Size;
	private String mp3Lrc;
	private String mp3LrcSize;
	
	public String getMp3Lrc() {
		return mp3Lrc;
	}
	public void setMp3Lrc(String mp3Lrc) {
		this.mp3Lrc = mp3Lrc;
	}
	public String getMp3LrcSize() {
		return mp3LrcSize;
	}
	public void setMp3LrcSize(String mp3LrcSize) {
		this.mp3LrcSize = mp3LrcSize;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMp3Name() {
		return mp3Name;
	}
	public void setMp3Name(String mp3Name) {
		this.mp3Name = mp3Name;
	}
	public String getMp3Size() {
		return mp3Size;
	}
	public void setMp3Size(String mp3Size) {
		this.mp3Size = mp3Size;
	}
	
	@Override
	public String toString() {
		return "MP3Info [id=" + id + ", mp3Name=" + mp3Name + ", mp3Size="
				+ mp3Size + ", mp3Lrc=" + mp3Lrc + ", mp3LrcSize=" + mp3LrcSize
				+ "]";
	}
	
}
