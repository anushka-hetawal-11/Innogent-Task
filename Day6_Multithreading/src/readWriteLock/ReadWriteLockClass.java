package readWriteLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Shared{
    private int data = 0;

     ReadWriteLock lock = new ReentrantReadWriteLock();
     Lock readLock = lock.readLock();
     Lock writeLock = lock.writeLock();

    public int getData() {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " reading: " + data);
            return data;
        }finally{
          readLock.unlock();
        }
    }

    public void setData(int data) {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " writing: " + data);
            this.data = data;
        }finally {
            writeLock.unlock();
        }
    }
}

public class ReadWriteLockClass {
    public static void main(String[] args) {
        Shared s = new Shared();

        Thread writer = new Thread(()->s.setData(10),"writer Thread");

        Thread reader1 = new Thread(()->s.getData(),"reader-1 Thread");
        Thread reader2 = new Thread(()->s.getData(),"reader-2 Thread");
        Thread reader3 = new Thread(()->s.getData(),"reader-3 Thread");

        writer.start();
        reader1.start();
        reader2.start();
        reader3.start();
    }
}
