package guide2.parkingmanager.RemotingStubs;

public interface ParkingManagerInterface {
	
	    public boolean enterPark(String carId) throws Exception;
	   
		public boolean leavePark(String carId) throws Exception;
		
		public boolean isInPark(String carId) throws Exception;
		
		public boolean stop() throws Exception;
}
