package fr.vcapi.utils;

import fr.vcapi.network.Client;
import fr.vcapi.packets.DisconnectRequest;

public class DeathThread {

	public static void attach(Client client) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				client.sendToMessageServer(new DisconnectRequest(client.getUUID()));
			}
		});
	}
}
