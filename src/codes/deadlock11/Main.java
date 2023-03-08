package codes.deadlock11;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // final Worker worker=new Worker();
        // final ReentrantWorker worker= new ReentrantWorker();
        // final ReeWorkerDeadlock worker= new ReeWorkerDeadlock();    // This line generates deadlock
        // final SynchronizedBlockDeadlock worker= new SynchronizedBlockDeadlock();
        // final DeadlockSolution4 worker= new DeadlockSolution4();
        final DeadlockSolution5 worker= new DeadlockSolution5();
        Thread t1=new Thread(()-> {            
                try {
                    worker.firstThread();
                } catch (InterruptedException e) {            
                    e.printStackTrace();
                }            
        });

        Thread t2= new Thread(()->{
            try{
                worker.secondThread();
            }catch(InterruptedException ex){
                ex.printStackTrace();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        worker.finished();
    }
}
