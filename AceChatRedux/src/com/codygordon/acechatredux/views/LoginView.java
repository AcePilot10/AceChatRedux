package com.codygordon.acechatredux.views;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.codygordon.acechatredux.AceChatRedux;
import com.codygordon.acechatredux.models.UserCredentials;

public class LoginView extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTextField txtEmail;
	private JTextField txtPassword;
	
	public LoginView() {
		setLayout(null);
		setSize(650, 430);
		
		JLabel lblTitle = new JLabel("Login");
		lblTitle.setBackground(SystemColor.control);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(279, 26, 92, 21);
		add(lblTitle);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(199, 76, 252, 48);
		add(txtEmail);
		txtEmail.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setBounds(293, 153, 64, 14);
		add(lblEmail);
		
		txtPassword = new JTextField();
		txtPassword.setColumns(10);
		txtPassword.setBounds(199, 196, 252, 48);
		add(txtPassword);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setBounds(293, 273, 64, 14);
		add(lblPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginButtonClicked();
			}
		});
		btnLogin.setBounds(280, 316, 89, 23);
		add(btnLogin);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registerButtonClicked();
			}
		});
		btnRegister.setBounds(280, 368, 89, 23);
		add(btnRegister);
	}
	
	private void loginButtonClicked() {
		System.out.println("Logging in...");
		UserCredentials credentials = new UserCredentials(txtEmail.getText(), txtPassword.getText());
		AceChatRedux.userController.loginUser(credentials);
	}
	
	private void registerButtonClicked() {
		AceChatRedux.displayScreen(new RegisterView());
	}
}