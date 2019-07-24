package com.booker.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
//import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class IOUtil {

	public static void read(List<String> data, String filename) {
		//UTF-8 universal character set
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"))) {
			// try(BufferedReader br = new BufferedReader(new FileReader(filename))){ // *
			String line;
			while ((line = reader.readLine()) != null) {
				data.add(line);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String read(InputStream inputStream) {
		StringBuilder text = new StringBuilder();

		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
			String line;
			while ((line = br.readLine()) != null) {
				text.append(line).append("\n");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return text.toString();
	}

	public static void write(String webpage, long id) {
		String pagesFolderLocation = "C:\\Users\\sonol\\Downloads\\_workspace\\_repositories\\local\\eclipse\\booker\\downloadedWebPages\\"; 
		//String pagesFolderLocation = "C:\\Users\\sonol\\Downloads\\_workspace\\_repositories\\local\\intellij\\booker\\src\\com\\booker\\downloadedWebPages\\";
		
		try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pagesFolderLocation + String.valueOf(id) + ".html"), "UTF-8"))) {
			writer.write(webpage);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}	


}
