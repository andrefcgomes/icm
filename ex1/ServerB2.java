package ex1;

import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;

public class ServerB2 {
	Socket socket;
	BufferedReader in;
	PrintWriter out;
	int port = 8888;
	String host = "localhost";

	private void launch() {

		try {
			ExecutorService threadPool = Executors.newCachedThreadPool();
			ServerSocket serverSocket = new ServerSocket(port);
			for (;;) {
				System.out.println("Server aguarda ligacao no porto ->" + port);
				Socket newSock = serverSocket.accept();
				threadPool.execute(new HandleClient(newSock));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Erro no estabelecimento da ligação:" + e.getMessage());
			System.exit(1);
		}
	}

	public static void main(String[] args) throws IOException {
		ServerB2 s = new ServerB2();
		s.launch();
	}

}

class HandleClient implements Runnable {
	private final Socket socket;
	PrintWriter out;
	BufferedReader in;

	public HandleClient(Socket socket) {
		this.socket = socket;
	}

	private void close(Socket s) throws IOException {
		in.close();
		out.close();
		s.close();
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

	private void handleSocket(Socket newSock) throws IOException {
		out = new PrintWriter(newSock.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(newSock.getInputStream()));
		while (read()) {

		}
		close(newSock);
	}

	@Override
	public void run() {

		try {
			handleSocket(socket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
