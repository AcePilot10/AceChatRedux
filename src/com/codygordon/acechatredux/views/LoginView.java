package com.codygordon.acechatredux.views;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.codygordon.acechatredux.AceChatRedux;
import com.codygordon.acechatredux.backgroundworker.BackgroundTask;
import com.codygordon.acechatredux.backgroundworker.BackgroundWorker;
import com.codygordon.acechatredux.backgroundworker.IBackgroundWorker;
import com.codygordon.acechatredux.models.UserCredentials;

public class LoginView extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextField txtEmail;
	private JPasswordField txtPassword;
	private JActivityIndicator activityIndicator;
	private JButton btnLogin;

	public LoginView() {
		setLayout(null);
		setSize(650, 430);

		activityIndicator = new JActivityIndicator(JActivityIndicator.CIRCLE_LIGHT_GREY);
		activityIndicator.setText("");
		activityIndicator.setBounds(293, 169, 64, 64);
		activityIndicator.setVisible(false);
		add(activityIndicator);

		JLabel lblTitle = new JLabel("Login");
		lblTitle.setBackground(SystemColor.control);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(279, 26, 92, 21);
		add(lblTitle);

		txtEmail = new JTextField();
		txtEmail.setHorizontalAlignment(SwingConstants.CENTER);
		txtEmail.setBounds(199, 76, 252, 48);
		add(txtEmail);
		txtEmail.setColumns(10);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setBounds(293, 153, 64, 14);
		add(lblEmail);

		txtPassword = new JPasswordField();
		txtPassword.setHorizontalAlignment(SwingConstants.CENTER);
		txtPassword.setColumns(10);
		txtPassword.setBounds(199, 196, 252, 48);
		add(txtPassword);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setBounds(293, 273, 64, 14);
		add(lblPassword);

		btnLogin = new JButton("Login");
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
		btnLogin.setEnabled(false);
		activityIndicator.setVisible(true);
		activityIndicator.startRotating();
		@SuppressWarnings("deprecation")
		UserCredentials credentials = new UserCredentials(txtEmail.getText(), txtPassword.getText());
		BackgroundWorker.runInBackground(
				new BackgroundTask(() -> AceChatRedux.userController.loginUser(credentials), new IBackgroundWorker() {
					@Override
					public void onComplete() {
						activityIndicator.stopRotating();
						activityIndicator.setVisible(false);
						btnLogin.setEnabled(true);
					}

					@Override
					public void onError(Exception e) {
						System.out.println("Error");
					}
				}));
	}

	private void registerButtonClicked() {
		AceChatRedux.displayScreen(new RegisterView());
	}
}