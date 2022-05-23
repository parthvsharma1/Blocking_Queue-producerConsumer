// Producer Consumer using the blocking queue  implementation

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

// Create a class for execution
public class Main {

    public static void main(String[] args) throws InterruptedException {

        // Create an ArrayBlockingQueue object with capacity
        int capacity=2;
        BlockingQueue<Integer> bqueue = new ArrayBlockingQueue<Integer>(capacity);


        producer p1 = new producer(bqueue,3,1);
        producer p2 = new producer(bqueue,4,4);
//        producer p3=new producer(bqueue,5,8);
        consumer c1 = new consumer(bqueue,5);

            Thread pThread = new Thread(p1);
            Thread p2Thread = new Thread(p2);
//            Thread p3Thread = new Thread(p3);
            Thread cThread = new Thread(c1);

            // Start all threads
            cThread.start();
            pThread.start();
            p2Thread.start();
//            p3Thread.start();

            pThread.join();
            p2Thread.join();
            cThread.join();
//            p3Thread.join();

        }

}

class producer implements Runnable {

    BlockingQueue<Integer> obj;
    int numOfItems;
    int prodIdBeg;
    public producer(BlockingQueue<Integer> obj,int numOfItems,int prodIdBeg)
    {
        this.obj = obj;
        this.numOfItems=numOfItems;
        this.prodIdBeg=prodIdBeg;
    }

    @Override
    public synchronized void run()
    {
        for (int i = prodIdBeg; i < prodIdBeg+numOfItems; i++) {

            try {
                System.out.println("Produced " + i);
                obj.put(i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

// use put and take instead of add and remove : they work with threads
class consumer implements Runnable {

    BlockingQueue<Integer> obj;

    int numOfItems;

    public consumer(BlockingQueue<Integer> obj,int numOfItems)
    {
        this.obj = obj;
        this.numOfItems=numOfItems;
    }

    @Override
    public void run()
    {
        int taken;
        while (numOfItems-- > 0) {
            try {
                taken = obj.take();
                System.out.println("Consumed " + taken);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

