package codes.deadlock11;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReeWorkerDeadlock3 {
    private Account ac1= new Account();
    private Account ac2= new Account();

/*
 * Two ReentrantLocks are taken to generate deadlock in a situation where 1 thread have 1 lock and asking for another 1 hold by 2nd thread
 * Note, l1.lock() before l2.lock() at firstThread() but opposite in secondThread(). There is a reason.
 *      If both are in same sequence, then 1st thread if got l1, and just after that 2nd thread hits. 
 *      It will ask for l1(as it would be 1st in the sequence). In absense of it, it would wait till thread1 finishes.
 *  To arise deadlock, let thread1 get l1 and thread2 get l2 and then wait for each other.
 */

    Lock l1= new ReentrantLock();
    Lock l2= new ReentrantLock();

    public void firstThread() throws InterruptedException {
        Random r1= new Random();
        try{  
            l2.lock();
            l1.lock();            
            for(int i=0; i<1e5; i++){
                Account.transfer(ac1, ac2, r1.nextInt(100));
            }
        }finally{
            l1.unlock();
            l2.unlock();
        }
    }

    public void secondThread() throws InterruptedException{
        Random r1= new Random();
        try{  
            l1.lock();
            l2.lock();      
            for(int i=0; i<1e5; i++){
                Account.transfer(ac2, ac1, r1.nextInt(100));
            }
        }finally{
            l1.unlock();
            l2.unlock();
        }
    }

    public void finished(){
        System.out.println("Account 1 balance:"+ac1.getBalance());
        System.out.println("Account 2 balance:"+ac2.getBalance());
        System.out.println("Total balance:"+(ac1.getBalance()+ac2.getBalance()));
    }
}

