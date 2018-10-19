package com.codygordon.acechatredux.views.util.chatlist;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

import com.codygordon.acechatredux.AceChatRedux;
import com.codygordon.acechatredux.controllers.UserController;
import com.codygordon.acechatredux.models.Chat;

public class ChatListCellRenderer extends JLabel 
										   implements ListCellRenderer<Chat> {

	@Override
	public Component getListCellRendererComponent(JList<? extends Chat> list, Chat value, int index, boolean isSelected,
			boolean cellHasFocus) {
		
		Chat chat = value;
		
		UserController userController = AceChatRedux.userController;
		
		String user1Name = userController.getUserById(chat.user1Id).getDisplayName();
		String user2Name = userController.getUserById(chat.user2Id).getDisplayName();
		
		String currentUsername = AceChatRedux.currentUser.getDisplayName();
		
		String textToDisplay = "";
		if(user1Name.equals(currentUsername)) {
			textToDisplay = user2Name;
		} else {
			textToDisplay = user1Name;
		}
		
		setText(textToDisplay);
		setHorizontalAlignment(SwingConstants.CENTER);
		return this;
	}
}