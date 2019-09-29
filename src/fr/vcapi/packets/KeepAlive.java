package fr.vcapi.packets;

import java.util.UUID;

import fr.vcapi.network.Context;
import fr.vcapi.network.MessageServer;
import fr.vcapi.network.NetworkUtilities;

public class KeepAlive implements Packet {

	private static final long serialVersionUID = -1188259293373177824L;
	private UUID clientUUID;
	
	public KeepAlive(UUID client) {
		this.clientUUID = client;
	}
	
	@Override
	public void parsePacket(Context ctx, NetworkUtilities nUtil) {
		MessageServer server = (MessageServer)nUtil;
		server.getClients();
	}

}