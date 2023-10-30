package com.jbk.controller;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.web.bind.annotation.CrossOrigin;

@Entity
public class ImageInfo {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String downloadUrl;
	private Long size;
    private String imagePath;
    private String msg;
    
	public Long getId() {
		return id;
	}
	public ImageInfo(Long id, String downloadUrl, Long size, String imagePath, String msg) {
		super();
		this.id = id;
		this.downloadUrl = downloadUrl;
		this.size = size;
		this.imagePath = imagePath;
		this.msg = msg;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	
    
    @Override
	public String toString() {
		return "ImageInfo [id=" + id + ", downloadUrl=" + downloadUrl + ", size=" + size + ", imagePath=" + imagePath
				+ ", msg=" + msg + "]";
	}
	public ImageInfo() {
		// TODO Auto-generated constructor stub
	}
}
