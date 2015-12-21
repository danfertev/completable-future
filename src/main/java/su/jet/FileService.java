package su.jet;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author Denis Anfertev/Jet Infosystems
 *         21.12.2015 1:45
 */
public class FileService {
    public static void readFileAsync(String path,
                                     Consumer<String> onSuccess,
                                     Consumer<Throwable> onFailure) {
        ForkJoinPool.commonPool().submit(() -> {
            try {
                final List<String> lines = Files.readAllLines(Paths.get(path), Charset.defaultCharset());
                final String content = lines.stream().collect(Collectors.joining("\n"));
                onSuccess.accept(content);
            } catch (IOException e) {
                onFailure.accept(e);
            }
        });
    }

    public static void readFileAsync(String path, ExecutorService executor, Consumer<String> onSuccess, Consumer<Throwable> onFailure) {
        executor.submit(() -> {
            try {
                final List<String> lines = Files.readAllLines(Paths.get(path), Charset.defaultCharset());
                final String content = lines.stream().collect(Collectors.joining("\n"));
                onSuccess.accept(content);
            } catch (IOException e) {
                onFailure.accept(e);
            }
        });
    }

    public static String readFile(String path) {
        try {
            final List<String> lines = Files.readAllLines(Paths.get(path), Charset.defaultCharset());
            return lines.stream().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static CompletableFuture<String> readFileAsync(String path) {
        return CompletableFuture.supplyAsync(() -> readFile(path));
    }
}
