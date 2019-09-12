package fr.vcapi.packets;

import java.io.Serializable;

public interface Packet extends Serializable {

	PacketType packetType = null;
	
	public PacketType getPacketType();
	
	enum PacketType {
		CONNECTION_REQUEST, CONNECTION_ANSWER
	}
	
}
