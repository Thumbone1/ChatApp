package tcpChat;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class ChatGUI {

	private JFrame frame;
	private JTextField serverIPText;
	private JTextField userNameText;
	
	private boolean isHost = false;
	private boolean isConnected = false;
	
	private static ChatServer server;
	private static ChatClient client;
	private String name;
	
	//TODO: need to check how to constantly update jtextarea
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatGUI window = new ChatGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}

	/**
	 * Create the application.
	 */
	public ChatGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame("Simple Chat");
		frame.setBounds(100, 100, 534, 348);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		JLabel lblServerIp = new JLabel("Server IP");
		lblServerIp.setVerticalAlignment(SwingConstants.TOP);
		lblServerIp.setHorizontalAlignment(SwingConstants.LEFT);
		lblServerIp.setBounds(10, 11, 89, 14);
		frame.getContentPane().add(lblServerIp);
		
		serverIPText = new JTextField();
		serverIPText.setBounds(129, 8, 86, 20);
		frame.getContentPane().add(serverIPText);
		serverIPText.setColumns(10);
		
		
		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setBounds(10, 42, 89, 14);
		frame.getContentPane().add(lblUserName);
		
		userNameText = new JTextField();
		userNameText.setBounds(129, 39, 86, 20);
		frame.getContentPane().add(userNameText);
		userNameText.setColumns(10);
		
		/*
		 * Add radio buttons Host and Client
		 * and create a button group so only one can
		 * be selected.
		 */
		
		JRadioButton rdbtnHost = new JRadioButton("Host");
		rdbtnHost.setBounds(10, 97, 109, 23);
		frame.getContentPane().add(rdbtnHost);
		
		JRadioButton rdbtnClient = new JRadioButton("Client");
		rdbtnClient.setBounds(129, 97, 109, 23);
		frame.getContentPane().add(rdbtnClient);
		
		ButtonGroup rdbtnGroup = new ButtonGroup();
		rdbtnGroup.add(rdbtnHost);
		rdbtnGroup.add(rdbtnClient);
		
		/*
		 * Add connect and disconnect buttons with
		 * tool tips (that are obvious).
		 */
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.setBounds(10, 224, 89, 23);
		frame.getContentPane().add(btnConnect);
		btnConnect.setToolTipText("Click here to disconnect");
		
		JButton btnDisconnect = new JButton("Disconnect");
		btnDisconnect.setBounds(126, 224, 89, 23);
		frame.getContentPane().add(btnDisconnect);
		btnDisconnect.setToolTipText("Click here to disconnect");
		
		// incoming text area
		JTextArea incomingTextArea = new JTextArea();
		incomingTextArea.setEditable(false); 
		incomingTextArea.setLineWrap(true);
		frame.getContentPane().add(incomingTextArea);
		JScrollPane scroll = new JScrollPane(incomingTextArea, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBounds(245, 11, 236, 199);
		
		frame.getContentPane().add(scroll);
		
		// outgoing text area
		JTextArea outgoingTextArea = new JTextArea();
		outgoingTextArea.setLineWrap(true);
		frame.getContentPane().add(outgoingTextArea);
		JScrollPane scroll2 = new JScrollPane(outgoingTextArea,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll2.setBounds(245, 223, 236, 54);
		frame.getContentPane().add(scroll2);
		
		//-------------------------------------------------
		/*
		 * Add action listeners to connect and disconnect buttons and 
		 * RUN PROGRAM BELOW
		 */
		//-------------------------------------------------

		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				name = userNameText.getText();
				btnConnect.setEnabled(false);
				btnDisconnect.setEnabled(true);
				serverIPText.setEnabled(false);
				userNameText.setEnabled(false);
				incomingTextArea.append("Connecting as: " + name + "...\n");
				
				
				if (rdbtnHost.isSelected()) {
					try {
						InetAddress ip = InetAddress.getByName(serverIPText.getText());
						server = new ChatServer(ip, name);
						server.start();
						ChatThread t = new ChatThread();
						incomingTextArea.append("Connected... \n");
						isConnected = true;
						t.start();
						
						
					} catch (IOException e1) {
						System.out.println("error has occured:");
						e1.printStackTrace();
					}
					isHost = true;
					
					
				} else if (rdbtnClient.isSelected()) {
					try {
						InetAddress ip = InetAddress.getByName(serverIPText.getText());
						client = new ChatClient(ip, name);
						incomingTextArea.append("Connected... \n");
						ChatThread t = new ChatThread();
						isConnected = true;
						t.start();
						
					} catch (IOException e1) {
						e1.printStackTrace();
					}

				}
				
			}
		});
		
		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				btnDisconnect.setEnabled(false);
				btnConnect.setEnabled(true);
				lblServerIp.setEnabled(true);
				lblUserName.setEnabled(true);
				incomingTextArea.append("Disconnecting...\n");
				if (rdbtnHost.isSelected()) {
					server.stopServer();
					incomingTextArea.append("Disconnected...\n");
					
				} else if (rdbtnClient.isSelected()) {
					client.stopClient();
					incomingTextArea.append("Disconnected...\n");
				}
				isConnected = false;
				
			}
		});
		
		outgoingTextArea.addKeyListener(new KeyListener(){
		    public void keyPressed(KeyEvent e){
		    	String s = outgoingTextArea.getText();
		        if(e.getKeyCode() == KeyEvent.VK_ENTER && isConnected){
		        	if (isHost) {
		        		server.sendMessage(s);
		        		incomingTextArea.append(name + ": " + s + "\n");
		        		outgoingTextArea.setText("");
		        	} else {
		        		client.sendMessage(s);
		        		incomingTextArea.append(name + ": " + s + "\n");
		        		outgoingTextArea.setText("");
		        	}
	
		        } else if (e.getKeyCode() == KeyEvent.VK_ENTER && !isConnected){
		        	incomingTextArea.append("You are not connected, message not sent");
		        }
		    }
			@Override
			public void keyReleased(KeyEvent arg0) {	
				
			}
			@Override
			public void keyTyped(KeyEvent arg0) {				
			}
		});
		
		
	}
	
	/**
	 * Thread for reading messages to gui
	 * I'm so fucking close on this one
	 * 
	 */
	private class ChatThread extends Thread {
		public void run() {
			if (!isHost) {
				client.getMessage();
				System.out.println("receiving a message on clientside");
			} else {
				server.getMessage();
				System.out.println("receiving a message on clientside");
			}
			
			
		}
	}


}
