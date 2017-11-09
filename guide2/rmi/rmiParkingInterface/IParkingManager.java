package guide2.rmi.rmiParkingInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IParkingManager extends Remote {
	public boolean enterPark(String carId) throws RemoteException;

	public boolean leavePark(String carId) throws RemoteException;

	public boolean isInPark(String carId) throws RemoteException;
	
	public boolean stop() throws RemoteException;
	
}
