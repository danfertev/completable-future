package su.jet;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static su.jet.FileService.readFileAsync;

/**
 * @author Denis Anfertev/Jet Infosystems
 *         21.12.2015 2:05
 */
public class CallbackHell {
    public static void main(String[] args) throws IOException {
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        readFileAsync(
                "path.txt",
                executor,
                System.out::println,
                Throwable::printStackTrace
        );


        readFileAsync(
                "path.txt",
                executor,
                path -> readFileAsync(
                        path,
                        executor,
                        System.out::println,
                        Throwable::printStackTrace
                ),
                Throwable::printStackTrace
        );

        System.in.read();
    }
}
