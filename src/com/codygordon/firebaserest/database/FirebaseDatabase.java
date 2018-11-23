package com.codygordon.firebaserest.database;

import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;

import com.codygordon.firebaserest.util.RestClient;
import com.google.gson.Gson;

public class FirebaseDatabase {
	
	private RestClient client;
	private String url;
	
	public FirebaseDatabase(String url) {
		client = RestClient.getInstance();
		this.url = url;
	}
	
	public String getData(String node) {
		try {
			URI uri = new URIBuilder()
					.setScheme("https")
					.setHost(url)
					.setPath(node + ".json")
					.build();
			HttpResponse response = client.get(uri);
			String json = client.readResponse(response);
			return json;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String updateData(String path, String data) {
		try {
			URI uri = new URIBuilder()
					.setScheme("https")
					.setHost(url)
					.setPath(path + ".json")
					.build();
			StringEntity body = new StringEntity(data);
			HttpResponse response = client.patch(uri, body);
			String json = client.readResponse(response);
			return json;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String postData(String path, String data) {
		try {
			URI uri = new URIBuilder()
					.setScheme("https")
					.setHost(url)
					.setPath(path + ".json")
					.build();
			
			String jsonData = new Gson().toJson(data);
			StringEntity body = new StringEntity(jsonData);
			HttpResponse response = client.post(uri, body);
			String json = client.readResponse(response);
			return json;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}