package tcpChat;

import java.io.*;
import java.net.*;


public class ChatServer {
	
	private final int PORT = 3835;
	
	private ServerSocket server;
	private Socket socket;
	private InetAddress ip;
	private String message;
	
	public ChatServer(InetAddress serverIP) throws IOException {
		try {
			this.server = new ServerSocket(PORT);
			this.socket = server.accept();
			this.ip = serverIP;
			
			
		} catch(Exception e) {
			System.out.println("Could not establish a server due to exception: " + e);
		}

	}
	
	public void stop() {
		try {
			socket.close();
			server.close();
		} catch (IOException e) {
			
			System.out.println("Could not stop server due to error: " + e);
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
	
	public String getOutgoingMessage() throws IOException {
		
		try {
			return this.socket.getOutputStream().toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "error";
	}
	
	public String getIncomingMessage() throws IOException {
		try {
			return this.socket.getInputStream().toString();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return "error";
		
	}
	
	public int getPort() {
		return this.PORT;
	}
	
	public InetAddress getIp() {
		return this.ip;
	}

}
