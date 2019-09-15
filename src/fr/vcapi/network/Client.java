package fr.vcapi.network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

import fr.vcapi.packets.ConnectRequest;
import fr.vcapi.packets.Packet;
import fr.vcapi.packets.VoicePacket;
import fr.vcapi.utils.DeathThread;

public class Client extends NetworkUtilities {

	private InetAddress ipAddress;
	private UUID selfUUID = UUID.randomUUID();

	private AudioFormat format = new AudioFormat(SAMPLE_RATE, SAMPLE_SIZE, CHANNELS, true, true);
	private DataLine.Info targetInfo = new DataLine.Info(TargetDataLine.class, format);
	private TargetDataLine targetLine;
	
	public static void main(String[] args) {
		Client client = new Client("localhost", MESSAGE_SERVER_PORT);
		
		client.sendToMessageServer(new ConnectRequest(client.getUUID()));
		client.start();
		DeathThread.attach(client);
		
		while(true) {
			client.sendToVoiceServer(new VoicePacket(client.getVoice(), client.getUUID()));
			try {
				TimeUnit.MILLISECONDS.sleep(deadTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public byte[] getVoice() {
		byte[] targetData = new byte[BLOCK_SIZE];
		targetLine.read(targetData, 0, targetData.length);
		return targetData;
	}

	public Client(String stringAddress, int port) {
		try {
			this.socket = new DatagramSocket();
			this.ipAddress = InetAddress.getByName(stringAddress);
			this.targetLine = (TargetDataLine) AudioSystem.getLine(targetInfo);
			this.targetLine.open(format);
			this.targetLine.start();
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
	
	public void changeVoiceChatPort(int port) {
		VOICE_SERVER_PORT = port;
	}
}
