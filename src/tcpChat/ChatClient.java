package tcpChat;

import java.net.*;


public class ChatClient {
	
	public ChatClient(String hostIP, int port) {
		try {
			Socket socket = new Socket(hostIP, port);
			
			
		} catch(Exception e) {
			System.out.println("Could not establish a connection: " + e);
		}
	}

}
