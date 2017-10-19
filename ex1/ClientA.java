package ex1;

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class ClientA {
	Socket socket;
	BufferedReader in;
	PrintWriter out;
	int port = 8888;
	String host = "localhost";
	Scanner reader = new Scanner(System.in);
	
	private void init() {
		System.out.println("INICIALIZAR CLIENTE");
		try {
			socket = new Socket(host, port);
			System.out.println("Endereço do Servidor: " + socket.getInetAddress() + " Porto: " + socket.getPort());
			System.out.println("Endereço Local: " + socket.getLocalAddress() + " Porto: " + socket.getLocalPort());
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Erro no estabelecimento da ligação:" + e.getMessage());
			System.exit(1);
		}
	}

	private void close() throws IOException {
		in.close();
		out.close();
		socket.close();
		reader.close();
	}

	private void sendMessage(String s) {
		out.println(s);
	}

	private String read() throws IOException {
		String message = in.readLine();
		System.out.println("RECEBI -> " + message);
		return message;
	}

	private String getUserInput() {

		System.out.println("INTRODUZA TEXTO: ");
		String n = reader.nextLine();

		return n;
	}

	private void start() throws IOException {
	
		init();
		String message = getUserInput();
		while(!message.equals("END")) {
		sendMessage(message);
		read();
		message = getUserInput();
		}
		
		close();
	}

	public static void main(String[] args) throws IOException {
//		Thread one = new Thread() {
//		    public void run() {
//		        try {
//		            System.out.println("Does it work?");
//		    		ClientA c = new ClientA();
//		    		c.start();
//		            Thread.sleep(1000);
//
//		            System.out.println("Nope, it doesnt...again.");
//		        } catch(InterruptedException | IOException v) {
//		            System.out.println(v);
//		        }
//		    }  
//		};
//
//		one.start();
//		Thread two = new Thread() {
//		    public void run() {
//		        try {
//		            System.out.println("Does it work?");
//		    		ClientA c = new ClientA();
//		    		c.start();
//		            Thread.sleep(1000);
//
//		            System.out.println("Nope, it doesnt...again.");
//		        } catch(InterruptedException | IOException v) {
//		            System.out.println(v);
//		        }
//		    }  
//		};
//
//		two.start();
		ClientA c = new ClientA();
		c.start();
//		ClientA c2 = new ClientA();
//		c2.start();
	}

}
