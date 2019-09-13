package fr.vcapi.packets;

import fr.vcapi.network.Context;
import fr.vcapi.network.NetworkUtilities;

public class ChangePort implements Packet{

	private static final long serialVersionUID = -3880345997499317206L;
	private int port;
	
	public ChangePort() {
		
	}
	
	@Override
	public void parsePacket(Context ctx, NetworkUtilities nUtil) {
		
	}

	
	
}
