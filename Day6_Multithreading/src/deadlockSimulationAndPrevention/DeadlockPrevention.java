package deadlockSimulationAndPrevention;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class SharedResource1 {
    private int sharedResource1;
    private int sharedResource2;

    Lock lock1 = new ReentrantLock();
    Lock lock2 = new ReentrantLock();

    public SharedResource1(int sharedResource1, int sharedResource2) {
        this.sharedResource1 = sharedResource1;
        this.sharedResource2 = sharedResource2;
    }

    void operation1() {
        try {
            boolean isLockAcquired = lock1.tryLock(1, TimeUnit.SECONDS);
            if (isLockAcquired) {
                System.out.println("Thread A acquired Lock 1, waiting for Lock 2...");

                Thread.sleep(50);
                if (lock2.tryLock(1, TimeUnit.SECONDS)) {
                    System.out.println("Thread A acquired Lock 2");
                    System.out.println("Thread A executing: " + (sharedResource1 - sharedResource2));
                    lock2.unlock();
                } else {
                    System.out.println("Thread A couldn't acquire Lock 2, avoiding deadlock");
                }
                lock1.unlock();
            } else {
                System.out.println("Thread A couldn't acquire Lock 1");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void operation2() {
        try {
            if (lock1.tryLock(1, TimeUnit.SECONDS)) {
                System.out.println("Thread B acquired Lock 1, waiting for Lock 2...");

                Thread.sleep(50);
                if (lock2.tryLock(1, TimeUnit.SECONDS)) {
                    System.out.println("Thread B acquired Lock 2");
                    System.out.println("Thread B executing: " + (sharedResource2 - sharedResource1));
                    lock2.unlock();
                } else {
                    System.out.println("Thread B couldn't acquire Lock 2, avoiding deadlock");
                }
                lock1.unlock();
            } else {
                System.out.println("Thread B couldn't acquire Lock 1");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

public class DeadlockPrevention {
    public static void main(String[] args) {
        SharedResource1 shared = new SharedResource1(3, 5);

        Thread A = new Thread(shared::operation1);
        Thread B = new Thread(shared::operation2);

        A.start();
        B.start();
    }
}


//preventions --
// keeping same locking order for both threads
// using trylock() method we are defining time,
// if not acquire lock within this time automatically
// stop waiting loop ,thus prevents deadlock