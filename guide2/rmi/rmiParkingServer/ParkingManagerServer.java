package guide2.rmi.rmiParkingServer;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import guide2.rmi.rmiParkingInterface.IParkingManager;

public class ParkingManagerServer {
	public static void main(String[] args) {
		try {
			String name = "Parking";
			ParkingManagerImplementation park = new ParkingManagerImplementation();
			IParkingManager stub = (IParkingManager) UnicastRemoteObject.exportObject(park, 0);
			System.out.println(stub);
			Registry registry = LocateRegistry.getRegistry("127.0.0.1");
			registry.rebind(name, stub);
			System.out.println("ParkingManagerServer bound ");
		} catch (Exception e) {
			System.err.println("ParkingManagerServer exception:");
			e.printStackTrace();
		}
	}
}
