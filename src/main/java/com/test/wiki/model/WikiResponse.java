package com.test.wiki.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Encapsule wikipedia api response
@JsonIgnoreProperties(ignoreUnknown = true)
public class WikiResponse {
	
	private Query query;
	
	

	public WikiResponse() {
		super();
	}

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	@Override
	public String toString() {
		return "WikiResponse [query=" + query + "]";
	}
	

}
