package fr.vcapi.packets;

import java.util.UUID;

public class ConnectAnswer implements Packet {

	private static final long serialVersionUID = 9204961286127060029L;
	private UUID uuid;

	public ConnectAnswer(UUID uuid) {
		this.uuid = uuid;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.CONNECTION_ANSWER;
	}

	public UUID getUUID() {
		return uuid;
	}
}
