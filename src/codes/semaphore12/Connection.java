package codes.semaphore12;

import java.util.concurrent.Semaphore;

public class Connection {
    private static Connection connection= new Connection();

/*
* Just as in OS, Semaphore is way to keep track of resource instances available to share.
* Initialized with 10, is the initial number of instance available.
* Once you take an instance, you call semaphore.acquire() and 10 will be 9 saying only 9 left
* Once you are done with the instance and release it, alongside call semaphore.release() to make 9 back to 10 indicating the same.
* When semaphore value reaches to 0 and semaphore.acquire() get called, then the thread goes in waiting until 
* semaphore goes back to 1 or more indicating resources are available.
*/    
    private Semaphore semaphore= new Semaphore(10);

    private Connection(){}

    public static Connection getConnection(){
        return connection;
    }

    private int currentConnections=0;

    public void connect() {
        try{
            semaphore.acquire();
            doConect();
        }catch(InterruptedException ex){}
        finally{
            semaphore.release();
        }
    }

    private void doConect() throws InterruptedException{
        synchronized(this){
            currentConnections++;       // depicting acquiring resource available. Note, I already decreased semaphore
            System.out.println("Current connections active:"+semaphore.availablePermits());
        }
        Thread.sleep(2000);     // utilising connection
        
        synchronized(this){
            currentConnections--;       // releasing the connection
        }
    }
}
