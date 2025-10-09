package assessment2.day5_Threads.evenOdd.src.syncApproach;

import java.util.Scanner;

class Number{
    private int n;
    private int start = 1;

    public Number(int n) {
        this.n = n;
    }

    public synchronized void even(){
        while(start<=n){
            if(start%2 != 0){
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                System.out.println(Thread.currentThread().getName() + " " + start + " ");
                start++;
                notify();
            }
        }
    }

    public synchronized void odd(){
        while(start<=n){
            if(start%2 == 0){
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                System.out.println(Thread.currentThread().getName() + " " +start + " ");
                start++;
                notify();
            }
        }
    }
}

public class Sync {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter n: ");
        int n = sc.nextInt();

        Number num = new Number(n);

        Thread even = new Thread(() -> num.even(),"Even");
        Thread odd = new Thread(() -> num.odd(), "Odd");

        even.start();
        odd.start();

    }
}