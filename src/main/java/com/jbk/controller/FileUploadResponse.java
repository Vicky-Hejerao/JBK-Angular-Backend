package com.jbk.controller;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class FileUploadResponse {
	
	private String fileName;
	private String downloadUrl;
	private Long size;
    private String imagePath;
    private String msg;
    
	@Override
	public String toString() {
		return "FileUploadResponse [fileName=" + fileName + ", downloadUrl=" + downloadUrl + ", size=" + size
				+ ", imagePath=" + imagePath + ", msg=" + msg + "]";
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	
    
}
