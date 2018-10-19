package com.codygordon.acechatredux.views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.codygordon.acechatredux.AceChatRedux;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.GridLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AceChatWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	public JPanel contentScreen;
	
	private int xx, xy;

	public AceChatWindow() {
		setResizable(false);
		setUndecorated(true);
		setTitle("Ace Chat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 706, 499);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xx = e.getX();
				xy = e.getY();
			}
		});
		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				int x = arg0.getXOnScreen();
				int y = arg0.getYOnScreen();
				AceChatWindow.this.setLocation(x - xx, y - xy);
			}
		});
		
		contentScreen = new JPanel();
		contentScreen.setBackground(Color.LIGHT_GRAY);
		contentScreen.setBounds(28, 56, 650, 430);
		contentPane.add(contentScreen);
		contentScreen.setLayout(new GridLayout(1, 0, 0, 0));
		
		
		JLabel lblX = new JLabel("X");
		lblX.setBounds(660, 0, 46, 22);
		contentPane.add(lblX);
		lblX.setHorizontalAlignment(SwingConstants.TRAILING);
		lblX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(1);
			}
		});
		lblX.setForeground(Color.RED);
		lblX.setFont(new Font("Tahoma", Font.PLAIN, 24));
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 662, 22);
		contentPane.add(menuBar);
		
		JMenu mnUser = new JMenu("User");
		menuBar.add(mnUser);
		
		JMenuItem mntmLogOut = new JMenuItem("Log Out");
		mntmLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logout();
			}
		});
		mnUser.add(mntmLogOut);
	}
	
	private void logout() {
		AceChatRedux.currentUser = null;
		AceChatRedux.displayScreen(new LoginView());
	}
}
