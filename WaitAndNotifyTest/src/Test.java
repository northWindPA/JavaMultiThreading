import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        WaitAndNotify wn = new WaitAndNotify();
        Thread thread1 = new Thread(wn::produce);
        Thread thread2 = new Thread(wn::consume);
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class WaitAndNotify {
    public void produce() {
        synchronized (this) {
            System.out.println("Producer thread started...");
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Producer thread resumed...");
        }
    }
    public void consume() {
        Scanner scan = new Scanner(System.in);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (this) {
            System.out.println("Waiting for return key pressed");
            scan.nextLine();
            this.notify();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}