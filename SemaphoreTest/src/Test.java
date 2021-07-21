import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        Connection connection = Connection.getConnection();
        ExecutorService executorService = Executors.newFixedThreadPool(200);
        for (int i = 0; i < 200; i++) {
            executorService.submit(connection::work);
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS);
    }
}

// Singleton
class Connection {
    private static Connection connection = new Connection();
    private Semaphore semaphore = new Semaphore(10);
    private int connectionsCount;

    private Connection() {}
    public static Connection getConnection() {
        return connection;
    }

    public void work() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            doWork();
        } finally {
            semaphore.release();
        }
    }

    public void doWork() {
        synchronized (this) {
            connectionsCount++;
            System.out.println(connectionsCount);
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (this) {
            connectionsCount--;
        }
    }
}