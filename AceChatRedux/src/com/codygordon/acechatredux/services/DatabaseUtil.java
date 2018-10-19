package com.codygordon.acechatredux.services;

import java.io.FileInputStream;

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
			String path = "bin/firebase_key.json";
			FileInputStream serviceAccount = new FileInputStream(path);
			FirebaseOptions options = new FirebaseOptions.Builder()
			  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
			  .setDatabaseUrl("https://ace-chat-redux.firebaseio.com")
			  .build();
			FirebaseApp.initializeApp(options);
			System.out.println("Succesfully connected to server!");
		} catch(Exception e) {
			e.printStackTrace();
		}
	} 
}