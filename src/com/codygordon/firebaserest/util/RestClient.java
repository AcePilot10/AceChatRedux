package com.codygordon.firebaserest.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

public class RestClient {

	private HttpClient client;
	private static RestClient instance;
	
	public static RestClient getInstance() {
		if(instance == null) {
			instance = new RestClient();
		}
		return instance;
	}
	
	private RestClient() {
		initClient();
	}

	private void initClient() {
		client = HttpClientBuilder.create().build();
	}
	
	public HttpResponse get(URI uri) {
		try {
			HttpGet request = new HttpGet(uri);
			HttpResponse response = client.execute(request);
			return response;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public HttpResponse post(URI uri, HttpEntity body) {
		try {
			HttpPost request = new HttpPost(uri);
			request.setEntity(body);
			HttpResponse response = client.execute(request);
			return response;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public HttpResponse patch(URI uri, HttpEntity body) {
		try {
			HttpPatch request = new HttpPatch(uri);
			request.setEntity(body);
			HttpResponse response = client.execute(request);
			return response;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String readResponse(HttpResponse response) {
		try {
			InputStreamReader streamReader = new InputStreamReader(response.getEntity().getContent());
			BufferedReader reader = new BufferedReader(streamReader);
			StringBuffer fullResponse = new StringBuffer();
			String line = "";
			while((line = reader.readLine()) != null) {
				fullResponse.append(line);
			}
			return fullResponse.toString();
		} catch(Exception e) {
			return e.getMessage();
		}
	}
}