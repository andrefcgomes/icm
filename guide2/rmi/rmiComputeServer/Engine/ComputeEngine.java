//From: https://docs.oracle.com/javase/tutorial/rmi/designing.html

package guide2.rmi.rmiComputeServer.Engine;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import guide2.rmi.rmiComputeServer.Compute.Compute;
import guide2.rmi.rmiComputeServer.Compute.Task;

public class ComputeEngine implements Compute {

	public ComputeEngine() {
		super();
	}

	public <T> T executeTask(Task<T> tsk) { //Compute interface's implementation method
		return tsk.execute();
	}

	public static void main(String[] args) {
//		if (System.getSecurityManager() == null) {
//			System.setSecurityManager(new SecurityManager());
//		}
		try {
			String name = "Compute";
			Compute engine = new ComputeEngine();
			Compute stub =
					(Compute) UnicastRemoteObject.exportObject(engine, 0);
			Registry registry = LocateRegistry.getRegistry();
			registry.rebind(name, stub);
			System.out.println("ComputeEngine bound");
		} catch (Exception e) {
			System.err.println("ComputeEngine exception:");
			e.printStackTrace();
		}
	}
}

/* To launch the server, please do not forget to launch the "rmiregistry" using
 * the project's BIN directory as its current directory. Otherwise it will not
 * have access to the java classes used in the stubs' interfaces.
 */
