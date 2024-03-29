package fr.vcapi.network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import fr.vcapi.management.DataClient;

public class MessageServer extends NetworkUtilities {

	private VoiceServer voiceServer;
	
	public static void main(String[] args) {
		MessageServer server = new MessageServer(MESSAGE_SERVER_PORT);
		server.setVoiceServer(new VoiceServer(VOICE_SERVER_PORT));
		server.runVoiceServer();
		server.start();
	}
	
	public MessageServer(int port) {
		try {
			this.socket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(true) {
			byte[] data = new byte[packetSize];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			
			try {
				this.socket.receive(packet);
				Context ctx = Context.get(packet);
				ctx.getPacket().parsePacket(ctx, this);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Sets the MessageSever's VoiceServer to vs
	 * 
	 * @param vs
	 */
	public void setVoiceServer(VoiceServer vs) {
		voiceServer=vs;
	}
	
	/**
	 * Runs a new thread for the VoiceServer
	 */
	public void runVoiceServer() {
		voiceServer.start();
	}
	
	@Override
	public void addClient(DataClient client) {
		super.addClient(client);
		this.voiceServer.addClient(client);
	}
	
}
