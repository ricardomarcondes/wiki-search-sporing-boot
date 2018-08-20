package com.test.wiki.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Query {
	

	private String queryParam;

	
	public String getQueryParam() {
		return queryParam;
	}



	public void setQueryParam(String queryParam) {
		this.queryParam = queryParam;
	}



	private List<Page> pages;

	public Query() {}



	public List<Page> getPages() {
		return pages;
	}



	public void setPages(List<Page> pages) {
		this.pages = pages;
	}



	@Override
	public String toString() {
		return "Query [queryParam=" + queryParam + ", pages=" + pages + "]";
	}




}
