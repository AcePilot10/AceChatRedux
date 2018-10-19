package com.codygordon.acechatredux.models;

public class ChatMessage {

	public String authorId;
	public String message;
	
	public ChatMessage(String authorId, String message) {
		this.authorId = authorId;
		this.message = message;
	}
}