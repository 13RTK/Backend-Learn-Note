package future;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class FutureTest {
    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            System.out.println("Enter base directory (e.g. /usr/local/jdk5.0/src): ");
            String directory = in.nextLine();

            System.out.println("Enter keyword (e.g. volatile): ");
            String keyword = in.nextLine();

            MatchCounter counter = new MatchCounter(new File(directory), keyword);
            FutureTask<Integer> task = new FutureTask<>(counter);
            Thread t = new Thread(task);
            t.start();

            try {
                System.out.println(task.get() + " matching files.");
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class MatchCounter implements Callable<Integer> {
    private File directory;
    private String keyWord;

    public MatchCounter(File directory, String keyWord) {
        this.directory = directory;
        this.keyWord = keyWord;
    }

    @Override
    public Integer call() throws Exception {
        int count = 0;

        try {
            File[] files = directory.listFiles();
            List<Future<Integer>> results = new ArrayList<>();

            for (File file : files) {
                if (file.isDirectory()) {
                    MatchCounter counter = new MatchCounter(file, keyWord);

                    FutureTask<Integer> task = new FutureTask<>(counter);
                    results.add(task);

                    Thread t = new Thread(task);
                    t.start();
                } else {
                    if (search(file)) {
                        count++;
                    }
                }
            }

            for (Future<Integer> result : results) {
                try {
                    count += result.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return count;
    }



    public boolean search(File file) {
        try {
            try(Scanner in = new Scanner(file, "UTF-8")) {
                boolean found = false;

                while (!found && in.hasNextLine()) {
                    String line = in.nextLine();
                    if (line.contains(keyWord)) {
                        found = true;
                    }
                }

                return found;
            }

        } catch (IOException e) {
            return false;
        }
    }
}
