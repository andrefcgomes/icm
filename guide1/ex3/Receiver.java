package guide1.ex3;

import java.net.*;
import java.io.*;

public class Receiver {
	
	
	public static void main(String[] args) {
		InetAddress mAddr = null;
		MulticastSocket mSocket = null;
		final int PORT_NUM = 4001;
		DatagramPacket dp;
		try {
			mAddr = InetAddress.getByName("224.2.127.254");
			mSocket = new MulticastSocket(PORT_NUM);
			String hostname = InetAddress.getLocalHost().getHostName();
			byte[] buffer = new byte[8192];
			mSocket.joinGroup(mAddr);
			System.out.println("Listening from " + hostname + " at " + mAddr.getHostName());
			dp = new DatagramPacket(buffer, buffer.length);
			while (true) {
				mSocket.receive(dp);
				String str = new String(dp.getData());
				System.out.println(str);
				byte[] newbuffer = new byte[8192];
				dp = new DatagramPacket(newbuffer, newbuffer.length);
			} // end of while
		} catch (SocketException se) {
			System.out.println("Socket Exception : " + se);
		} catch (IOException e) {
			System.out.println("Exception : " + e);
		} finally {
			if (mSocket != null) {
				try {
					mSocket.leaveGroup(mAddr);
					mSocket.close();
				} catch (IOException e) {
				}
			} // end of if
		} // end of finally
	}// end of main
}