package com.test.wiki.util;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.test.wiki.model.WikiResponse;


//Represents a thread for each wikipedia api call 
@Component
@Scope("prototype")
public class WorkerThread implements Callable<WikiResponse> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerThread.class);

    private String query;
    
    @Autowired
    private Utils utils;

    public WorkerThread(String query) {
        this.query = query;
    }

    @Override
    public WikiResponse call() throws Exception {
        
        return doWork(query);      
    }

    //Uses utility class to call wikipedia api
    private WikiResponse doWork(String query) {
    	LOGGER.debug("Thread started [" + query + "]");
    	//utils = new Utils();
    	
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
			LOGGER.debug("Thread finished [" + query + "]");
			return wikiResponse;
		}

    }

}
