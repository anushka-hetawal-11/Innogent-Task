package ExecutorService;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.*;

public class LineCountPool {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        File file = new File("textfiles/sample.txt");

        Callable<Integer> task =()->{
            int count = 0;
            try(BufferedReader br = new BufferedReader(new FileReader(file))){
                while(br.readLine()!=null){
                    count++;
                }
                System.out.println(Thread.currentThread().getName()+" counting");
            }
            return count;
        };

        Future<Integer> future = executor.submit(task);

        try {
            int result = future.get();
            System.out.println(file.getName()+" has lines: "+ result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        executor.shutdown();
    }


}
