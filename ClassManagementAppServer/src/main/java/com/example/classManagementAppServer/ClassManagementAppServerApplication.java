package com.example.classManagementAppServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@SpringBootApplication
public class ClassManagementAppServerApplication {



	public static void main(String[] args) {
//		System.setProperty("https.proxyHost", "192.168.122.106");
//		System.setProperty("https.proxyPort", "8080");

		loadEnvironmentVariables();

        SpringApplication.run(ClassManagementAppServerApplication.class, args);

	}

	private static void loadEnvironmentVariables ()
	{
		try
		{
			System.getProperties().load(new FileInputStream(".env"));
		}
		catch (IOException e) {
			throw new RuntimeException("Environment variables are essential for this app to start\nPlease refer to the docs on loading classmanagementappserver for better clarification");
		}
	}

}
