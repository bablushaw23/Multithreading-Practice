package codes;

class AppV2{
    int count=0;
    public static void main(String[] args) throws Exception {
        AppV2 v2= new AppV2();
        v2.doWork();
    }

    private void doWork() throws InterruptedException{
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
         
        t1.join();
        t2.join();
        
        // output: 16248, 17255, 20000
        System.out.println("Count:"+count);
    }
}