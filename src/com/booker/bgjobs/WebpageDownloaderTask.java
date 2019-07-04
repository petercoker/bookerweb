package com.booker.bgjobs;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.booker.util.IOUtil;
import com.booker.entities.WebLink;
import com.booker.entities.WebLink.DownloadStatus;
import com.booker.util.HttpConnect;

import com.booker.dao.BookmarkDao;

public class WebpageDownloaderTask implements Runnable {

	private static BookmarkDao dao = new BookmarkDao();
	
	private static final long TIME_FRAME = 3000000000L; //3 Seconds
	
	private boolean downloadAll = false;
	
	ExecutorService downloadExecutor = Executors.newFixedThreadPool(5);
	
	private static class Downloader<T extends WebLink> implements Callable<T> {
		private T weblink;
		
		public Downloader(T weblink) {
			this.weblink = weblink;
		}
		
		public T call() {
			try {
				if (!weblink.getUrl().endsWith(".pdf")) {
					//At the start set weblink download to failed, once download set to true
					weblink.setDownloadStatus(WebLink.DownloadStatus.FAILED);
					String htmlPage = HttpConnect.download(weblink.getUrl());
					weblink.setHtmlPage(htmlPage);
				} else {
					//Its not eligible for download
					weblink.setDownloadStatus(WebLink.DownloadStatus.NOT_ELIGIBLE);
				}
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			return weblink;
		}
	}
	
	//if true download all weblinks
	public WebpageDownloaderTask(boolean downloadAll) {
		this.downloadAll = downloadAll;
	}
	
	@Override
	public void run() {
		
		// with isInterrupte() want to respond to any interruption
		while(!Thread.currentThread().isInterrupted()) {
			
			// Get WebLinks
			List<WebLink> webLinks = getWebLinks();
			
			// After the job is done then it continues by Downloading concurrently
			// If we have at least one weblink, invoke the download method
			if (webLinks.size() > 0) {
				download(webLinks); //invoke download method to download all webpages currently
			} else {
				System.out.println("No new Web Links to download!");
			}
			
			// when the job is done it Wait for current seconds 
			//background down job will try to download once every 15 seconds to look for new weblinks
			try {
				TimeUnit.SECONDS.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
			
		}
		
		downloadExecutor.shutdown();
		
	}

	private void download(List<WebLink> webLinks) {
		List<Downloader<WebLink>> tasks = getTask(webLinks);
		List<Future<WebLink>> futures = new ArrayList<>();
		
		try {
			futures = downloadExecutor.invokeAll(tasks, TIME_FRAME, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for (Future<WebLink> future : futures) {
			try {
				if (!future.isCancelled()) {
					WebLink webLink = future.get(); //returns a webLink
					String webPage = webLink.getHtmlPage(); //from weblink, get webpage once downloaded
					if (webPage != null) { //if webpage is downloaded is successfully
						IOUtil.write(webPage, webLink.getId()); //save the webpage to the disk
						webLink.setDownloadStatus(WebLink.DownloadStatus.SUCCESS);
						System.out.println("Download Webpage Success: " + webLink.getTitle()); //get webLink Title
					} else {
						System.out.println("Webpage not downloaded: " + webLink.getTitle()); //get webLink Title
					}
				} else {
					System.out.println("\n\nCurrent Tasks --> " + Thread.currentThread());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		
	}

	//returns a list of Downloaded tasks
	private List<Downloader<WebLink>> getTask(List<WebLink> webLinks) {
		List<Downloader<WebLink>> tasks = new ArrayList<>();
		
		for (WebLink webLink : webLinks) {
			tasks.add(new Downloader<WebLink>(webLink)); //Download is Genericfied 
		}
		
		return tasks;
	}

	private List<WebLink> getWebLinks() {
		List<WebLink> webLinks = null;
		
		//if the downloadAll constructor is true, get all the links
		if (downloadAll) {
			webLinks = dao.getAllWebLinks(); //invoke dao field variable
			downloadAll = false; //once get all weblinks turn back to false (only get new weblinks that added)
		} else {
			//else get web links that are not attempted 
			webLinks = dao.getWebLinks(WebLink.DownloadStatus.NOT_ATTEMPTED);
		}
		return webLinks;
	}

}
