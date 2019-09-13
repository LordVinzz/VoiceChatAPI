package fr.vcapi.packets;

import java.util.UUID;

import fr.vcapi.management.DataClient;
import fr.vcapi.network.Client;
import fr.vcapi.network.Context;
import fr.vcapi.network.NetworkUtilities;

public class RemoveClient implements Packet{

	private static final long serialVersionUID = 988281066184767109L;
	private UUID clientUUID;

	public RemoveClient(UUID client) {
		this.clientUUID = client;
	}

	/**
	 * Method only used by the <a>Client</a> when the <a>MessageServer</a> sends a
	 * <a>RemoveClient</a> It requests to all of the connected clients to remove (
	 * if not already done ), a specific user from the client list.
	 * 
	 * @param ctx   Context of the call
	 * @param nUtil Context caller
	 * 
	 * @return void
	 */
	
	@Override
	public void parsePacket(Context ctx, NetworkUtilities nUtil) {
		Client client = (Client)nUtil;
		
		if (client.clientExists(clientUUID)) {
			client.addClient(new DataClient(clientUUID));
			client.log("Removed Client !");
		}
	}
	
}
