package codes.waitandnotify;

class MyThread extends Thread {
    private final Object lock;

    public MyThread(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized(lock) {
            System.out.println("Thread " + Thread.currentThread().getId() + " is waiting");
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread " + Thread.currentThread().getId() + " is awake");
        }
    }
}

public class SmallTest {
    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();

        MyThread thread1 = new MyThread(lock);
        MyThread thread2 = new MyThread(lock);

        thread1.start();
        thread2.start();

        Thread.sleep(1000);

        synchronized(lock) {
            lock.notify();          // will awake only 1st thread
            // lock.notifyAll();    // will awake both threads
        }
    }
}
