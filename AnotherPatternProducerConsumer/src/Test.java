import java.util.LinkedList;
import java.util.Queue;

public class Test {

    public static void main(String[] args) {
        ProducerConsumer pc = new ProducerConsumer();
        Thread thread1 = new Thread(pc::produce);
        Thread thread2 = new Thread(pc::consume);
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

class ProducerConsumer {
    private final Queue<Integer> queue = new LinkedList<>();
    private final Object lock = new Object();
    private final int LIMIT = 10;

    public void produce() {
        int value = 0;

        while (true) {
            synchronized (lock) {
                while (queue.size() == LIMIT) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                queue.offer(value++);
                lock.notify();
            }
        }
    }
    public void consume() {
        while (true) {
            synchronized (lock) {
                while (queue.size() == 0) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                int value = queue.poll();
                System.out.println(value);
                System.out.println("QueueSize " + queue.size());
                lock.notify();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
