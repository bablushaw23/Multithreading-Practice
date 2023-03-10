package codes.future_callable_13;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) {
        ExecutorService exec= Executors.newCachedThreadPool();

        Callable<Integer> callable= new Callable<Integer>() {
    
            @Override
            public Integer call() throws InterruptedException {
                Random r= new Random();
                int duration=r.nextInt(4000);
                System.out.println("Sleep for: "+duration);
                Thread.sleep(duration);
                System.out.println("Woke up");
                return duration;
            }
            
        };        

        // get what is returned in Future.
        Future<Integer> returned= exec.submit(callable);

        try {
            Integer got= returned.get();  // would wait until get the value
            System.out.println(got);
            // catch if the thread throws exception
        } catch (InterruptedException | ExecutionException e) {        
            e.printStackTrace();
        }
        exec.shutdown();
    }
}
