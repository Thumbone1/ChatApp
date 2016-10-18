package tcpChat;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;


public class ChatGUI implements Runnable {

	private JFrame frame;
	private JTextField serverIPText;
	private JTextField userNameText;
	
	private boolean isHost = false;
	private boolean isConnected = false;
	
	private int DISCONNECTED = 0;
	private int CONNECTED = 1;
	private int connectionStatus = DISCONNECTED;
	
	
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
		
		JTextArea incomingTextArea = new JTextArea();
		incomingTextArea.setEditable(false); // uncomment when done testing
		incomingTextArea.setLineWrap(true);
		frame.getContentPane().add(incomingTextArea);
		JScrollPane scroll = new JScrollPane(incomingTextArea, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBounds(245, 11, 236, 199);
		
		frame.getContentPane().add(scroll);
		
	
		//scroll.add(incomingTextArea);
	
		
		
		JTextArea outgoingTextArea = new JTextArea();
		outgoingTextArea.setLineWrap(true);
		
		
		frame.getContentPane().add(outgoingTextArea);
		JScrollPane scroll2 = new JScrollPane(outgoingTextArea,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll2.setBounds(245, 223, 236, 54);
		frame.getContentPane().add(scroll2);
		
		
		/*
		 * Add action listeners to connect and disconnect buttons
		 */
		
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnConnect.setEnabled(false);
				btnDisconnect.setEnabled(true);
				
				incomingTextArea.append("Connecting...\n");
				
			}
		});
		
		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				btnDisconnect.setEnabled(false);
				btnConnect.setEnabled(true);
				
				incomingTextArea.append("Disconnecting...\n");
				
			}
		});
		
	}

	@Override
	public void run() {
		switch(connectionStatus) {
		case DISCONNECTED:
			
			break();
		}
		
	}
}
