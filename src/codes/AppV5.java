package codes;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Process implements Runnable{
    int id;
    public Process(int id){
        this.id=id;
    }
    @Override
    public void run() {
        System.out.println("Starting process. Id: "+id);
        try{
            Thread.sleep(3000);
        }catch(InterruptedException ex){}
        System.out.println("Completed process. Id: "+id);
    }

}

class App{
    public static void main(String[] args) throws InterruptedException {
        ExecutorService eService= Executors.newFixedThreadPool(3);
        
        for(int i=1; i<=10; i++)
            eService.submit(new Process(i));
        
        System.out.println("All tasks submitted");

        long start= System.currentTimeMillis();
        // eService.shutdown();     // accepting no more threads, but whatever in queue will be finished
        // eService.shutdownNow();     // will immediate shutdown dumping all threads in queue, only running threads will keep running.
        eService.awaitTermination(2, TimeUnit.SECONDS);  // keeps the main thread block until all threads are complete or timeout
        
        long end = System.currentTimeMillis();
        System.out.println("Time taken to shutdown: "+(end-start));
        // providing shutdown or shutdownNow is important else executorService will keep waiting for new task
        eService.shutdown();

    }
}