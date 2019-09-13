package fr.vcapi.packets;

import java.util.UUID;

import fr.vcapi.management.DataClient;
import fr.vcapi.network.Client;
import fr.vcapi.network.Context;
import fr.vcapi.network.NetworkUtilities;

public class RemoveClient implements Packet{

	private static final long serialVersionUID = 988281066184767109L;
	UUID clientUUID;

	public RemoveClient(UUID client) {
		this.clientUUID = client;
	}

	public UUID getClientUUID() {
		return this.clientUUID;
	}
	
	/**
	 * Method only used by the <a>Client</a> when the <a>MessageServer</a> sends a <a>RemoveClient</a>
	 * It requests to all of the connected clients to remove ( if not already done ), a specific
	 * user to the client list.
	 * 
	 * @param ctx Context of the call
	 * @param nUtil Context caller
	 * 
	 * @return void
	 */
	
	@Override
	public void parsePacket(Context ctx, NetworkUtilities nUtil) {
		Client client = (Client)nUtil;
		RemoveClient packet = (RemoveClient)ctx.getPacket();
		
		if (client.clientExists(packet.getClientUUID())) {
			client.addClient(new DataClient(packet.getClientUUID()));
			client.log("Removed Client !");
		}
	}
	
}
