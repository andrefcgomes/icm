//From: https://docs.oracle.com/javase/tutorial/rmi/designing.html

package guide2.rmi.rmiComputeServer.Compute;

public interface Task<T> {
    T execute();
}

