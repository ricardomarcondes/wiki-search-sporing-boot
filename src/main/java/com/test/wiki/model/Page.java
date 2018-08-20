package com.test.wiki.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Page {
	
	private String pageid;
	private String title;
	private String fullurl;
	
	public Page() {
		
	}
	
	public Page(String pageid, String title, String fullurl) {
		super();
		this.pageid = pageid;
		this.title = title;
		this.fullurl = fullurl;
	}

	public String getPageid() {
		return pageid;
	}
	public void setPageid(String pageid) {
		this.pageid = pageid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFullurl() {
		return fullurl;
	}
	public void setFullurl(String fullurl) {
		this.fullurl = fullurl;
	}
	
	@Override
	public String toString() {
		return "Pages [pageid=" + pageid + ", title=" + title + ", fullurl=" + fullurl + "]";
	}

	
	
	
}
