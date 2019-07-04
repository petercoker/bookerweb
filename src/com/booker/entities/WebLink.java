package com.booker.entities;

import com.booker.partner.IShareable;

public class WebLink extends Bookmark implements IShareable {
	private String url;
	private String host;
	private String htmlPage; //when downloader thread has downloaded a webpage depending on the url we store the it in this variable 
	private DownloadStatus downloadStatus = DownloadStatus.NOT_ATTEMPTED; //weblink default

	public enum DownloadStatus{
		NOT_ATTEMPTED,
		SUCCESS,
		FAILED,
		NOT_ELIGIBLE; //not eligible for download
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	@Override
	public String toString() {
		return "WebLink [url=" + url + ", host=" + host + "]";
	}

	 @Override
	  public boolean isKidFriendlyEligible() {
	    return (url.contains("porn") || getTitle().contains("porn") || host.contains("adult")) ? false : true;
	 }
	 
	 
	@Override
	public String getItemData() {
		StringBuilder builder = new StringBuilder();
		builder.append("<item>");
			builder.append("<type>Weblink</type>");
			builder.append("<title>").append(getTitle()).append("</title");
			builder.append("<url>").append(url).append("</url>");
			builder.append("<host>").append(host).append("</host>");
		builder.append("</item>");
		return builder.toString();
	}
	
	public String getHtmlPage() {
		return htmlPage;
	}

	public void setHtmlPage(String htmlPage) {
		this.htmlPage = htmlPage;
	}

	public DownloadStatus getDownloadStatus() {
		return downloadStatus;
	}

	public void setDownloadStatus(DownloadStatus downloadStatus) {
		this.downloadStatus = downloadStatus;
	}

}
