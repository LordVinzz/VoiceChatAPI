package fr.vcapi.network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.UUID;

import fr.vcapi.management.DataClient;
import fr.vcapi.packets.ConnectRequest;

public class Client extends NetworkUtilities {

	private InetAddress ipAddress;
	private UUID selfUUID = UUID.randomUUID();

	public static void main(String[] args) {
		Client client = new Client("localhost", MESSAGE_SERVER_PORT);

		client.sendToMessageServer(new ConnectRequest(client.getUUID()));

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
		while (true) {
			byte[] data = new byte[packetSize];
			DatagramPacket packet = new DatagramPacket(data, data.length);

			try {
				this.socket.receive(packet);
				Context ctx = Context.get(packet);
				ctx.getPacket().parsePacket(ctx, this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isItMe(UUID uuid) {
		return this.selfUUID.equals(uuid);
	}

	public void sendToMessageServer(Object o) {
		sendObject(o, ipAddress, MESSAGE_SERVER_PORT);
	}

	public void sendToVoiceServer(Object o) {
		sendObject(o, ipAddress, VOICE_SERVER_PORT);
	}

	public InetAddress getServerIP() {
		return this.ipAddress;
	}

	public UUID getUUID() {
		return this.selfUUID;
	}

	public void setUUID(UUID selfUUID) {
		this.selfUUID = selfUUID;
	}
}
