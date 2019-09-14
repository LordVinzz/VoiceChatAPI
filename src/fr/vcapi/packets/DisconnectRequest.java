package fr.vcapi.packets;

import java.util.UUID;

import fr.vcapi.network.Context;
import fr.vcapi.network.MessageServer;
import fr.vcapi.network.NetworkUtilities;

public class DisconnectRequest implements Packet {

	private static final long serialVersionUID = -3069208048242263881L;
	private UUID clientUUID;
	
	public DisconnectRequest(UUID client) {
		this.clientUUID = client;
	}

	/**
	 * Method only used by the <a>MessageServer</a> when the <a>Client</a> sends a
	 * <a>DisconnectRequest</a> Sends a RemoveClient packet to all of the users to
	 * remove the specified user from the client list.
	 * 
	 * @param ctx   Context of the call
	 * @param nUtil Context caller
	 * 
	 * @return void
	 */

	@Override
	public void parsePacket(Context ctx, NetworkUtilities nUtil) {
		MessageServer server = (MessageServer) nUtil;
		server.removeClientByUUID(clientUUID);
		server.log("Client " + ctx.getStringIP() + " disconnected");
		server.sendObjectToAll(new RemoveClient(clientUUID));
	}

}
