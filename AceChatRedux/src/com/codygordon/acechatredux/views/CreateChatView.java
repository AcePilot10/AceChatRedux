package com.codygordon.acechatredux.views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.codygordon.acechatredux.AceChatRedux;

public class CreateChatView extends JPanel {
	
	private JTextField txtUsername;

	public CreateChatView() {
		setLayout(null);
		setSize(650, 430);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(215, 178, 219, 33);
		add(txtUsername);
		txtUsername.setColumns(10);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createButtonClicked();
			}
		});
		btnCreate.setBounds(280, 359, 89, 23);
		add(btnCreate);
		
		JLabel lblCreateChat = new JLabel("Create Chat");
		lblCreateChat.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreateChat.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblCreateChat.setBounds(195, 21, 259, 51);
		add(lblCreateChat);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setBounds(285, 222, 80, 14);
		add(lblUsername);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AceChatRedux.displayScreen(new ConversationsView());
			}
		});
		btnBack.setBounds(280, 393, 89, 23);
		add(btnBack);

	}
	
	private void createButtonClicked() {
		String username = txtUsername.getText();
		AceChatRedux.chatController.createChat(username);
	}
}