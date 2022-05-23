// Tried implementing the blocking queue by myself using wait and notify
// just for practice and understanding
 class myBlockingQ {
    node front;
    node rear;
    int capacity;
    int length;

    myBlockingQ(int capacity){
        this.capacity=capacity;
        this.length=0;
    }
    public synchronized void put(int val) throws InterruptedException {
        if(this.length>capacity)
        {
            System.out.println(this.length+" > "+ this.capacity);
            System.out.println("queue is full please wait ");
            wait();
        }

        node n=new node(val);
        if(this.length==0)
        {
            rear=n;
            front=rear;
        }
        else {
            rear.nextNode = n;
            rear = n;
        }
        length++;
        System.out.println(val+" added to queue ");
        notifyAll();
    }

    public synchronized int take()throws InterruptedException
    {
        if(this.length==0)
        {
            System.out.println("Empty ! , wait for value to come ");
            wait();
        }

        int data=front.getData();
        front=front.nextNode;
        length--;
        return data;
    }
}
 public class implementMyBlockingQueue{
     public static void main(String[] args) throws InterruptedException {
         myBlockingQ q = new myBlockingQ(10);

             for(int i=0;i<5;i++) {

             new Thread(() -> {
                 try {
                     int removed = q.take();
                     System.out.println(removed + " has been removed from the buffer ");
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             }).start();
                Thread.sleep(1000);// only for better demonstration

                 int finali = i+1;
                 new Thread(() -> {
                     try {
                         q.put(finali);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
                 }).start();

                 Thread.sleep(1000);// only for better demonstration

             }

     }
 }
