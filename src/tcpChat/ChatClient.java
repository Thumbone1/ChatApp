package tcpChat;

import java.net.*;
import java.io.*;

public class ChatClient {
	
	private final int PORT = 3835;
	
	private InetAddress ip;
	private Socket socket;
	private String name;
	
	public ChatClient(InetAddress hostIP, String myName) {
		this.name = myName;
		try {
			ip = hostIP;
			socket = new Socket(ip, PORT);

		} catch(Exception e) {
			System.out.println("Client could not establish a connection: " + e);
		}
	}
		
		
	public void sendMessage(String s) {
		try {
			OutputStream outToServer = this.socket.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);
			out.writeUTF(s);
		} catch (IOException e) {
			System.out.println("message could not be sent");
			e.printStackTrace();
		}
		
	}
	
	public String getMessage() {
		try {
			InputStream inFromServer = this.socket.getInputStream();
			DataInputStream in = new DataInputStream(inFromServer);
			return in.readUTF();
			
		} catch (IOException e)	{ 
			e.printStackTrace();
		}
		return "Could not read message...sorry";
	}

	
	public void stop() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getName() {
		return name;
	}
	

}
