package fr.vcapi.network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class VoiceServer extends NetworkUtilities {

	private int port;

	public VoiceServer(int port) {
		try {
			this.port = port;
			this.socket = new DatagramSocket(port);
		} catch (SocketException e) {
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
	 * @return The port of the VoiceServer
	 */
	public int getPort() {
		return this.port;
	}
}
