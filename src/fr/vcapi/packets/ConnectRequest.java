package fr.vcapi.packets;

import java.util.UUID;

import fr.vcapi.management.DataClient;
import fr.vcapi.network.Context;
import fr.vcapi.network.MessageServer;
import fr.vcapi.network.NetworkUtilities;

public class ConnectRequest implements Packet {

	private static final long serialVersionUID = -4213027016689187881L;

	public ConnectRequest() {}

	/**
	 * Method only used by the <a>MessageServer</a> class when a client connects.
	 * It bounds him to a UUID which will allow him to sign his later messages.
	 * 
	 * @param ctx Context of the call
	 * @param nUtil Context caller
	 * 
	 * @return void
	 */
	
	@Override
	public void parsePacket(Context ctx, NetworkUtilities nUtil) {
		MessageServer server = (MessageServer)nUtil;
		if(!server.clientExists(ctx)) {
			UUID uuid = UUID.randomUUID();
			server.addClient(new DataClient(uuid, ctx));
			server.sendObject(new ConnectAnswer(uuid), ctx.getIp(), ctx.getPort());
			server.log("New client connected successfully");
		} else {
			server.log("Client " + ctx.toString() + " attempted a double connection");
		}
	}
	
}
