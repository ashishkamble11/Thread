public class ThreadDemo extends Thread
{
    public void run()
    {
        for(int i=0; i<5; i++)
        {
            System.out.println(Thread.currentThread().getId() + " " + Thread.currentThread().getName());
            try
            {
                Thread.sleep(500);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }

        }
    }
    public static void main(String[] args)
    {
        ThreadDemo t1=new ThreadDemo();
        ThreadDemo t2=new ThreadDemo();
        ThreadDemo t3=new ThreadDemo();

        t1.start();
        try
        {
            t1.join();
        }
        catch (Exception e)
        {
            System.out.println(e.getStackTrace());
        }
        t2.start();
        t3.start();
    }
}
