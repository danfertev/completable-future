package su.jet;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author Denis Anfertev/Jet Infosystems
 *         20.12.2015 23:50
 */
public class FutureApp {
    public static String getCurrentThreadName() {
        return Optional.ofNullable(Thread.currentThread())
                .map(Thread::getName).orElse("unknown thread");
    }

    public static void main(String[] args) throws InterruptedException {
        final Executor executor = Executors.newSingleThreadExecutor();
        CompletableFuture<String> f = new CompletableFuture<>();
        f.thenAcceptAsync(s -> {
            String name = getCurrentThreadName();
            System.out.println("Completed in thread: " + name +
                    ".\nMessage: " + s);
        }, executor);

        String name = getCurrentThreadName();
        System.out.println("Not completed yet in thread: " + name);

        f.complete("Completed async");
    }
}
