package fr.vcapi.network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import fr.vcapi.management.DataClient;
import fr.vcapi.packets.ConnectAnswer;
import fr.vcapi.packets.ConnectRequest;

public class Client extends NetworkUtilities{

	private InetAddress ipAddress;
	private DataClient me;
	
	public static void main(String[] args) {
		Client client = new Client("localhost", MESSAGE_SERVER_PORT);
		
		client.sendToMessageServer(new ConnectRequest());
		
		client.start();
	}
	
	public Client(String stringAddress, int port) {
		try {
			this.socket = new DatagramSocket();
			this.ipAddress = InetAddress.getByName(stringAddress);
		} catch (Exception e) {
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
				
				switch(ctx.getPacketType()) {
				case CONNECTION_ANSWER:
					ConnectAnswer ca = (ConnectAnswer)ctx.getPacket();
					me = new DataClient(ca.getUUID());
					break;
				case CONNECTION_REQUEST:
					break;
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void sendToMessageServer(Object o) {
		sendObject(o, ipAddress, MESSAGE_SERVER_PORT);
	}
	
	public void sendToVoiceServer(Object o) {
		sendObject(o, ipAddress, VOICE_SERVER_PORT);
	}
	
	public InetAddress getServerIP() {
		return ipAddress;
	}
}
