package guide2.parkingmanager.RemoteParkingServerAndClient;

import java.io.IOException;

import guide2.parkingmanager.ParkingManagerClass.ParkingManager;
import guide2.parkingmanager.RemotingStubs.ServerStub;

public class ParkServer {

	public static void main(String[] args) throws InterruptedException, IOException {
	
		ParkingManager  park = new ParkingManager();
		
		ServerStub   srvStub = new ServerStub(park, 5000);
		
		Thread  threadSrv = new Thread(srvStub); //Create server thread
		threadSrv.start();                       //Start the server
		
        System.out.println("Park server started!");

	}

}
