package com.codygordon.acechatredux.models;

import java.util.UUID;

public class Chat {
	
	public String id;
	public String user1Id;
	public String user2Id;
	
	public Chat(String user1Id, String user2Id) {
		this.user1Id = user1Id;
		this.user2Id = user2Id;
		id = UUID.randomUUID().toString();
	}
}