package codes.deadlock11;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockSolution5 {
    private Account ac1= new Account();
    private Account ac2= new Account();
/*
 * This solution involves a method handling aquiring locks.
 * This time it will be checked before aquiring the lock if can be aquired.
 * There is a method in Reentrant locks, tryLock(). When lock can be taken, it takes it and return true else false.
 * Note this time, locks will be tried to acquire non-sequentially. 1stThread:(l1->l2), 2ndThread:(l2->l1)
 */
    Lock l1= new ReentrantLock();
    Lock l2= new ReentrantLock();

    private void acquireLock(Lock l1, Lock l2){
        while(true){
            boolean gotL1=false, gotL2=false;
            try{
                gotL1=l1.tryLock();
                gotL2=l2.tryLock();            
            }
            finally{
                if(gotL1 && gotL2)
                    return;         // as got both the locks;
                if(gotL1)
                    l1.unlock();    // not got L2 so, unlock L1 and wait for next time when both locks acquire
                if(gotL2)
                    l2.unlock();
            }            
        }
    }

    public void firstThread() throws InterruptedException {
        Random r1= new Random();
        try{  
            acquireLock(l1, l2);
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
            acquireLock(l2, l1);
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