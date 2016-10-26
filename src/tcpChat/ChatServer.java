package tcpChat;

import java.io.*;
import java.net.*;


public class ChatServer {
	
	private final int PORT = 3835;
	
	private ServerSocket server;
	private Socket socket;
	private InetAddress ip;
	private String name;
	
	public ChatServer(InetAddress serverIP, String myName) throws IOException {
		this.name = myName;
		this.ip = serverIP;
		this.server = new ServerSocket(PORT);
		server.setSoTimeout(10000);

	}
	
	public void start() {
		try {
			this.socket = server.accept();
		} catch (IOException e) {
			System.out.println("Could not start server socket due to error: " + e);
		}
	}
	
	public void stopServer() {
		try {
			socket.close();
			server.close();
		} catch (IOException e) {
			System.out.println("Could not stop server due to error: " + e);
		}

	}
	
	public void sendMessage(String s) {
		try {
			OutputStream outToClient = socket.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToClient);
			out.writeUTF(s);
		} catch (IOException e) {
			System.err.println("serverside message could not be sent");
			e.printStackTrace();
		}
		
	}
	
	public String getMessage() {
		try {
			InputStream inFromClient = this.socket.getInputStream();
			DataInputStream in = new DataInputStream(inFromClient);
			return in.readUTF();
			
		} catch (IOException e)	{ 
			System.err.println("server could not get message");
			e.printStackTrace();
		}
		return "Could not read message...sorry";
	}

	public int getPort() {
		return this.PORT;
	}
	
	public InetAddress getIp() {
		return this.ip;
	}
	
	public String getServerName() {
		return name;
	}

	// not sure what this will do so...yeah
	/*
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
	*/
}
