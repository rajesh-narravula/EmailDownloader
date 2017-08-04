/*
 * @EmailDownloader.java	@Aug 2, 2017
 *
 * Copyright (c) 2016 Argos. 
 * All rights reserved. 
 * 
 * 
 */
package main.java.sample;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * <p>
 * </p>
 *
 * @author narravula.rajesh
 * @version 1.0
 *
 */
public class EmailDownloader
{
	private String URL = "http://mail-archives.apache.org/mod_mbox/maven-users/";
	
	private String path = "C:/Users/narravula.rajesh/Desktop/output/";
	
	public void downloadEmails(String year){
        try {
            Document document = Jsoup.connect(URL).get();
            
            Elements elements = document.select("a[href^=\" "+ year + "\"]" );
            
            for(Element element : elements){
            	String url = element.attr("abs:href");
            	if(url.contains("thread")){
            		url = url.replace("/thread", "");
            		System.out.println(url);
            		Response resultImageResponse = Jsoup.connect(url).ignoreContentType(true).execute();
            		String fileName = url.replace(URL, "");
            		FileOutputStream out = (new FileOutputStream(new java.io.File(path + fileName)));
            		out.write(resultImageResponse.bodyAsBytes());
            		out.close();
            	}
            }
            
        } catch (IOException e) {
        	e.printStackTrace();
        }
	}
	
	public static void main(String[] args)
	{
		EmailDownloader downloader = new EmailDownloader();
		System.out.println("Enter year in format YYYY: ");
		Scanner scanner = new Scanner(System.in);
		String year = scanner.nextLine();
		scanner.close();
		downloader.downloadEmails(year);
	}
}
