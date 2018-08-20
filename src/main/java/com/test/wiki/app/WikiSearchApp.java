package com.test.wiki.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.test.wiki.rest.WikiSearchController;
import com.test.wiki.util.WorkerThread;

//Apps entry point
@SpringBootApplication(scanBasePackageClasses = {WikiSearchController.class, WorkerThread.class})
public class WikiSearchApp {

	public static void main(String[] args) {
		SpringApplication.run(WikiSearchApp.class, args);
	}

}
