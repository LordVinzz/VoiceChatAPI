package fr.vcapi.packets;

import fr.vcapi.network.Client;
import fr.vcapi.network.Context;
import fr.vcapi.network.NetworkUtilities;

public class ChangePort implements Packet {

	private static final long serialVersionUID = -3880345997499317206L;
	private int port;

	public ChangePort(int port) {
		this.port = port;
	}

	public int getPort() {
		return this.port;
	}

	/**
	 * Method only used by the <a>Client</a> when the <a>MessageServer</a> sends a
	 * <a>ChangePort</a> It requests to a single client to change it's port to send
	 * messages to the <a>VoiceServer</a> 
	 * @param ctx   Context of the call
	 * @param nUtil Context caller
	 * 
	 * @return void
	 */
	
	@Override
	public void parsePacket(Context ctx, NetworkUtilities nUtil) {
		Client client = (Client) nUtil;
		client.changeVoiceChatPort(port);
	}

}
