package CountDownLatch;

import java.util.concurrent.CountDownLatch;

class LoadingConfig{
   private CountDownLatch countDownLatch;
   private int userId;
   private String pass;

    public LoadingConfig(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }


    void loadCredentials(int userId,String pass){
        try{
            System.out.println(Thread.currentThread().getName()+" trying to load configurations");
            Thread.sleep(1000);
            this.userId = userId;
            this.pass = pass;
            System.out.println(Thread.currentThread().getName()+" Configurations loaded");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally{
            countDownLatch.countDown();
        }
    }
}

public class CountDownLatchExample {
    public static void main(String[] args) {
        CountDownLatch count = new CountDownLatch(3);
        LoadingConfig load = new LoadingConfig(count);


        Thread t1 = new Thread(()->load.loadCredentials(1,"abc"),"Thread-1");
        Thread t2 = new Thread(()->load.loadCredentials(2,"xyz"),"Thread-2");
        Thread t3 = new Thread(()->load.loadCredentials(3,"pqr"),"Thread-3");

        t1.start();
        t2.start();
        t3.start();

        System.out.println(Thread.currentThread().getName()+" thread waiting");
        try {
            count.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("workers have completed loading, Main thread can now proceed");

    }
}
