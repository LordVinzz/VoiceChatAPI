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
	
	/**
	 * This function gets raw data from the datagram packet to transform it into an object
	 * using context is very important cause it can carry useful informations for the server
	 * such as the ip of the sender, on the other hand because of polymorphism, context might
	 * not be useful at all for the clients
	 * 
	 * @param datagramPacket
	 * @return Context
	 */
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
	
	public String getStringIP() {
		return getIP().getCanonicalHostName() + ":" + getPort();
	}
}
