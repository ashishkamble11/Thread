import java.io.UncheckedIOException;
import java.util.concurrent.*;

public class CallableInterfaceDemo
{
    public static void main(String[] args) throws ExecutionException, InterruptedException
    {
        ExecutorService service = Executors.newSingleThreadExecutor();
        CallableFactorialTask task = new CallableFactorialTask(5);
        Future<Integer> f = service.submit(task);
        Integer val = f.get();
        System.out.println(val);
        service.shutdown();
    }
}
class CallableFactorialTask implements Callable<Integer>
{
    private int num = 0;
    public CallableFactorialTask(int num)
    {
        this.num = num;
    }

    @Override
    public Integer call()throws RuntimeException
    {
        int prod = 1;
        prod+=num;
        return prod;
    }
}