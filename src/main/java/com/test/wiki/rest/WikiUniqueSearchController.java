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
import org.springframework.beans.factory.annotation.Autowired;
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
import com.test.wiki.util.Utils;

@RestController
@RequestMapping("/wikisearch")
public class WikiUniqueSearchController {

    //@Autowired
    Utils utils;
//    UserRepository userRepository;


//    @GetMapping
//    public Page<User> getUsers(Pageable pageable) {
//        return userRepository.findAll(pageable);
//    }

    @GetMapping("/unique")
    public WikiResponse searchWiki(@RequestParam(value = "query", required = true) String query) {
    	
    	utils = new Utils();
    	
    	//TODO Handle term => encode.url etc....
    	WikiResponse wikiResponse = new WikiResponse();
    	
    	if(null == query || query.isEmpty() ) {
    		return wikiResponse;
    	}
    	
    	try {

			wikiResponse = utils.searchWiki(query);

		} catch (Exception e) {
			//e.printStackTrace();
		} finally {
			return wikiResponse;
		}
    	
    }

}
