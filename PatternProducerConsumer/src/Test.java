import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Test {
    private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

    public static void main(String[] args) {
        Thread thread1 = new Thread(Test::produce);
        Thread thread2 = new Thread(Test::consumer);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void produce() {
        Random random = new Random();

        while (true) {
            try {
                queue.put(random.nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void consumer() {
        Random random = new Random();
        while (true) {
            try {
                Thread.sleep(100);
                if (random.nextInt(10) == 5) {
                    System.out.println(queue.take());
                    System.out.println("Queue size is " + queue.size());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
