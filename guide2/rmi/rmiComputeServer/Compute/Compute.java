
//From: https://docs.oracle.com/javase/tutorial/rmi/designing.html

package guide2.rmi.rmiComputeServer.Compute;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Compute extends Remote {
	<T> T executeTask(Task<T> tsk) throws RemoteException;
}
