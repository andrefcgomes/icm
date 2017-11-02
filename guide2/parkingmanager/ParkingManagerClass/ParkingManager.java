package guide2.parkingmanager.ParkingManagerClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;

public class ParkingManager {

	HashMap<String, Date> park = null;

	@SuppressWarnings({ "unchecked", "resource" })
	public ParkingManager() throws IOException {
		File f = new File("parkedData.dat");
		if (f.exists() && !f.isDirectory()) {
			// do something
			try {
				FileInputStream fin = new FileInputStream("parkedData.dat");
				ObjectInputStream oin = new ObjectInputStream(fin);
				park = (HashMap<String, Date>) oin.readObject();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			park = new HashMap<String, Date>();
		}
	}

	public boolean enterPark(String carId) throws Exception {

		if ("exception".equals(carId)) {
			throw new Exception("Hello my exception test!");
		} // Lanca excepcao quando "exception"
		if ("div".equals(carId)) {
			int X = 5 / 0;
		} // Lanca excepcao quando "div"

		return park.put(carId, new Date()) == null;
	}

	public boolean leavePark(String carId) throws Exception {
		return park.remove(carId) != null;
	}

	public boolean isInPark(String carId) throws Exception {

		if ("slow".equals(carId)) { // Server para durante 10 segundos "slow", sem afectar outros clientes
			Thread.sleep(10000); // Freezes the thread 10 seconds
		}

		return park.containsKey(carId);
	}

	public boolean stop() throws Exception {
		FileOutputStream fout = new FileOutputStream("parkedData.dat");
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		oos.writeObject(park);
		fout.close();
		oos.close();
		return true;

	}

}
