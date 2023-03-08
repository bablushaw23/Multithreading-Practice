package codes.appv4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * It is good practice to use seperate object as locks instead of using the main object.
 * Sometimes, for sake of optimization JVM caches the object (if immutable)
 */

public class Workerv2 {
    private Random random= new Random();

    Object lock1= new Object();
    Object lock2= new Object();

    private List<Integer> list1= new ArrayList<>();
    private List<Integer> list2= new ArrayList<>();

    public void stageOne() {
        synchronized(lock1){
            try{
                Thread.sleep(1);
            }catch(InterruptedException ex){}
            list1.add(random.nextInt(100));
        }
    }

    public void stageTwo() {
        synchronized(lock2){
            try{
                Thread.sleep(1);
            }catch(InterruptedException ex){}
            list2.add(random.nextInt(100));
        }
    }    
    
    public void process() {
        for(int i=1; i<=1000; i++){
            stageOne();
            stageTwo();
        }
    }

    public static void main(String[] args) {
        Workerv2 worker= new Workerv2();
        System.out.println("Starting...");

        Thread t1= new Thread(new Runnable() {
            public void run(){
                worker.process();
            }
        });
        Thread t2= new Thread(new Runnable() {
            public void run(){
                worker.process();
            }
        });

        long start= System.currentTimeMillis();
        t1.start();
        t2.start();    

        try{
            t1.join();
            t2.join();
        }catch(InterruptedException ex){}
        long end= System.currentTimeMillis();
        System.out.println("Time taken:"+(end-start));
        System.out.println("list1 size:"+worker.list1.size()+"; list2 size:"+worker.list2.size());

    }
}
