package fr.vcapi.packets;

import java.util.UUID;

import fr.vcapi.audio.AudioOutputThread;
import fr.vcapi.management.DataClient;
import fr.vcapi.network.Client;
import fr.vcapi.network.Context;
import fr.vcapi.network.NetworkUtilities;

public class AddClient implements Packet {

	private static final long serialVersionUID = -6920893443737512553L;
	private UUID clientUUID;

	public AddClient(UUID client) {
		this.clientUUID = client;
	}

	/**
	 * Method only used by the <a>Client</a> when the <a>MessageServer</a> sends a
	 * <a>AddClient</a> It requests to all of the connected clients to add ( if not
	 * already done ), a specific user to the client list.
	 * 
	 * @param ctx   Context of the call
	 * @param nUtil Context caller
	 * 
	 * @return void
	 */

	@Override
	public void parsePacket(Context ctx, NetworkUtilities nUtil) {
		Client client = (Client)nUtil;
		
		/*
		 * If the client is already registered, we don't register him twice,
		 * and if we are the user, we don't register ourselves 
		 */
		if (!client.clientExists(clientUUID) && !client.isItMe(clientUUID)) {
			DataClient dtaClient = new DataClient(clientUUID, new AudioOutputThread());
			dtaClient.startAudioThread();
			client.addClient(dtaClient);
			client.log("New client registered !");
		}
	}
}
