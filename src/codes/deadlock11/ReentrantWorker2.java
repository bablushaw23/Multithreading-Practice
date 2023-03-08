package codes.deadlock11;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantWorker2 {
    private Account ac1= new Account();
    private Account ac2= new Account();

    Lock l1= new ReentrantLock();

    public void firstThread() throws InterruptedException {
        Random r1= new Random();
        try{  
            l1.lock();            
            for(int i=0; i<1e5; i++){
                Account.transfer(ac1, ac2, r1.nextInt(100));
            }
        }finally{
            l1.unlock();
        }
    }

    public void secondThread() throws InterruptedException{
        Random r1= new Random();
        try{  
            l1.lock();
            for(int i=0; i<1e5; i++){
                Account.transfer(ac2, ac1, r1.nextInt(100));
            }
        }finally{
            l1.unlock();
        }
    }

    public void finished(){
        System.out.println("Account 1 balance:"+ac1.getBalance());
        System.out.println("Account 2 balance:"+ac2.getBalance());
        System.out.println("Total balance:"+(ac1.getBalance()+ac2.getBalance()));
    }
}
