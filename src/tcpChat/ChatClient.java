package tcpChat;

import java.net.*;
import java.io.*;
import java.lang.*;


public class ChatClient {
	
	private final int PORT = 3835;
	
	private InetAddress ip;
	private String message;
	private Socket socket;
	
	
	public ChatClient(InetAddress hostIP) {
		try {
			this.socket = new Socket(ip, PORT);
			this.ip = hostIP;

		} catch(Exception e) {
			System.out.println("Could not establish a connection: " + e);
		}
	}
		
		
	public void sendMessage(String s) {
		message = s;
		try {
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			out.print(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public String getMessage() {
		try {
			return this.socket.getInputStream().toString();
		} catch (IOException e)	{ 
			e.printStackTrace();
			
		}
		return "error";
	}
	
	public void stop() {
		try {
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
