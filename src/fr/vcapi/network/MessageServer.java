package fr.vcapi.network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.UUID;

import fr.vcapi.management.DataClient;
import fr.vcapi.packets.ConnectAnswer;

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
			socket = new DatagramSocket(port);
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
				socket.receive(packet);
				Context ctx = Context.get(packet);
				switch(ctx.getPacketType()) {
				case CONNECTION_REQUEST :
					UUID uuid = UUID.randomUUID();
					clients.add(new DataClient(uuid, ctx.getIp(), ctx.getPort()));
					sendObject(new ConnectAnswer(uuid), ctx.getIp(), ctx.getPort());
					break;
				case CONNECTION_ANSWER :
					break;
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setVoiceServer(VoiceServer vs) {
		voiceServer=vs;
	}
	
	public void runVoiceServer() {
		voiceServer.start();
	}
	
}
