package fr.vcapi.network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.UUID;

import fr.vcapi.packets.ConnectRequest;
import fr.vcapi.packets.DisconnectRequest;
import fr.vcapi.packets.Packet;
import fr.vcapi.utils.DeathThread;

public class Client extends NetworkUtilities {

	private InetAddress ipAddress;
	private UUID selfUUID = UUID.randomUUID();

	public static void main(String[] args) {
		Client client = new Client("localhost", MESSAGE_SERVER_PORT);

		client.sendToMessageServer(new ConnectRequest(client.getUUID()));
		client.start();
		DeathThread.attach(client);
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

	
	
	/**
	 * Checks if the provided UUID matches with the client's UUID
	 * 
	 * @param uuid
	 * @return
	 */
	public boolean isItMe(UUID uuid) {
		return this.selfUUID.equals(uuid);
	}

	/**
	 * Used to send a packet to the MessageServer
	 * 
	 * @param packet
	 */
	public void sendToMessageServer(Packet packet) {
		sendObject(packet, ipAddress, MESSAGE_SERVER_PORT);
	}

	/**
	 * Used to send a packet to the VoiceServer
	 * 
	 * @param packet
	 */
	public void sendToVoiceServer(Packet packet) {
		sendObject(packet, ipAddress, VOICE_SERVER_PORT);
	}

	/**
	 * Get the client's UUID
	 * 
	 * @return
	 */
	public UUID getUUID() {
		return this.selfUUID;
	}

	/**
	 * Set the client's UUID to selfUUID
	 * 
	 * @param selfUUID
	 */
	public void setUUID(UUID selfUUID) {
		this.selfUUID = selfUUID;
	}
}
