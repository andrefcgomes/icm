package guide1.ex2;

import java.io.*;
import java.net.*;

public class ClienteUDP {
	public static void main(String[] args) throws IOException {
		DatagramSocket sockfd = null;
		String host = "localhost";
		DatagramPacket epacket = null, rpacket = null;
		try { // Cria socket – UDP
			sockfd = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		// Mostrar os parametros da ligação
		System.out.println("Endereço do Servidor: " + InetAddress.getByName(host) + " Porto: " + 5026);
		System.out.println("Endereço Local: " + sockfd.getLocalAddress() + " Porto: " + sockfd.getLocalPort());
		// constroi mensagem e Envia pedido
		String userInput = new String("Olá mundo!!");
		epacket = new DatagramPacket(userInput.getBytes(), userInput.length(), InetAddress.getByName(host), 5026);
		try {
			sockfd.send(epacket);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Erro no envio da mensagem: " + e.getMessage());
			System.exit(-1);
		}
		// Recebe resposta
		byte[] buf = new byte[100];
		rpacket = new DatagramPacket(buf, buf.length);
		try {
			sockfd.receive(rpacket);
		} catch (IOException e) {
			System.err.println("Erro na recepção da mensagem: " + e.getMessage());
			System.exit(-1);
		}
		// Mostra Resposta
		String received = new String(rpacket.getData(), 0, rpacket.getLength());
		System.out.println("Dados recebidos: " + received);
		// No fim de tudo fechar o socket
		sockfd.close();
	}
}