package com.codygordon.acechatredux.controllers;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.List;

import javax.swing.JOptionPane;

import com.codygordon.acechatredux.AceChatRedux;
import com.codygordon.acechatredux.models.UserCredentials;
import com.codygordon.acechatredux.views.ConversationsView;
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
				JOptionPane.showMessageDialog(AceChatRedux.mainFrame,
						"Registration complete! You may now login.",
						"Registration Complete",
						JOptionPane.PLAIN_MESSAGE);
				restartApplication();
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
			if (user.getEmail() != null) {
				System.out.println("Login succeeded!");
				AceChatRedux.executeLogin(user);
				ConversationsView view = new ConversationsView();
				AceChatRedux.displayScreen(view);
				JOptionPane.showMessageDialog(AceChatRedux.mainFrame, "Welcome!", "Login Succeeded",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				System.out.println("Invalid credentials!");
				JOptionPane.showMessageDialog(AceChatRedux.mainFrame, "Username or password is incorrect.",
						"Login Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			System.out.println("There was an error loggin in!");
			JOptionPane.showMessageDialog(AceChatRedux.mainFrame, "Username or password is incorrect.", "Login Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public UserRecord getUserById(String id) {
		try {
			String json = firebaseDatabase.getData("Users/" + id);
			return new Gson().fromJson(json, UserRecord.class);
		} catch (Exception e) {
			return null;
		}
	}

	public UserRecord getUserByUsername(String username) {
		try {
			String json = firebaseDatabase.getData("Users");
			JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
			for (String childKey : jsonObject.keySet()) {
				String child = firebaseDatabase.getData("Users/" + childKey);
				UserRecord user = new Gson().fromJson(child, UserRecord.class);
				if (user.getDisplayName().equals(username)) {
					return user;
				}
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}
	
	private void restartApplication() throws IOException {
		try {
			// java binary
			String java = System.getProperty("java.home") + "/bin/java";
			// vm arguments
			List<String> vmArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
			StringBuffer vmArgsOneLine = new StringBuffer();
			for (String arg : vmArguments) {
				// if it's the agent argument : we ignore it otherwise the
				// address of the old application and the new one will be in conflict
				if (!arg.contains("-agentlib")) {
					vmArgsOneLine.append(arg);
					vmArgsOneLine.append(" ");
				}
			}
			// init the command to execute, add the vm args
			final StringBuffer cmd = new StringBuffer("\"" + java + "\" " + vmArgsOneLine);

			// program main and program arguments
			String[] mainCommand = System.getProperty("sun.java.command").split(" ");
			// program main is a jar
			if (mainCommand[0].endsWith(".jar")) {
				// if it's a jar, add -jar mainJar
				cmd.append("-jar " + new File(mainCommand[0]).getPath());
			} else {
				// else it's a .class, add the classpath and mainClass
				cmd.append("-cp \"" + System.getProperty("java.class.path") + "\" " + mainCommand[0]);
			}
			// finally add program arguments
			for (int i = 1; i < mainCommand.length; i++) {
				cmd.append(" ");
				cmd.append(mainCommand[i]);
			}
			// execute the command in a shutdown hook, to be sure that all the
			// resources have been disposed before restarting the application
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					try {
						Runtime.getRuntime().exec(cmd.toString());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			// exit
			System.exit(0);
		} catch (Exception e) {
			// something went wrong
			throw new IOException("Error while trying to restart the application", e);
		}
	}
}