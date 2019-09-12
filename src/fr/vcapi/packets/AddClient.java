package fr.vcapi.packets;

import fr.vcapi.management.DataClient;
import fr.vcapi.network.Context;
import fr.vcapi.network.NetworkUtilities;

public class AddClient implements Packet {

	private static final long serialVersionUID = -6920893443737512553L;

	DataClient client;

	public AddClient(DataClient client) {
		this.client = client;
	}

	@Override
	public void parsePacket(Context ctx, NetworkUtilities nUtil) {
		
	}

}
