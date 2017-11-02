package guide2.parkingmanager.RemotingStubs;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientStub implements ParkingManagerInterface{

	Socket clientSock = null;
	ObjectOutputStream oos = null;
	ObjectInputStream  ois =null;
	String srvIp="127.1.1.0";
	int srvPort = 5000;

	public ClientStub ( String Ip, int port){
		srvIp = Ip; srvPort = port;
	}

	public void Destroy(){
		deconnectServer();
	}
	//Starts a connection
	private boolean connectServer(){
		boolean result = false;
		try {
			clientSock = new Socket(srvIp, srvPort);
			oos = new ObjectOutputStream(clientSock.getOutputStream());
			ois = new ObjectInputStream(clientSock.getInputStream());
			result = true;

		} catch (UnknownHostException e) { e.printStackTrace(); //No more to do here
		} catch (IOException e) {          e.printStackTrace(); //No more to do here
		}
		//clientSock is the connection to the server Stub
		// oos, ois are de interface Objects' streams
		return result;
	}
	public void deconnectServer(){
		try {
			if(ois != null) ois.close(); ois = null; 
			if(oos != null) oos.close(); oos = null;
			if(clientSock != null) clientSock.close(); clientSock = null;
		} catch (IOException e) {
			e.printStackTrace();  //No more to do here
		}
	}

	//Interface ParkingManagerInterface Methods

	@Override
	public boolean enterPark(String carId) throws Exception{
		Object resultObj = null;
		boolean result = false;

		if(connectServer() ) {
			try {
				oos.writeObject("enterPark");
				oos.writeObject(carId);
				oos.flush(); //Send request to server
				resultObj =  ois.readObject(); //Get result
				deconnectServer();
			} catch (Exception e) { e.printStackTrace();}
			//Now analyse the result

			if(resultObj instanceof  Exception) {
				throw (Exception)resultObj;
			} else result = (Boolean)resultObj;

		}
		return result;
	}

	@Override
	public boolean leavePark(String carId) throws Exception {
		Object resultObj = null;
		boolean result = false;

		if(connectServer() ) {
			try {
				oos.writeObject("leavePark");
				oos.writeObject(carId);
				oos.flush(); //Send request to server
				resultObj =  ois.readObject(); //Get result
				deconnectServer();
			} catch (Exception e) { e.printStackTrace();}
			//Now analyse the result

			if(resultObj instanceof  Exception) {
				throw (Exception)resultObj;
			} else result = (Boolean)resultObj;

		}
		return result;
	}

	@Override
	public boolean isInPark(String carId) throws Exception {
		Object resultObj = null;
		boolean result = false;

		if(connectServer() ) {
			try {
				oos.writeObject("isInPark");
				oos.writeObject(carId);
				oos.flush(); //Send request to server
				resultObj =  ois.readObject(); //Get result
				deconnectServer();
			} catch (Exception e) { e.printStackTrace();}
			//Now analyse the result

			if(resultObj instanceof  Exception) {
				throw (Exception)resultObj;
			} else result = (Boolean)resultObj;

		}
		return result;
	}
	
	@Override
	public boolean stop() throws Exception {
		Object resultObj = null;
		boolean result = false;

		if(connectServer() ) {
			try {
				oos.writeObject("stop");
				oos.writeObject("stop");
				oos.flush(); //Send request to server
				resultObj =  ois.readObject(); //Get result
				deconnectServer();
			} catch (Exception e) { e.printStackTrace();}
			//Now analyse the result

			if(resultObj instanceof  Exception) {
				throw (Exception)resultObj;
			} else result = (Boolean)resultObj;

		}
		return result;
	}
}

