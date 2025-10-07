import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class Data {
    private int n;
    private ArrayList<Integer> buffer = new ArrayList<>();
    private int bufferSize = 5;
    private int producedCount = 0;
    private ReentrantLock lock = new ReentrantLock();
    private Condition isFull = lock.newCondition();
    private Condition isEmpty = lock.newCondition();

    public Data(int n) {
        this.n = n;
    }

    public void produce() {
        while (true) {
            lock.lock();
            try {
                if (producedCount >= n) break;

                while (buffer.size() == bufferSize) {
                    isFull.await(); // wait if buffer is already full
                }

                int item = producedCount + 1; // produce next item
                buffer.add(item);
                producedCount++;
                System.out.println(Thread.currentThread().getName() + " produced: " + item);

                isEmpty.signal(); // notify consumer
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public void consume() {
        int consumedCount = 0;
        while (true) {
            lock.lock();
            try {
                if (consumedCount >= n) break;

                while (buffer.isEmpty()) {
                    isEmpty.await();
                }

                int item = buffer.remove(0);
                consumedCount++;
                System.out.println(Thread.currentThread().getName() + " consumed: " + item);

                isFull.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}



public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter n: ");
        int n = sc.nextInt();

        Data data = new Data(n);

        Thread producer = new Thread(() -> data.produce(),"Producer Thread");
        Thread consumer = new Thread(() -> data.consume(),"Consumer Thread");

        producer.start();
        consumer.start();
    }
}