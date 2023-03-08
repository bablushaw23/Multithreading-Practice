package codes.appv4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * In this I see, how unsynchronized data-structure impacts with multi-threading.
 * 
 * I see here, when synchronization is applied over stage1() & stage() the time taken to complete is almost double if removed.
 * And there is a big reason behind. 
 * When we declare synchronized methods then all those methods uses same and only lock comes with object Worker. And so
 * when 1 method is locked all other methods are locked as well. And hence, when 1 thread enters into CS(critical section) all 
 * other threads have to wait irrespective of whether they use same method or different(inside Worker object).
 * 
 * Solution is, using synchronized blocks instead. There you will create as many new locks.
 * So, create 2 locks, 1 for list1 and another for list2.
 * 
 * It is good practice to use seperate object as locks instead of using the main object. See v2
 */

public class Worker {
    private Random random= new Random();

    private List<Integer> list1= new ArrayList<>();
    private List<Integer> list2= new ArrayList<>();

    public void stageOne() {
        try{
            Thread.sleep(1);
        }catch(InterruptedException ex){}
        list1.add(random.nextInt(100));
    }

    public void stageTwo() {
        try{
            Thread.sleep(1);
        }catch(InterruptedException ex){}
        list2.add(random.nextInt(100));
    }    
    
    // when using synchronization in (stageOne & stageTwo) then: Time taken:6984 ~7s, list1 size:2000; list2 size:2000
    // when not using synchronization then: Time taken:3270 ~ 3sec, list1 size:1996; list2 size:1998
    public void process() {
        for(int i=1; i<=1000; i++){
            stageOne();
            stageTwo();
        }
    }

    public static void main(String[] args) {
        Worker worker= new Worker();
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
