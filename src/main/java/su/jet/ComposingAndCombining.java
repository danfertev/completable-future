package su.jet;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static su.jet.FileService.readFileAsync;

/**
 * @author Denis Anfertev/Jet Infosystems
 *         12/21/15 12:14 PM
 */
public class ComposingAndCombining {
    public static void main(String[] args) throws IOException {
        readFileAsync("path.txt")
                .thenComposeAsync(FileService::readFileAsync)
                .thenAcceptAsync(System.out::println)
                .exceptionally(ex -> {
                    ex.printStackTrace();
                    return null;
                });

        CompletableFuture<String> path = readFileAsync("path.txt");
        CompletableFuture<String> content = readFileAsync("file.txt");

        path.thenCombineAsync(content, (p, c) -> p + " - " + c)
                .thenAcceptAsync(System.out::println)
                .exceptionally(ex -> {
                    ex.printStackTrace();
                    return null;
                });

        path.acceptEitherAsync(content, System.out::println);

        System.in.read();
    }
}
