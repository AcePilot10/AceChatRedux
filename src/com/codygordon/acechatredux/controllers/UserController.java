package com.codygordon.acechatredux.controllers;

import javax.swing.JOptionPane;

import com.codygordon.acechatredux.AceChatRedux;
import com.codygordon.acechatredux.models.UserCredentials;
import com.codygordon.acechatredux.views.ConversationsView;
import com.codygordon.acechatredux.views.LoginView;
import com.codygordon.firebaserest.auth.FirebaseAuth;
import com.codygordon.firebaserest.database.FirebaseDatabase;
import com.google.firebase.auth.UserRecord;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class UserController {
	
	private FirebaseAuth auth;
	private FirebaseDatabase firebaseDatabase;
	
	public UserController() {
		auth = new FirebaseAuth("AIzaSyDaiojsXZVQIYlgnWibZgngjiZH_p2pQus");
		firebaseDatabase = new FirebaseDatabase("ace-chat-redux.firebaseio.com");
	}
	
	public void registerUser(UserCredentials credentials) {
		try {
			String idToken = auth.registerUser(credentials.email,
					credentials.password,
					credentials.username);
			
			UserRecord user = com.google.firebase.auth.FirebaseAuth.getInstance().getUser(idToken);
			
			if(user.getEmail() != null) {
				
				String json = new Gson().toJson(user);
				firebaseDatabase.updateData("Users/" + user.getUid(), json);
				
				System.out.println("Registration succeeded!");
				AceChatRedux.displayScreen(new LoginView());
				JOptionPane.showMessageDialog(AceChatRedux.mainFrame,
						"Registration complete! You may now login.",
						"Registration Complete",
						JOptionPane.PLAIN_MESSAGE);
			} else {
				System.out.println("Registration failed!");
				JOptionPane.showMessageDialog(AceChatRedux.mainFrame,
						"It looks like that email is already registered!",
						"Registration Error", 
						JOptionPane.ERROR_MESSAGE);
			}
		} catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(AceChatRedux.mainFrame,
					"An error was encountered during registration: " + e.getMessage(),
					"Registration Error", 
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void loginUser(UserCredentials credentials) {
		try {
			String uid = auth.signIn(credentials.email, credentials.password);
			UserRecord user = com.google.firebase.auth.FirebaseAuth.getInstance().getUser(uid);
			if(user.getEmail() != null) {
				System.out.println("Login succeeded!");
				AceChatRedux.executeLogin(user);
				ConversationsView view = new ConversationsView();
				AceChatRedux.displayScreen(view);
				JOptionPane.showMessageDialog(AceChatRedux.mainFrame,
						"Welcome!", "Login Succeeded", JOptionPane.INFORMATION_MESSAGE);
			} else {
				System.out.println("Invalid credentials!");
				JOptionPane.showMessageDialog(AceChatRedux.mainFrame,
						"Username or password is incorrect.",
						"Login Error", 
						JOptionPane.ERROR_MESSAGE);
			} 
		} catch(Exception e) {
			System.out.println("There was an error loggin in!");
			JOptionPane.showMessageDialog(AceChatRedux.mainFrame,
					"Username or password is incorrect.",
					"Login Error", 
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public UserRecord getUserById(String id) {
		try {
			String json = firebaseDatabase.getData("Users/" + id);
			return new Gson().fromJson(json, UserRecord.class);
		} catch(Exception e) {
			return null;	
		}
	}
	
	public UserRecord getUserByUsername(String username) {
		try {
			String json = firebaseDatabase.getData("Users");
			JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();		
			for(String childKey : jsonObject.keySet()) {
				String child = firebaseDatabase.getData("Users/" + childKey);
				UserRecord user = new Gson().fromJson(child, UserRecord.class);
				if(user.getDisplayName().equals(username)) {
					return user;
				}
			}
			return null;
		} catch(Exception e) {
			return null;
		}
	}
}