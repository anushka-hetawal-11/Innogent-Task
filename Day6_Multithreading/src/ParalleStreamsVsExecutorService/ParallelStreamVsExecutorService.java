package ParalleStreamsVsExecutorService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class ParallelStreamVsExecutorService {
    public static void main(String[] args) throws Exception {

        int N = 10_000_000;
        int numThreads = 4;

        System.out.println("\nExecutorService Version");

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<Long>> futures = new ArrayList<>();

        int chunkSize = N / numThreads;
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize + 1;
            int end = (i == numThreads - 1) ? N : (i + 1) * chunkSize;

            Callable<Long> task = () -> {
                long sum = 0;
                for (int j = start; j <= end; j++) {
                    sum += j;
                }
                return sum;
            };

            futures.add(executor.submit(task));
        }

        long totalSum = 0;
        for (Future<Long> f : futures) {
            totalSum += f.get();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Total Sum: " + totalSum);
        System.out.println("ExecutorService Time: " + (endTime - startTime) + " ms");

        executor.shutdown();

        System.out.println("\nParallel Stream Version");

        startTime = System.currentTimeMillis();

        long streamSum = IntStream.rangeClosed(1, N)
                .parallel()
                .asLongStream()
                .sum();

        endTime = System.currentTimeMillis();

        System.out.println("Total Sum: " + streamSum);
        System.out.println("Parallel Stream Time: " + (endTime - startTime) + " ms");
    }
}
