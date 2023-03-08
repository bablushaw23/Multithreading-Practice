package codes;
public class Appv1 {
    int count=0;
    public static void main(String[] args) throws Exception {
        Appv1 app= new Appv1();
        app.doWorkv1();
    }

    private void doWorkv1() throws InterruptedException{
        Thread t1= new Thread(new Runnable() {
            public void run(){
                for(int i=0; i<1e4; i++)   count++;
            }
        });

        Thread t2= new Thread(new Runnable() {
            public void run(){
                for(int i=0; i<1e4; i++)   count++;
            }
        });

        t1.start();
        t2.start();
// v1
        // System.out.println("Value of count:"+count);        

        // Out: Value of count:0 
        // bcz, main thread kept running after starting t1 and t2 and didnt wait for them to finish
// v2        
        // Now let the main thread sleep for 2sec so t1 and t2 would finish
        
        Thread.sleep(2000);
        System.out.println("Value of count:"+count);

        // Output some time: 13954, 14234, 15687 and sometime 20000.
        // This is happening because the shared variable count is not shared in mutual exclusive fashion.
        // So when count++ i.e. count=count+1 is executing in 1 thread, another thread update it to some new value.
        // Actually count++ is 3 statement: 1. READ count 2. ADD Count 1  3. WRITE Count 
        // After reading and before writing, it is updated by another thread.
        

    }
}
