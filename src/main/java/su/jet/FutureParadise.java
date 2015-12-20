package su.jet;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * @author Denis Anfertev/Jet Infosystems
 *         21.12.2015 2:11
 */
public class FutureParadise {
    public static void main(String[] args) throws IOException {
        CompletableFuture.supplyAsync(() -> FileService.readFile("path.txt"))
                .thenApplyAsync(FileService::readFile)
                .thenApplyAsync(String::toUpperCase)
                .whenCompleteAsync((content, ex) -> {
                    if (ex == null) {
                        System.out.println(content);
                    } else {
                        ex.printStackTrace();
                    }
                })
                .thenRunAsync(() -> System.out.println("File successfully read"));

        System.in.read();
    }
}
