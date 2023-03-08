package codes;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {
    ReentrantLock lock = new ReentrantLock();
    Object simpleLock= new Object();
    private int count = 0;

    public void doSomething() {
        try {
            lock.lock();
            System.out.println("Inside CS. Counts:" + count);
            count++;
            if (count < 5) {
                doSomething();
            }
        } finally {
            lock.unlock();
            lock.unlock();
        }
    }

    public void doSomethingWithSynchronized1() {
        synchronized(simpleLock) {
            // lock.lock();
            System.out.println("Inside CS1. Counts:" + count);
            count++;
            if (count < 5) {
                doSomethingWithSynchronized2();
            }
        }
    }

    public void doSomethingWithSynchronized2() {
        synchronized(simpleLock) {
            // lock.lock();
            System.out.println("Inside CS2. Counts:" + count);
            count++;
            if (count < 5) {
                doSomethingWithSynchronized1();
            }
        }
    }

    public static void main(String[] args) {
        ReentrantLockTest test= new ReentrantLockTest();
        Thread t1= new Thread(()->{
            System.out.println("Calling 1st time");
            test.doSomethingWithSynchronized1();
            System.out.println("Calling 2nd time");
            test.doSomethingWithSynchronized1();
            // test.doSomething();
            System.out.println("Calling 3rd time");
            // test.doSomething();
        });
        t1.start();
    }
}
