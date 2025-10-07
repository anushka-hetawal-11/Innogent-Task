package lockConditionApproach;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class Numbers{
    private int n;
    private AtomicInteger count = new AtomicInteger(1);
    private ReentrantLock lock = new ReentrantLock();
    private Condition turn = lock.newCondition();

    public Numbers(int n) {
        this.n = n;
    }

    public void even(){
        while(count.get()<=n){
            lock.lock();
            try {
                if (count.get() % 2 != 0) { //if odd, even thread waiting
                        turn.await();
                } else {
                    System.out.println(Thread.currentThread().getName() + " " +count.getAndIncrement());
                }
                turn.signal();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }finally {
                lock.unlock();
            }
        }
    }

    public void odd(){
        while(count.get()<=n){
            lock.lock();
            try {
                if (count.get() % 2 == 0) { //if even ,odd thread waiting queue m
                        turn.await();
                } else {
                    System.out.println(Thread.currentThread().getName() + " " +count.getAndIncrement());
                }
                turn.signal();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }finally {
                lock.unlock();
            }
        }
    }
}

public class LockCondition {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter n: ");
        int n = sc.nextInt();

        Numbers num = new Numbers(n);

        Thread even = new Thread(() -> num.even(), "EVEN");
        Thread odd = new Thread(() -> num.odd(), "ODD");

        even.start();
        odd.start();

    }
}

//in classic synchronization approach we are allowing only one thread to enter method at a time
//and rest all threads have to wait , yielding in performance degradation

