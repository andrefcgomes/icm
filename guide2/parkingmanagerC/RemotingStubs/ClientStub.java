package guide2.parkingmanager.RemotingStubs;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientStub implements InvocationHandler {

	Socket clientSock = null;
	ObjectOutputStream oos = null;
	ObjectInputStream ois = null;
	String srvIp = "127.1.1.0";
	int srvPort = 5000;

	public ClientStub(String Ip, int port) {
		srvIp = Ip;
		srvPort = port;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// Sends the arguments to the server using serialization and a TCP
		// socket
		// And waits for the result to return back to its caller
		// …
		oos.writeUTF(method.getName());
		oos.writeObject(method.getParameterTypes());
		oos.writeObject(args);
		oos.flush(); //Send request to server
		Object result = ois.readObject(); //Return reply
		deconnectServer();
		return result;
	}

	public static Object getProxy(Class servInterface, String host, int port) {
		Object proxy = Proxy.newProxyInstance(servInterface.getClassLoader(), new Class[] { servInterface },
				new ClientStub(host, port));
		return proxy;
	}

	public void Destroy() {
		deconnectServer();
	}

	// Starts a connection
	private boolean connectServer() {
		boolean result = false;
		try {
			clientSock = new Socket(srvIp, srvPort);
			oos = new ObjectOutputStream(clientSock.getOutputStream());
			ois = new ObjectInputStream(clientSock.getInputStream());
			result = true;

		} catch (UnknownHostException e) {
			e.printStackTrace(); // No more to do here
		} catch (IOException e) {
			e.printStackTrace(); // No more to do here
		}
		// clientSock is the connection to the server Stub
		// oos, ois are de interface Objects' streams
		return result;
	}

	public void deconnectServer() {
		try {
			if (ois != null)
				ois.close();
			ois = null;
			if (oos != null)
				oos.close();
			oos = null;
			if (clientSock != null)
				clientSock.close();
			clientSock = null;
		} catch (IOException e) {
			e.printStackTrace(); // No more to do here
		}
	}

	// Interface ParkingManagerInterface Methods

	@Override
	public boolean enterPark(String carId) throws Exception {
		Object resultObj = null;
		boolean result = false;

		if (connectServer()) {
			try {
				oos.writeObject("enterPark");
				oos.writeObject(carId);
				oos.flush(); // Send request to server
				resultObj = ois.readObject(); // Get result
				deconnectServer();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// Now analyse the result

			if (resultObj instanceof Exception) {
				throw (Exception) resultObj;
			} else
				result = (Boolean) resultObj;

		}
		return result;
	}

	@Override
	public boolean leavePark(String carId) throws Exception {
		Object resultObj = null;
		boolean result = false;

		if (connectServer()) {
			try {
				oos.writeObject("leavePark");
				oos.writeObject(carId);
				oos.flush(); // Send request to server
				resultObj = ois.readObject(); // Get result
				deconnectServer();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// Now analyse the result

			if (resultObj instanceof Exception) {
				throw (Exception) resultObj;
			} else
				result = (Boolean) resultObj;

		}
		return result;
	}

	@Override
	public boolean isInPark(String carId) throws Exception {
		Object resultObj = null;
		boolean result = false;

		if (connectServer()) {
			try {
				oos.writeObject("isInPark");
				oos.writeObject(carId);
				oos.flush(); // Send request to server
				resultObj = ois.readObject(); // Get result
				deconnectServer();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// Now analyse the result

			if (resultObj instanceof Exception) {
				throw (Exception) resultObj;
			} else
				result = (Boolean) resultObj;

		}
		return result;
	}

	@Override
	public boolean stop() throws Exception {
		Object resultObj = null;
		boolean result = false;

		if (connectServer()) {
			try {
				oos.writeObject("stop");
				oos.writeObject("stop");
				oos.flush(); // Send request to server
				resultObj = ois.readObject(); // Get result
				deconnectServer();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// Now analyse the result

			if (resultObj instanceof Exception) {
				throw (Exception) resultObj;
			} else
				result = (Boolean) resultObj;

		}
		return result;
	}
}
