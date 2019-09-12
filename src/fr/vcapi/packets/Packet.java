package fr.vcapi.packets;

import java.io.Serializable;

import fr.vcapi.network.Context;
import fr.vcapi.network.NetworkUtilities;

public interface Packet extends Serializable {

	/**
	 * 
	 * This function will be used to replace the parsing switch statement by
	 * polymorphism which makes the code a lot easier to read
	 * 
	 * @param ctx
	 * @param nUtil
	 */
	public void parsePacket(Context ctx, NetworkUtilities nUtil);

}
