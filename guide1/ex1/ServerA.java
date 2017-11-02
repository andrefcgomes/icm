package guide1.ex1;

import java.net.*;
import java.io.*;

public class ServerA {
	Socket socket;
	BufferedReader in;
	PrintWriter out;
	int port = 8888;
	String host = "localhost";

	private void close(Socket s) throws IOException {
		in.close();
		out.close();
		s.close();
	}

	private void sendTestAnswer() {

	}

	private boolean read() throws IOException {
		String message = in.readLine();
		System.out.println("Recebi ->" + message);
		if (message.equals("STOP")) {
			return false;
		}
		out.println(message.toUpperCase());
		out.flush();
		return true;
	}

	private void launch() {
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			// for (;;) {
			System.out.println("Server aguarda ligacao no porto ->" + port);
			Socket newSock = serverSocket.accept();
			out = new PrintWriter(newSock.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(newSock.getInputStream()));
			while (read()) {
				
			}
			close(newSock);
			// }

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Erro no estabelecimento da ligação:" + e.getMessage());
			System.exit(1);
		}
	}

	public static void main(String[] args) throws IOException {
		ServerA s = new ServerA();
		s.launch();
	}

}
