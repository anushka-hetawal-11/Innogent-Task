package deadlockSimulationAndPrevention;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

class SharedResource{
    private int sharedResource1;
    private int sharedResource2;

    Lock lock1 = new ReentrantLock();
    Lock lock2 = new ReentrantLock();


    public SharedResource(int sharedResource1, int sharedResource2) {
        this.sharedResource1 = sharedResource1;
        this.sharedResource2 = sharedResource2;
    }

     void operation1(){
        try {
            lock1.lock();
            System.out.println("Lock 1 acquired on shared resource1, waiting for lock 2...");
            sleep(50);

            lock2.lock();
            System.out.println("Lock 2 acquired on shared resource 2");

            System.out.println("Thread A executing ");
            System.out.println(sharedResource1 - sharedResource2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock1.unlock();
            lock2.unlock();
        }
    }

     void operation2(){
        try {
            lock2.lock();
            System.out.println("Lock 2 acquired on shared resource2, waiting for lock 1...");
            sleep(50);

            lock1.lock();
            System.out.println("Lock 1 acquired on shared resource 1");

            System.out.println("Thread B executing ");
            System.out.println(sharedResource2 - sharedResource1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock2.unlock();
            lock1.unlock();
        }
    }
}

public class DeadLockSimulate {
    public static void main(String[] args) {
        SharedResource shared = new SharedResource(3,5);

      Thread A = new Thread(()->shared.operation1());
      Thread B = new Thread(()->shared.operation2());

      A.start();
      B.start();
    }
}