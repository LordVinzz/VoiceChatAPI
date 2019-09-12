package fr.vcapi.network;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;

import fr.vcapi.packets.Packet;
import fr.vcapi.packets.Packet.PacketType;

public class Context {

	private InetAddress ip;
	private int port;
	private byte[] rawData;
	private Packet packet;
	private PacketType packetType;
	
	public static Context get(DatagramPacket datagramPacket) {
		Context ctx = new Context();
		ctx.ip = datagramPacket.getAddress();
		ctx.port = datagramPacket.getPort();
		ctx.rawData = datagramPacket.getData();
		
		ObjectInputStream stream = null;
		try {
			stream = new ObjectInputStream(new ByteArrayInputStream(ctx.rawData));
			ctx.packet = (Packet) stream.readObject();
			ctx.packetType = ctx.packet.getPacketType();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return ctx;
	}
	
	public InetAddress getIp() {
		return ip;
	}
	
	public int getPort() {
		return port;
	}
	
	public byte[] getRawData() {
		return rawData;
	}
	
	public int getRawDataLength() {
		return rawData.length;
	}
	
	public Packet getPacket() {
		return packet;
	}
	
	public PacketType getPacketType() {
		return packetType;
	}
	
}
