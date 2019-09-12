package fr.vcapi.packets;

import java.io.Serializable;

import fr.vcapi.network.Context;
import fr.vcapi.network.NetworkUtilities;

public interface Packet extends Serializable {

	public void parsePacket(Context ctx, NetworkUtilities nUtil);
	
}
