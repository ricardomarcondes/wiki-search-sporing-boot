package com.test.wiki.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.wiki.model.Page;
import com.test.wiki.model.Query;
import com.test.wiki.model.WikiResponse;

//@Bean
public class Utils {
	
	private static final String urlPrefix = "http://en.wikipedia.org/w/api.php?action=query&generator=search&gsrsearch=";
	private static final String urlSufix = "&format=json&gsrprop=snippet&prop=info&inprop=url&gsrlimit=3";
	
	//Call the wiki webservice
	public WikiResponse searchWiki(String query){
    	  	
    	WikiResponse wikiResponse = new WikiResponse();
    	
    	//removes non-alphanumeric characters from a Java String.
    	query = query.replaceAll("[^a-zA-Z0-9]", "");
    	
    	if(null == query || query.isEmpty() ) {
    		return wikiResponse;
    	}
    	
    	try {

			StringBuffer jsonString = new StringBuffer();
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet getRequest = new HttpGet(urlPrefix  + query + urlSufix);
			getRequest.addHeader("accept", "application/json");

			HttpResponse response = httpClient.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));

			String output;
			while ((output = br.readLine()) != null) {
				jsonString.append(output);
			}

			httpClient.getConnectionManager().shutdown();
			
			wikiResponse = parseResponse(query, jsonString.toString());

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (Exception e) {
			//e.printStackTrace();
		} finally {
			return wikiResponse;
		}
    	
    }

	//Parse the jsons returned from wikipedia api into a object
    public WikiResponse parseResponse(String queryParam, String json) {
    	
    	  WikiResponse wikiResponse = new WikiResponse();

		  ObjectMapper mapper = new ObjectMapper();
		  Map<String, Object> map = new HashMap<String, Object>();
		  Map<String, Object> query;
		  Map<String, Object> pages;
		  Map<String, String> page;
		  List<Page> listPages = new ArrayList<Page>();

		  try{

			 // convert JSON string to Map
			 map = mapper.readValue(json, new TypeReference<Map<String, Object>>(){});
			 query = (Map<String, Object>) map.get("query");
			 pages = (Map<String, Object>)query.get("pages");
			 Set<String> setPages = pages.keySet();
			 for (String pageID : setPages) {
				page = (Map<String, String>) pages.get(pageID);
				
				String id = String.valueOf(page.get("pageid"));
				String title = page.get("title");
				String fullurl = page.get("fullurl");
				
				listPages.add(new Page(id, title, fullurl));
			}
			
			Query queryObj = new Query();
			queryObj.setQueryParam(queryParam);
			queryObj.setPages(listPages);
			wikiResponse.setQuery(queryObj);
			
		  }
		  catch (JsonParseException e) { e.printStackTrace();}
		  catch (JsonMappingException e) { e.printStackTrace(); }
		  catch (IOException e) { e.printStackTrace(); }
		  
		  return wikiResponse;
	   }   	

}
