package codes.deadlock11;

import java.util.Random;

public class SynchronizedBlockDeadlock {
    private Account ac1= new Account();
    private Account ac2= new Account();

/*
 * Two ReentrantLocks are taken to generate deadlock in a situation where 1 thread have 1 lock and asking for another 1 hold by 2nd thread
 * Note, l1.lock() before l2.lock() at firstThread() but opposite in secondThread(). There is a reason.
 *      If both are in same sequence, then 1st thread if got l1, and just after that 2nd thread hits. 
 *      It will ask for l1(as it would be 1st in the sequence). In absense of it, it would wait till thread1 finishes.
 *  To arise deadlock, let thread1 get l1 and thread2 get l2 and then wait for each other.
 */

 public void firstThread() throws InterruptedException {    
    Random r1= new Random();
     synchronized(ac1){  
        for(int i=0; i<1e4; i++){
            Account.transfer(ac2, ac1, r1.nextInt(100));
        }
        secondThread();
     }
 }

 public void secondThread() throws InterruptedException{
    Random r1= new Random();
     synchronized(ac2){
        for(int i=0; i<1e5; i++){
            Account.transfer(ac1, ac2, r1.nextInt(100));
        }
        firstThread();
     }
 }

 public void finished(){
     System.out.println("Account 1 balance:"+ac1.getBalance());
     System.out.println("Account 2 balance:"+ac2.getBalance());
     System.out.println("Total balance:"+(ac1.getBalance()+ac2.getBalance()));
 }
}

