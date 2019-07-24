package com.booker.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * For the Weblinks, 
 * @author sonol
 *
 */
public class HttpConnect {
	
	public static String download(String webpageUrl) throws MalformedURLException, URISyntaxException {
		System.out.println("Downloading: " + webpageUrl);
		URL url = new URI(webpageUrl).toURL();		
		
		try {
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			int responseCode = con.getResponseCode();	
			
			if(responseCode >= 200 && responseCode < 300) { // ok
				return IOUtil.read(con.getInputStream());		        
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
