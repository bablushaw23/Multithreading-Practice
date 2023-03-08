package codes;

class AppV3{
    int count=0;
    public static void main(String[] args) throws Exception {
        AppV3 v3= new AppV3();
        v3.doWork();
    }

    private void increment() throws InterruptedException{
        wait();
        count++;
    }

    private void doWork() throws InterruptedException{
        Thread t1= new Thread(new Runnable() {
            public void run(){                
                for(int i=0; i<1e4; i++)
                    try {
                        increment();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
            }
        });

        Thread t2= new Thread(new Runnable() {
            public void run(){
                for(int i=0; i<1e4; i++)
                    try {
                        increment();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
            }
        });

        t1.start();
        t2.start();
         
        t1.join();
        t2.join();
        
        // output:  20000 every time
        System.out.println("Count:"+count);
    }
}