package guide2.parkingmanager.RemotingStubs;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import guide2.parkingmanager.ParkingManagerClass.ParkingManager;

public class ServerStub implements Runnable {
	ParkingManager park = null;
	ServerSocket servSock = null;
	int srvPort = 0;
	Future<Integer> submit;

	public ServerStub(ParkingManager mng, int port) {
		// Saver ParkManager Object and service Port
		park = mng;
		srvPort = port;
	}

	public void run() {

		// Executor executor = Executors.newFixedThreadPool( 2 ) ; //Create thread pool

		// Executor executor = Executors.newCachedThreadPool();
		ExecutorService executor = Executors.newCachedThreadPool();
		try {
			servSock = new ServerSocket(srvPort);
			servSock.setSoTimeout(2000);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} // Exit in case of error

		do {

			try {
				System.out.println("awaiting new connection");
				Socket newSock = servSock.accept();
				submit = executor.submit(new executeRequest(newSock));
			} catch (SocketTimeoutException e) {
				System.out.println("timeout!");
				if (submit != null && submit.isDone()) {
					try {
						System.out.println(submit.get());
						if(submit.get() == -1) {
							System.out.println("done");
							executor.shutdown();
							break;
						}
						else{
							System.out.println("notdone");
							continue;
						}
					} catch (InterruptedException | ExecutionException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					try {
						servSock.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
		

			}
			// NOTE: Is the ParkingManagerClass thread safe?!
			// Or in other words, is it correct to call its methods concurrently? Why?
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Dado que o controlo do armazenamento dos dados é simples, poderiam haver
			// pequenos
			// assincronismos durante o acesso aos mesmos. Por outro lado, a simplicidade
			// também
			// torna a aplicação rápida, pelo que provavelmente não se notará

		} while (true);

	}

	class executeRequest implements Callable<Integer> {
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		Exception expt = null;
		Socket sock = null;

		public executeRequest(Socket sk) {
			sock = sk;
			try {
				oos = new ObjectOutputStream(sock.getOutputStream());
				ois = new ObjectInputStream(sock.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		public Integer call() {
			boolean stop = false;
			try {
				try {
					boolean result = false;

					String method = (String) ois.readObject();
					String arg = (String) ois.readObject();
					// Decode the request
					try {
						if (method.equals("enterPark")) {
							result = park.enterPark(arg);
						} else if (method.equals("leavePark"))
							result = park.leavePark(arg);
						else if (method.equals("isInPark"))
							result = park.isInPark(arg);
						else if (method.equals("stop")) {
							stop = park.stop();
							result = park.stop();
						}
					} catch (Exception e) { // Catch Any raised exception
						expt = e; // Save it to send back to client
					}

					if (expt == null) {
						oos.writeObject(new Boolean(result)); // Send back normal results
					} else {
						oos.writeObject(expt); // Send back exception caught
					}

					oos.flush();

				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace(); // Not much more to do ...
			} finally {
				try {
					if (oos != null)
						oos.close();
					if (ois != null)
						ois.close();
					if (sock != null)
						sock.close();
					if (stop)
						return -1;
					else
						return 1;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (stop)
				return -1;
			else
				return 1;
		}
	}

}
