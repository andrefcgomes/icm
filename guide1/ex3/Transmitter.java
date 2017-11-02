package guide1.ex3;

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Transmitter {
	Scanner reader = new Scanner(System.in);
	private String getUserInput() {

		System.out.println("INTRODUZA O TEXTO A ANUNCIAR: ");
		String n = reader.nextLine();

		return n;
	}
	public static void main(String[] args) {
		Transmitter t = new Transmitter();
		String text_to_send = t.getUserInput();
		InetAddress ia = null;
		int port = 4001;
		byte ttl = (byte) 1;
		String host = null;
		try {
			ia = InetAddress.getByName("224.2.127.254");
			host = InetAddress.getLocalHost().getHostName();
		} catch (Exception ex) {
			System.err.println("MulticastSender Error:" + ex);
			System.exit(1);
		}
		byte[] data = text_to_send.getBytes();
		System.out.println("sending ->"+text_to_send);
		DatagramPacket dp = new DatagramPacket(data, data.length, ia, port);

		try {
			MulticastSocket ms = new MulticastSocket();
			ms.joinGroup(ia);
			ms.setTimeToLive(ttl);
			ms.send(dp);
			ms.leaveGroup(ia);
			ms.close();
		} catch (SocketException ex) {
			System.err.println(ex);
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
}