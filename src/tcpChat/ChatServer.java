package tcpChat;

import java.io.IOException;
import java.net.*;



public class ChatServer {
	
	ServerSocket server;
	Socket socket;
	
	public ChatServer(int port) {
		try {
			server = new ServerSocket(port);
			socket = server.accept();
			
			
		} catch(Exception e) {
			System.out.println("Could not establish a server due to exception: " + e);
		}
		
		
	}
	
	public void stopChatServer() {
		try {
			socket.close();
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not stop server due to exception: " + e);
		}
		
		
	}
	

}
