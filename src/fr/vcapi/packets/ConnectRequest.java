package fr.vcapi.packets;

public class ConnectRequest implements Packet {

	private static final long serialVersionUID = -4213027016689187881L;

	public ConnectRequest() {}

	@Override
	public PacketType getPacketType() {
		return PacketType.CONNECTION_REQUEST;
	}

}
