package guide2.rmi.rmiParkingServer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import guide2.rmi.rmiParkingInterface.IParkingManager;

public class ParkingManagerImplementation implements IParkingManager {
	ConcurrentHashMap<String, Date> park = null;

	public ParkingManagerImplementation() {
		park = new ConcurrentHashMap<String, Date>();
	}

	@Override
	public boolean enterPark(String carId) throws RemoteException {
		if ("div".equals(carId)) {
			@SuppressWarnings("unused")
			int X = 5 / 0;
		}
		return park.put(carId, new Date()) == null;
	}

	@Override
	public boolean leavePark(String carId) throws RemoteException {
		return park.remove(carId) != null;
	}

	@Override
	public boolean isInPark(String carId) throws RemoteException {
		if ("slow".equals(carId)) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
			} // Freezes the thread 10 seconds
		}
		return park.containsKey(carId);
	}

	@Override
	public boolean stop() throws RemoteException {
		FileOutputStream fout;
		try {
			fout = new FileOutputStream("parkedData.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(park);
			fout.close();
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;

	}

}
