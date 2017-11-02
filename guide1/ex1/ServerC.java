package guide1.ex1;

import java.net.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.io.*;

public class ServerC {
	Socket socket;
	BufferedReader in;
	PrintWriter out;
	int port = 8888;
	String host = "localhost";
	Future<Integer> submit;
	

	private void launch() {

		try {
			ExecutorService threadPool = Executors.newCachedThreadPool();
			ServerSocket serverSocket = new ServerSocket(port);
			serverSocket.setSoTimeout(5000);
			for (;;) {
				try {
					System.out.println("Server aguarda ligacao no porto ->" + port);
					Socket newSock = serverSocket.accept();
					submit = threadPool.submit(new HandleClient2(newSock));
					
				} catch (SocketTimeoutException e) {
					System.out.println("TIMEOUT");
					if (submit != null && submit.isDone()){
						System.out.println("GONNA STOP");
						threadPool.shutdown();
						serverSocket.close();
						break;
					}
					System.out.println("CONTINUE");
					continue;
					// accept timed out, continue listening unless stop
					// requested
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Erro no estabelecimento da ligação:" + e.getMessage());
			System.exit(1);
		}
	}

	public static void main(String[] args) throws IOException {
		ServerC s = new ServerC();
		s.launch();
	}

}

class HandleClient2 implements Callable<Integer> {
	private final Socket socket;
	PrintWriter out;
	BufferedReader in;

	public HandleClient2(Socket socket) {
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

	private int handleSocket(Socket newSock) throws IOException {
		out = new PrintWriter(newSock.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(newSock.getInputStream()));
		while (read()) {

		}
		close(newSock);
		return -1;
	}


	@Override
	public Integer call() throws Exception {
		try {
			return handleSocket(socket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -2;
	}
}
