import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test {
    private volatile int counter;

    public static void main(String[] args) throws InterruptedException{
//        Test test = new Test();
//        test.doWork();

        new Worker().main();
    }

//    old syntax
//    public synchronized void increment() {
//        counter++;
//    }

//    new syntax
    public void increment() {
        synchronized (this) {
            counter++;
        }
    }

    public void doWork() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                increment();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();


        System.out.println(counter);
    }
}

class Worker {
    Random randomNumber = new Random();

    final Object lock1 = new Object();
    final Object lock2 = new Object();

    private final List<Integer> list1 = new ArrayList<>();
    private final List<Integer> list2 = new ArrayList<>();

    public void addToList1() {
        synchronized (lock1) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list1.add(randomNumber.nextInt(100));
        }
    }

    public void addToList2() {
        synchronized (lock2) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list2.add(randomNumber.nextInt(100));
        }
    }

    public void work() {
        for (int i = 0; i < 1000; i++) {
            addToList1();
            addToList2();
        }
    }

    public void main() {
        long before = System.currentTimeMillis();
//        work();
        Thread thread1 = new Thread(this::work);

        Thread thread2 = new Thread(this::work);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long after = System.currentTimeMillis();
        System.out.println("WorkTime " + (after - before) + " ms");

        System.out.println("List1: " + list1.size());
        System.out.println("List2: " + list2.size());
    }
}
