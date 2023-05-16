package com.homevision.photosdownloader;

import java.net.http.HttpClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PhotosDownloaderApplication {
	
	public static void main(final String[] args) {
		SpringApplication.run(PhotosDownloaderApplication.class, args);
	}

	@Bean
	public HttpClient httpClient() {
		return  HttpClient.newHttpClient();
	}
}
