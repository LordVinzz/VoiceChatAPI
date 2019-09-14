package fr.vcapi.management;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.UUID;

import fr.vcapi.audio.AudioOutputThread;
import fr.vcapi.network.Context;

public class DataClient implements Serializable {

	private static final long serialVersionUID = -852244219002721768L;

	private UUID uuid;
	private InetAddress ip;
	private int port;
	private AudioOutputThread audioThread;	
	
	public DataClient(UUID uuid, Context ctx) {
		this.uuid = uuid;
		this.ip = ctx.getIP();
		this.port = ctx.getPort();
	}
	
	public DataClient(UUID uuid, InetAddress ip, int port) {
		this.uuid = uuid;
		this.ip = ip;
		this.port = port;
	}

	public DataClient(UUID uuid, AudioOutputThread audioThread) {
		this.uuid = uuid;
		this.audioThread = audioThread;
	}
	
	public DataClient(UUID uuid) {
		this.uuid = uuid;
	}

	public InetAddress getIP() {
		return this.ip;
	}
	
	public int getPort() {
		return this.port;
	}
	
	public UUID getUUID() {
		return this.uuid;
	}
	
	public AudioOutputThread getAudioThread() {
		return this.audioThread;
	}
	
	public void startAudioThread() {
		this.audioThread.start();
	}
}
