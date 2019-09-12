package fr.vcapi.packets;

import java.util.UUID;

import fr.vcapi.management.DataClient;
import fr.vcapi.network.Client;
import fr.vcapi.network.Context;
import fr.vcapi.network.NetworkUtilities;

public class ConnectAnswer implements Packet {

	private static final long serialVersionUID = 9204961286127060029L;
	private UUID uuid;

	public ConnectAnswer(UUID uuid) {
		this.uuid = uuid;
	}

	public UUID getUUID() {
		return this.uuid;
	}

	/**
	 * Method only used by when the <a>MessageServer</a> sends a <a>ConnectAnswer</a>
	 * It bounds him to a UUID which will allow him to sign his later messages.
	 * 
	 * @param ctx Context of the call
	 * @param nUtil Context caller
	 * 
	 * @return void
	 */
	@Override
	public void parsePacket(Context ctx, NetworkUtilities nUtil) {
		Client client = (Client)nUtil;
		UUID uuid = ((ConnectAnswer)ctx.getPacket()).getUUID();
		client.setUUID(uuid);
		client.addClient(new DataClient(uuid));
		
	}
}
