package com.codygordon.acechatredux.views.util.messagelist;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

import com.codygordon.acechatredux.AceChatRedux;
import com.codygordon.acechatredux.models.ChatMessage;
import com.google.firebase.auth.UserRecord;

public class MessageListCellRenderer extends JLabel 
								  implements ListCellRenderer<ChatMessage> {
	
	@Override
	public Component getListCellRendererComponent(JList<? extends ChatMessage> list, ChatMessage chatMessage, int index,
			boolean isSelected, boolean cellHasFocus) {
		
		setFont(new Font("Ariel", Font.PLAIN, 18));
		
		UserRecord currentUser = AceChatRedux.currentUser;
		
		UserRecord author = AceChatRedux.userController.getUserById(chatMessage.authorId);
		if(author.getUid().equals(currentUser.getUid())) {
			setHorizontalAlignment(SwingConstants.RIGHT);
			setBackground(Color.YELLOW);
			setText("Me: " + chatMessage.message);
		} else {
			setHorizontalAlignment(SwingConstants.LEFT);
			setBackground(Color.BLUE);
			setText(author.getDisplayName() + ": " + chatMessage.message);
		}
		return this;
	}
}