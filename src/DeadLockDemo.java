import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class DeadLockDemo
{
    public static void main(String[] args)
    {
        DeadLockDemo test = new DeadLockDemo();

        final A a = test.new A();
        final B b = test.new B();

        // Thread-1
        Runnable block1 = new Runnable()
        {
            public void run()
            {
                synchronized (b)
                {
                    System.out.println("Lock acquired on B by block 1.");
                    try
                    {
                        Thread.sleep(100);
                        System.out.println("Sleeping.");
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    System.out.println("Block 1 finished work with B");
                    synchronized (a)
                    {
                        System.out.println("Lock acquired on A by block 1.");
                    }
                    System.out.println("Block 1 finished work with A");
                }
            }
        };

        // Thread-2
        Runnable block2 = new Runnable()
        {
            public void run()
            {
                synchronized (b)
                {
                    System.out.println("Lock acquired on B by block 2.");

                    System.out.println("Block 2 finished work with B");
                    synchronized (a)
                    {
                        System.out.println("Lock acquired on A by block 2.");

                        System.out.println("Block 2 finished work with A");
                    }
                }
            }
        };

        new Thread(block1).start();
        new Thread(block2).start();
    }

    // Resource A
    private class A
    {
        private int i = 10;

        public int getI()
        {
            return i;
        }

        public void setI(int i)
        {
            this.i = i;
        }
    }

    // Resource B
    private class B
    {
        private int i = 20;

        public int getI()
        {
            return i;
        }

        public void setI(int i)
        {
            this.i = i;
        }
    }
}