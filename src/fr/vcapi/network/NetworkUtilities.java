package fr.vcapi.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import fr.vcapi.management.DataClient;

public abstract class NetworkUtilities extends Thread{

	protected ArrayList<DataClient> clients = new ArrayList<DataClient>();
	protected int packetSize = 1024;
	protected DatagramSocket socket;

	protected static final int MESSAGE_SERVER_PORT = 1331, VOICE_SERVER_PORT = 1329;
	
	public void sendObject(Object o, InetAddress ip, int port) {
		try {
			ByteArrayOutputStream bStream = new ByteArrayOutputStream();
			ObjectOutput objectOutput = new ObjectOutputStream(bStream); 
			objectOutput.writeObject(o);
			objectOutput.close();
			byte[] serializedMessage = bStream.toByteArray();
			sendData(serializedMessage, ip, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	};
	public void sendData(byte[] data, InetAddress ip, int port) {
		if (data.length > packetSize) throw new ArrayIndexOutOfBoundsException("data.length :" + data.length + " > packetBufferLength");
		DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
		try {
			this.socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	};
	
	public void log(Object o) {
		Date date = new Date();
		String timeStamp = new SimpleDateFormat("HH:mm:ss").format(date);
		System.out.println("[" + timeStamp + "] [" + getClass().getName() + "] " + o.toString());
	}
}
