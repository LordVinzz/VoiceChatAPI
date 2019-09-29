package fr.vcapi.packets;

import java.util.UUID;

import fr.vcapi.management.DataClient;
import fr.vcapi.network.Client;
import fr.vcapi.network.Context;
import fr.vcapi.network.NetworkUtilities;
import fr.vcapi.network.VoiceServer;

public class VoicePacket implements Packet {

	private static final long serialVersionUID = -8310956329351627200L;
	
	byte data[] = new byte[NetworkUtilities.BLOCK_SIZE];
	UUID signature;
	
	public VoicePacket(byte[] data, UUID signature) {
		this.data = data;
		this.signature = signature;
	}
	
	public byte[] getData() {
		return this.data;
	}
	
	public int getLength() {
		return this.data.length;
	}

	@Override
	public void parsePacket(Context ctx, NetworkUtilities nUtil) {
		if(nUtil instanceof Client) {
			Client client = (Client)nUtil;
			DataClient player;
			if((player = client.getClientByUUID(signature)) != null) {
				player.getAudioThread().addToQueue(this);
			}
		}else if(nUtil instanceof VoiceServer) {
			VoiceServer server = (VoiceServer)nUtil;

			for(DataClient client : server.getClients()) {
				if(!client.getUUID().equals(signature)) {
					server.sendObject(this, client.getIP(), client.getPort());
				}
			}
		}
	}

}
