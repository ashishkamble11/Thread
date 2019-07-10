import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class BankDriver
{
    static void transfer(Bank from, Bank to, int temp_Amount)
    {
        boolean transfer=false;
        ReentrantLock lock =new ReentrantLock();
        try
        {
            if(lock.tryLock())
            {
                System.out.println(Thread.currentThread().getName()+" Lock acquired.");
                boolean flag = from.debit(temp_Amount);
                if(flag)
                {
                    to.credit(temp_Amount);
                }
                System.out.println(Thread.currentThread().getName()+ ":" + from.acc_Name + " says: now balance is " + from.checkBalance());
                System.out.println(Thread.currentThread().getName()+ ":" + to.acc_Name + " says: now balance is " + to.checkBalance());
                transfer=true;
            }
            else
            {
                System.out.println(Thread.currentThread().getName()+"Unable to acquire lock.");
                transfer(from,to,temp_Amount);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (transfer)
            {
                lock.unlock();
            }
        }
    }
    public static void main(String[] args)
    {
        ExecutorService service = Executors.newFixedThreadPool(3);

        Bank b1=new Bank(10000, "User1");
        Bank b2=new Bank(10000, "User2");

        Runnable a = new Runnable()
        {
            public void run()
            {
                try
                {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                BankDriver.transfer(b1,b2,200);
                System.out.println(Thread.currentThread().getName() +" says :: Transfer successfull");
            }
        };

        Runnable b = new Runnable()
        {
            public void run()
            {
                try
                {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                BankDriver.transfer(b2, b1,1000);
                System.out.println(Thread.currentThread().getName() +" says :: Transfer successfull");
            }
        };

        for(int i=0; i<3; i++)
        {
            service.submit(a);
            service.submit(b);
        }
    }
}
