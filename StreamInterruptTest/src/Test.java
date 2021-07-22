import java.util.Random;

public class Test {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            Random random = new Random();
            for (int i = 0; i < 1_000_000_000; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Thread has been interrupted");
                    break;
                }
//                if (Thread.currentThread().isInterrupted()) {
//                    System.out.println("Thread has been interrupted");
//                    break;
//                }
                System.out.println(Math.sin(random.nextDouble()));
            }
        });
        System.out.println("Starting thread");
        thread.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread has finished");
    }
}
