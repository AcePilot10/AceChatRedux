package com.codygordon.firebaserest.firebase;

public class Credentials {

	private String webApiKey;
	private String url;
	
	public Credentials(String webApiKey, String url) {
		this.webApiKey = webApiKey;
		this.url = url;
	}
	
	public Credentials(String url) {
		this.url = url;
	}
	
	public String getWebApiKey() {
		return this.webApiKey;
	}
	
	public String getUrl() {
		return this.url;
	}
}