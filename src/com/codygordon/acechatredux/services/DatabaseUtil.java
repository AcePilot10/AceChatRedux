package com.codygordon.acechatredux.services;

import java.io.InputStream;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class DatabaseUtil {

	public DatabaseUtil() {
		System.out.println("Attempting to connect to server...");
		initFirebase();
	}
	
	private void initFirebase() {
		try {
			InputStream serviceAccount = DatabaseUtil.class.getResourceAsStream("firebase_key.json");
			GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
			FirebaseOptions options = new FirebaseOptions.Builder()
			  .setCredentials(credentials)
			  .setDatabaseUrl("https://ace-chat-redux.firebaseio.com")
			  .build();
			FirebaseApp.initializeApp(options);
			System.out.println("Succesfully connected to server!");
		} catch(Exception e) {
			e.printStackTrace();
		}
	} 
}