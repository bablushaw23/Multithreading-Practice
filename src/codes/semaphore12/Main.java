package codes.semaphore12;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec= Executors.newCachedThreadPool();
    
        Runnable runnable= new Runnable(){
            public void run(){
                Connection.getConnection().connect();
            }
        };
    
        for(int i=0; i<200; i++){   // get 200 connections, u will experiance 10 connections then 2sec pause, agn 10 connections
            exec.submit(runnable);
        }
        exec.shutdown();
        exec.awaitTermination(1, TimeUnit.DAYS);   
    }
}