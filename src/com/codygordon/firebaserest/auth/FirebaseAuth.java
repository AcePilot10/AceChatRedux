package com.codygordon.firebaserest.auth;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;

import com.codygordon.firebaserest.auth.models.RegisterResponse;
import com.codygordon.firebaserest.auth.models.SignInResponse;
import com.codygordon.firebaserest.util.RestClient;
import com.google.gson.Gson;

public class FirebaseAuth {

	private static final String BASE_URL = "www.googleapis.com/identitytoolkit/v3/relyingparty";
		
	private String apiKey;
	
	public RestClient client;
	
	public FirebaseAuth(String key) {
		client = RestClient.getInstance();
		this.apiKey = key;
	}
	
	public String registerUser(String email, String password, String displayName) {
		try {
			URI uri = new URIBuilder()
					.setScheme("https")
					.setHost(BASE_URL)
					.setPath("signupNewUser")
					.addParameter("key", apiKey)
					.build();
			
			List<NameValuePair> form = new ArrayList<>();
            form.add(new BasicNameValuePair("email", email));
            form.add(new BasicNameValuePair("password", password));
            form.add(new BasicNameValuePair("returnSecureToken", "true"));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(form, Consts.UTF_8);
			
			HttpResponse response = client.post(uri, entity);
			
			String json = client.readResponse(response);
			
			RegisterResponse reg = new Gson().fromJson(json, RegisterResponse.class);
			
			setDisplayName(reg.idToken, displayName);
			
			return reg.localId;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String signIn(String email, String password) {
		try {
			URI uri = new URIBuilder()
					.setScheme("https")
					.setHost(BASE_URL)
					.setPath("verifyPassword")
					.addParameter("key", apiKey)
					.build();
			
			List<NameValuePair> form = new ArrayList<>();
            form.add(new BasicNameValuePair("email", email));
            form.add(new BasicNameValuePair("password", password));
            form.add(new BasicNameValuePair("returnSecureToken", "true"));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(form, Consts.UTF_8);
			
			HttpResponse response = client.post(uri, entity);
			
			String json = client.readResponse(response);
			
			SignInResponse token = new Gson().fromJson(json, SignInResponse.class);
			
			return token.localId;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setDisplayName(String token, String displayName) {
		try {
			URI uri = new URIBuilder()
					.setScheme("https")
					.setHost(BASE_URL)
					.setPath("setAccountInfo")
					.addParameter("key", apiKey)
					.build();
			
			List<NameValuePair> form = new ArrayList<>();
			form.add(new BasicNameValuePair("idToken", token));
			form.add(new BasicNameValuePair("displayName", displayName));
			UrlEncodedFormEntity body = new UrlEncodedFormEntity(form, Consts.UTF_8);
			client.post(uri, body);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}