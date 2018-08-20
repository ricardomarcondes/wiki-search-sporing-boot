package com.test.wiki.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.test.wiki.rest.WikiSearchController;
import com.test.wiki.util.Utils;
import com.test.wiki.util.WorkerThread;

//Apps entry point
@SpringBootApplication(scanBasePackageClasses = {WikiSearchController.class, WorkerThread.class, Utils.class})
public class WikiSearchApp {

	public static void main(String[] args) {
		SpringApplication.run(WikiSearchApp.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

}
