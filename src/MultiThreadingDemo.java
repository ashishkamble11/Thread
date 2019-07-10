import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class TakeInput extends Thread
{
    public static String expression;
    Scanner sc = new Scanner(System.in);
    public void run()
    {
        System.out.println("Enter expression:");
        expression = sc.nextLine();
    }
}

public class MultiThreadingDemo
{

    static boolean flag=false;
    public static void main(String[] args)
    {
        /**
         * thread to take input from user
         */
        TakeInput t=new TakeInput();
        t.start();
        try
        {
            //sleep 2 seconds
            Thread.sleep(5000);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        /**
         * thread to validate the expression
         */
        Runnable r=new Runnable()
        {
            @Override
            public void run()
            {
                if (TakeInput.expression.isEmpty())
                    flag=true;

                Stack<Character> stack = new Stack<Character>();
                for (int i = 0; i < TakeInput.expression.length(); i++)
                {
                    char current = TakeInput.expression.charAt(i);
                    if (current == '{' || current == '(' || current == '[')
                    {
                        stack.push(current);
                    }
                    if (current == '}' || current == ')' || current == ']')
                    {
                        if (stack.isEmpty())
                            flag=false;
                        char last = stack.peek();
                        if (current == '}' && last == '{' || current == ')' && last == '(' || current == ']' && last == '[')
                            stack.pop();
                        else
                            flag=false;
                    }
                }
                flag=stack.isEmpty()?true:false;


                if(flag==true)
                {
                    System.out.println("The expression "+TakeInput.expression+" is balanced.");
                    System.out.println("Exiting");
                    System.exit(0);
                }
                else
                {
                    System.out.println("The expression "+TakeInput.expression+" is not balanced.");
                    System.out.println("Exiting");
                    System.exit(0);
                }

            }
        };

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(r);
    }
}