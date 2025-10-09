package semaphore;

import java.util.concurrent.Semaphore;

class ParkingLot{
    private Semaphore semaphore;

    public ParkingLot(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    void park(){
        try {
            semaphore.acquire();
            System.out.println(Thread.currentThread().getName() + " has Parked");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            depart();
        }
    }

    void depart(){
        System.out.println(Thread.currentThread().getName() + " is leaving");
        semaphore.release();
        System.out.println("Current available slots: " + semaphore.availablePermits());
    }

}

public class SemaphoreClass {
    public static void main(String[] args) {

    Semaphore s = new Semaphore(2);

    ParkingLot parkingLot = new ParkingLot(s);

    Thread car1 = new Thread(() -> parkingLot.park(),"car1");
    Thread car2 = new Thread(() -> parkingLot.park(),"car2");
    Thread car3 = new Thread(() -> parkingLot.park(),"car3");
    Thread car4 = new Thread(() -> parkingLot.park(),"car4");


   car1.start();
   car2.start();
   car3.start();
   car4.start();
  }
}
