package com.codygordon.acechatredux.views;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.codygordon.acechatredux.AceChatRedux;
import com.codygordon.acechatredux.models.Chat;
import com.codygordon.acechatredux.views.util.chatlist.ChatListCellRenderer;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ConversationsView extends JPanel  {
		
	private JList<Chat> listConversations;
	private DefaultListModel<Chat> model;
	
	public ConversationsView() {		
		setLayout(null);
		setSize(650, 430);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(72, 95, 506, 324);
		add(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		listConversations = new JList<Chat>();
		model = new DefaultListModel<Chat>();
		listConversations.setModel(model);		
		listConversations.setCellRenderer(new ChatListCellRenderer());
		
		listConversations.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				itemSelected();
			}
		});
		
		panel.add(listConversations);
		
		JButton btnNewChat = new JButton("New Chat");
		btnNewChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newChatButtonClicked();
			}
		});
		btnNewChat.setBounds(280, 61, 89, 23);
		add(btnNewChat);
		
		JLabel lblTitle = new JLabel("Conversations");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(237, 11, 176, 38);
		add(lblTitle);
	
		loadChats();
	}
	
	private void loadChats() {
		ChildEventListener listener = new ChildEventListener() {

			@Override
			public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
				Object rawJsonObject = snapshot.getValue();
			    String json = new Gson().toJson(rawJsonObject);
				JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
			    String user1Id = jsonObject.get("user1Id").getAsString();
			    String user2Id = jsonObject.get("user2Id").getAsString();
			    String chatId = jsonObject.get("id").getAsString();
			    Chat chat = new Chat(user1Id, user2Id);
			    chat.id = chatId;
				addChatToModel(chat);
			}

			@Override
			public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
				
			}

			@Override
			public void onChildRemoved(DataSnapshot snapshot) {
				
			}

			@Override
			public void onChildMoved(DataSnapshot snapshot, String previousChildName) {

			}

			@Override
			public void onCancelled(DatabaseError error) {

			}
		};
		AceChatRedux.chatController.addChatChildListener(listener);
	}
	
	private void addChatToModel(Chat chat) {
		if(AceChatRedux.chatController.chatBelongsToUser(chat)) {
			model.addElement(chat);
			System.out.println("Loaded chat");
		}
	}
	
	private void itemSelected() {
		try {
			int selectedIndex = listConversations.getSelectedIndex();
			Chat chat = listConversations.getModel().getElementAt(selectedIndex);
			ChatView view = new ChatView(chat);
			AceChatRedux.displayScreen(view);
		} catch(Exception e) { }
	}
	
	private void newChatButtonClicked() {
		AceChatRedux.displayScreen(new CreateChatView());
	}
}