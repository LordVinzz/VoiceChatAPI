package fr.vcapi.network;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;

import fr.vcapi.packets.Packet;

public class Context {

	private InetAddress ip;
	private int port;
	private byte[] rawData;
	private Packet packet;
	
	public static Context get(DatagramPacket datagramPacket) {
		Context ctx = new Context();
		ctx.ip = datagramPacket.getAddress();
		ctx.port = datagramPacket.getPort();
		ctx.rawData = datagramPacket.getData();
		
		ObjectInputStream stream = null;
		try {
			stream = new ObjectInputStream(new ByteArrayInputStream(ctx.rawData));
			ctx.packet = (Packet) stream.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return ctx;
	}
	
	/**
	 * @return The Context's IP
	 */
	public InetAddress getIP() {
		return this.ip;
	}
	
	/**
	 * @return The Context's port
	 */
	public int getPort() {
		return this.port;
	}
	
	/**
	 * @return The Context's unprocessed packet
	 */
	public byte[] getRawData() {
		return this.rawData;
	}
	
	/**
	 * @return The Context's unprocessed packet length
	 */
	public int getRawDataLength() {
		return this.rawData.length;
	}
	
	/**
	 * @return The Context's packet
	 */
	public Packet getPacket() {
		return this.packet;
	}

	@Override
	public String toString() {
		return "Context [ip=" + ip + ", port=" + port + "]";
	}
}
