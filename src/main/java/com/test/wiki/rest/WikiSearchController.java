package com.test.wiki.rest;


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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.wiki.model.Page;
import com.test.wiki.model.Query;
import com.test.wiki.model.WikiResponse;

@RestController
@RequestMapping("/wikisearch")
public class WikiSearchController {

//    @Autowired
//    UserRepository userRepository;


//    @GetMapping
//    public Page<User> getUsers(Pageable pageable) {
//        return userRepository.findAll(pageable);
//    }

    @GetMapping("")
    public WikiResponse searchWiki(@RequestParam(value = "query", required = true) String query) {
    	
    	//TODO Handle term => encode.url etc....
    	WikiResponse wikiResponse = new WikiResponse();
    	
    	if(null == query || query.isEmpty() ) {
    		return wikiResponse;
    	}
    	
    	try {

			StringBuffer jsonString = new StringBuffer();
			DefaultHttpClient httpClient = new DefaultHttpClient();
			//HttpGet getRequest = new HttpGet("http://en.wikipedia.org/w/api.php?action=query&generator=search&gsrsearch=brazil&format=json&gsrprop=snippet&prop=info&inprop=url");
			HttpGet getRequest = new HttpGet("http://en.wikipedia.org/w/api.php?action=query&generator=search&gsrsearch=" + query + "&format=json&gsrprop=snippet&prop=info&inprop=url&gsrlimit=3");
			getRequest.addHeader("accept", "application/json");

			HttpResponse response = httpClient.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));

			String output;
			//System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {

				//System.out.println(output);
				jsonString.append(output);
			}

			httpClient.getConnectionManager().shutdown();
			
			wikiResponse = parseResponse(jsonString.toString());

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

    public WikiResponse parseResponse(String json) {
    	
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
				
				//System.out.println(id + " : " + title + " : " + fullurl);
				listPages.add(new Page(id, title, fullurl));
			}
			
			Query queryObj = new Query();
			queryObj.setPages(listPages);
			//WikiResponse wikiResponse = new WikiResponse();
			wikiResponse.setQuery(queryObj);
			
			//String jsonResponse = mapper.writeValueAsString(wikiResponse);

			 //System.out.println(map);
		     //System.out.println(query);
		     //System.out.println(pages);
			//System.out.println(jsonResponse);
		  }
		  catch (JsonParseException e) { e.printStackTrace();}
		  catch (JsonMappingException e) { e.printStackTrace(); }
		  catch (IOException e) { e.printStackTrace(); }
		  
		  return wikiResponse;
	   }   	

}
