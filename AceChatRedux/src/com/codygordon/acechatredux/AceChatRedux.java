package com.codygordon.acechatredux;

import javax.swing.JPanel;

import com.codygordon.acechatredux.controllers.ChatController;
import com.codygordon.acechatredux.controllers.UserController;
import com.codygordon.acechatredux.services.DatabaseUtil;
import com.codygordon.acechatredux.views.AceChatWindow;
import com.codygordon.acechatredux.views.LoginView;
import com.google.firebase.auth.UserRecord;

public class AceChatRedux {

	public static DatabaseUtil db;
	public static AceChatWindow mainFrame;
	public static UserRecord currentUser;
	public static UserController userController;
	public static ChatController chatController;
	
	public static void main(String[] args) {
		System.out.println("Ace Chat Redux has started!");
		new AceChatRedux();
	}
	
//	private void seedDatabase() {
//		Chat chat = new Chat("T6ki1T3LWzfQnqb9w65lEZsD80x2", 
//				"da8n613sL0UrlHkKCAd6NHTbG9O2");
//		String chatJson = new Gson().toJson(chat);
//		
//		new com.codygordon.firebaserest.database.FirebaseDatabase("ace-chat-redux.firebaseio.com")
//		.updateData("Chats/" + chat.id, chatJson);
//		
//		ChatMessage msgTest= new ChatMessage("T6ki1T3LWzfQnqb9w65lEZsD80x2",
//				"Test Message");
//		chatController.addChatMessage(chat.id, msgTest);
//	}
	
	public AceChatRedux() {
		db = new DatabaseUtil();
		userController = new UserController();
		chatController = new ChatController();		
		initFrame();
	}
	
	private void initFrame() {
		mainFrame = new AceChatWindow();
		LoginView view = new LoginView();
		mainFrame.contentScreen.add(view);
		mainFrame.setVisible(true);
	}
	
	public static void displayScreen(JPanel view) {
		mainFrame.contentScreen.removeAll();
		mainFrame.contentScreen.add(view);
		mainFrame.contentScreen.repaint();
		mainFrame.revalidate();
	}
	
	public static void updateScreen() {
		mainFrame.contentScreen.revalidate();
		mainFrame.contentScreen.repaint();
	}
	
	public static void executeLogin(UserRecord user) {
		currentUser = user;
	}
}