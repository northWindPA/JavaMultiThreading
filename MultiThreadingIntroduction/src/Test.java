public class Test {
    public static void main(String[] args) {
//        main is a thread
//        MyThread myThread = new MyThread();
//        MyThread myThread1 = new MyThread();
//        myThread.start();
//        myThread1.start();
//        threads work asynchronously

        Thread thread = new Thread(new Runner());
        Thread thread1 = new Thread(new Runner());
        thread.start();
        thread1.start();
        System.out.println("Main is here!");
    }
}
class Runner implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println("Thread is here! " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


class MyThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println("Thread " + getName() + " is here! " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}