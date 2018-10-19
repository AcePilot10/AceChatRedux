package com.codygordon.acechatredux.controllers;

import java.util.UUID;

import javax.swing.JOptionPane;

import com.codygordon.acechatredux.AceChatRedux;
import com.codygordon.acechatredux.models.Chat;
import com.codygordon.acechatredux.models.ChatMessage;
import com.codygordon.acechatredux.views.ChatView;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ChatController {

	private FirebaseDatabase firebase;
	private com.codygordon.firebaserest.database.FirebaseDatabase dbAccessor;
	
	public ChatController() {
		firebase = FirebaseDatabase.getInstance();
		dbAccessor = new com.codygordon.firebaserest.database.FirebaseDatabase("ace-chat-redux.firebaseio.com");
	}
	
	public void addChatChildListener(ChildEventListener callback) {
		DatabaseReference ref = firebase.getReference("Chats");
		ref.addChildEventListener(callback);
	}
	
	public boolean chatBelongsToUser(Chat chat) {
		String userId = AceChatRedux.currentUser.getUid();
		return chat.user1Id.equals(userId) || chat.user2Id.equals(userId);
	}
	
	public void addChatMessage(String chatId, ChatMessage msg) {
		String data = new Gson().toJson(msg);
		String msgId = UUID.randomUUID().toString();
		dbAccessor.updateData("Chats/" + chatId + "/Messages/" + msgId, data);
	}
	
	public void addMessageListener(String chatId, ChildEventListener callback) {
		DatabaseReference ref = firebase.getReference("Chats/" + chatId + "/Messages");
		ref.addChildEventListener(callback);
	}
	
	public void createChat(String username) {
		UserRecord currentRecord = AceChatRedux.currentUser;
		UserRecord recipient = AceChatRedux.userController.getUserByUsername(username);
		if(recipient != null) {
			if(!chatExists(currentRecord.getUid(), recipient.getUid())) {
				Chat chat = new Chat(currentRecord.getUid(), recipient.getUid());
				dbAccessor.updateData("Chats/" + chat.id, new Gson().toJson(chat));
				System.out.println("Succesfully created chat!");
				ChatView view = new ChatView(chat);
				AceChatRedux.displayScreen(view);
			} else {
				System.out.println("A chat with that user already exists!");
				JOptionPane.showMessageDialog(AceChatRedux.mainFrame,
						"You already have a chat with " + username,
						"Error", 
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		} else {
			System.out.println("Couldn't find recipient with username: " + username);
			JOptionPane.showMessageDialog(AceChatRedux.mainFrame,
					"Couldn't find a user named " + username, 
					"User Not Found", 
					JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
	
	public boolean chatExists(String user1Id, String user2Id) { 
		try {
			String json = dbAccessor.getData("Chats");
			JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();		
			for(String childKey : jsonObject.keySet()) {
				String child = dbAccessor.getData("Chats/" + childKey);
				Chat chat = new Gson().fromJson(child, Chat.class);
				if(chat.user1Id.equals(user1Id) && chat.user2Id.equals(user2Id)) {
					return true;
				} else  if(chat.user2Id.equals(user1Id) && chat.user1Id.equals(user2Id)) {
					return true;
				}
			}
			return false;
		} catch(Exception e) {
			return false;
		}
	}
}