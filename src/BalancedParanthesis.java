import java.util.Scanner;
import java.util.Stack;

public class BalancedParanthesis
{
    public static void main(String[] args)
    {
        final Paranthesis p=new Paranthesis();

        Thread t1 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    p.takeInput();
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    p.verifyParanthesis();
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        });

        t2.start();
        t1.start();

        try
        {
            t1.join();
            t2.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}

class Paranthesis
{
    static String expression;
    static int counter = 0;

    public void takeInput() throws InterruptedException {
        synchronized (this)
        {
            Scanner sc = new Scanner(System.in);

            System.out.println("Enter expression:");
            expression = sc.nextLine();
            try
            {
                notifyAll();
                System.out.println("Notifying waiting Thread.");

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void verifyParanthesis() throws InterruptedException
    {
        synchronized (this)
        {
            Thread.sleep(1000);
            boolean flag = false;

            System.out.println("Before wait block.");

            if (Thread.currentThread().getState() != Thread.State.WAITING && counter < 1)
            {
                counter++;
                try
                {
                    System.out.println(Thread.currentThread().getState());
                    System.out.println("Thread is waiting.");
                    wait();
                    System.out.println("waiting is over.");
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                    System.out.println("Thread is waiting.");
                }
            }

            System.out.println("Aftre wait block.");

            if (expression.isEmpty())
                flag = true;


            Stack<Character> stack = new Stack<Character>();
            for (int i = 0; i < expression.length(); i++)
            {
                char current = expression.charAt(i);
                if (current == '{' || current == '(' || current == '[')
                {
                    stack.push(current);
                }
                if (current == '}' || current == ')' || current == ']')
                {
                    if (stack.isEmpty())
                        flag = false;
                    char last = stack.peek();
                    if (current == '}' && last == '{' || current == ')' && last == '(' || current == ']' && last == '[')
                        stack.pop();
                    else
                        flag = false;
                }
            }
            flag = stack.isEmpty() ? true : false;


            if (flag == true)
            {
                System.out.println("The expression " + expression + " is balanced.");
            }
            else
            {
                System.out.println("The expression " + expression + " is not balanced.");
            }
        }
    }
}