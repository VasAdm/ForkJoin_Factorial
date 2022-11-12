import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class Main {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountFactorial countFactorial = new CountFactorial(15);
        System.out.println(forkJoinPool.invoke(countFactorial));
    }
}

class CountFactorial extends RecursiveTask<Long> {
    private long number1;
    private long number2;

    public CountFactorial(long number1, long number2) {
        this.number1 = number1;
        this.number2 = number2;
    }

    public CountFactorial(long number) {
        this(1, number);
    }

    @Override
    protected Long compute() {
        if (number1 - number2 <= 5) {
            return multiply(number2);
        } else {
            long mid = number1 + (number2 - number1) / 2;
            CountFactorial task1 = new CountFactorial(number1, mid);
            CountFactorial task2 = new CountFactorial(mid + 1, number2);

            task1.fork();
            long c = task2.compute();

            return c * task1.join();
        }
    }

    private long multiply(long num) {
        if (num == number1) {
            return number1;
        }
        return num * multiply(num - 1);
    }
}