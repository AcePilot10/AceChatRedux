package com.codygordon.acechatredux.views;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.codygordon.acechatredux.AceChatRedux;
import com.codygordon.acechatredux.controllers.UserController;
import com.codygordon.acechatredux.models.Chat;
import com.codygordon.acechatredux.models.ChatMessage;
import com.codygordon.acechatredux.views.util.messagelist.MessageListCellRenderer;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ChatView extends JPanel {

	public Chat chat;
	private JTextField txtInput;
	private JList<ChatMessage> listChat;
	private JButton btnBack;
	private JLabel lblTitle;
	private DefaultListModel<ChatMessage> model;
	private UserController controller = AceChatRedux.userController;
	
	private ChildEventListener chatMessageListener = new ChildEventListener() {

		@Override
		public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
			
			Object rawJsonObject = snapshot.getValue();
			String json = new Gson().toJson(rawJsonObject);
			JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
			
			String authorId = jsonObject.get("authorId").getAsString();
			String message = jsonObject.get("message").getAsString();
			
			ChatMessage msg = new ChatMessage(authorId, message);
			addChatMessage(msg);
		}

		@Override
		public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onChildRemoved(DataSnapshot snapshot) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onCancelled(DatabaseError error) {
			// TODO Auto-generated method stub
			
		}
	};
	
	public ChatView(Chat chat) {		
		setSize(650, 430);
		setBackground(SystemColor.control);
		this.chat = chat;
	 
		setLayout(null);
		
		listChat = new JList<ChatMessage>();
		listChat.setBackground(SystemColor.controlDkShadow);
		listChat.setBounds(85, 61, 480, 287);
		model = new DefaultListModel<ChatMessage>();
		listChat.setModel(model);
		listChat.setCellRenderer(new MessageListCellRenderer());
		add(listChat);
		
		txtInput = new JTextField();
		txtInput.setBounds(51, 379, 406, 40);
		add(txtInput);
		txtInput.setColumns(10);
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendButtonClicked();
			}
		});
		btnSend.setBounds(508, 379, 89, 40);
		add(btnSend);
		
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AceChatRedux.displayScreen(new ConversationsView());
			}
		});
		btnBack.setBounds(6, 24, 69, 23);
		add(btnBack);
		
		lblTitle = new JLabel("Title");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Ariel", Font.PLAIN, 25));
		lblTitle.setBounds(204, 28, 242, 22);
	
		UserRecord user1 = controller.getUserById(chat.user1Id);
		UserRecord user2 = controller.getUserById(chat.user2Id);
		String currentUser = AceChatRedux.currentUser.getUid();
		String title = "";
		if(user1.getUid().equals(currentUser)) {
			title = user2.getDisplayName();
		} else {
			title = user1.getDisplayName();
		}
		lblTitle.setText(title);
		add(lblTitle);
		
		FirebaseDatabase db = FirebaseDatabase.getInstance();
		db.getReference("Chats/" + chat.id + "/Messages").addChildEventListener(chatMessageListener);
	}
	
	private void addChatMessage(ChatMessage msg) {
		model.addElement(msg);
	}
	
	private void sendButtonClicked() {
		String text = txtInput.getText();
		if(text.isEmpty()) return;
		String userId = AceChatRedux.currentUser.getUid();
		ChatMessage msg = new ChatMessage(userId, text);
		AceChatRedux.chatController.addChatMessage(chat.id, msg);
		txtInput.setText("");
		System.out.println("Succesfully sent chat message!");
	}
}