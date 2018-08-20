package com.test.wiki.rest;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
import com.test.wiki.util.WorkerThread;


@RestController
@RequestMapping("/wikisearch")
public class WikiMultiSearchController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WikiMultiSearchController.class);
	
	@Autowired
    private ApplicationContext context;

	//=> /multi?query=Canada&query=USA&query=Brazil
    @GetMapping("/multi")
    public WikiResponse[] searchWiki(@RequestParam List<String> query) throws Exception{
    	LOGGER.debug("searchWiki started...");
    	
    	WikiResponse[] wikiResponses;
    	
    	if(null == query || query.isEmpty() ) {
    		return wikiResponses = new WikiResponse[1];
    	}

    	//Create array response based on num of queries
    	wikiResponses = new WikiResponse[query.size()];

    	//Create thread pool based on num of queries
    	ExecutorService executorService = Executors.newFixedThreadPool(query.size());

    	//Create threads list based on num of queries
        List<WorkerThread> tasks = new ArrayList<>(query.size());
        
        //Insert a new thread object, for a specific query, in the threads list 
        for (int i = 0; i < query.size(); i++) {
            WorkerThread wt = context.getBean(WorkerThread.class, query.get(i));
            tasks.add(wt);
        }
        
    	//Trigger all threads created
        List<Future<WikiResponse>> futures = executorService.invokeAll(tasks);
        
        //Wait threads finish and assign the results in the response array
        for (int j = 0; j < query.size(); j++) {
        	wikiResponses[j] = (WikiResponse) futures.get(j).get(10000, TimeUnit.MILLISECONDS);
        }

        executorService.shutdown();

        LOGGER.debug("searchWiki finished...");
        
        return wikiResponses;
    }
  	

}
