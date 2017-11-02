package guide1.ex2;

import java.net.*;
import java.io.*;
/*
 * Diferenças principais entre TCP e UDP
 *  - TCP envolve manutenção da ligação, sendo utilizada em aplicações de comunicação continua, com garantia de entrega
 *  - UDP funciona apenas como pedido/resposta unico, não sendo mantido qualquer tipo de estado ou garantia de entrega
 */
class ServerUDP {
	public static void main(String args[]) {
		DatagramSocket sockfd = null;
		DatagramPacket epacket, rpacket;
		int dim_buffer = 100;
		byte ibuffer[] = new byte[dim_buffer];
		String received = null;
		try { // Cria socket - UDP
			sockfd = new DatagramSocket(5026);
		} catch (SocketException e) {
			e.printStackTrace();
			System.err.println("Erro na execução do servidor (porto):" + e.getMessage());
			System.exit(-1);
		}
		rpacket = new DatagramPacket(ibuffer, dim_buffer);
		for (;;) {
			try {
				System.out.println("Servidor aguarda" + "recepção de mensagem no porto 5026");
				rpacket.setLength(dim_buffer);
				sockfd.receive(rpacket);
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Erro na recepção da" + " mensagem: " + e.getMessage());
				System.exit(-1);
			}
			received = new String(rpacket.getData(), 0, rpacket.getLength());
			// Criar um datagrama para enviar a resposta
			epacket = new DatagramPacket(received.toUpperCase().getBytes(), received.length(), rpacket.getAddress(),
					rpacket.getPort());
			System.out.println("Endereço do cliente:" + epacket.getAddress() + " Porto: " + epacket.getPort());
			System.out.println("Dados recebidos: " + received);
			System.out.println("Número de bytes " + "recebidos: " + rpacket.getLength());
			try {
				sockfd.send(epacket);
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Erro no envio:" + e.getMessage());
				System.exit(-1);
			}
		} // Fim do ciclo do servidor
	}
}