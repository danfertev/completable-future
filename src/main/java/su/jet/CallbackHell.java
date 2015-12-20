package su.jet;

import java.io.IOException;

import static su.jet.FileService.readFileAsync;

/**
 * @author Denis Anfertev/Jet Infosystems
 *         21.12.2015 2:05
 */
public class CallbackHell {
    public static void main(String[] args) throws IOException {
//        readFileAsync("path.txt", System.out::println, Throwable::printStackTrace);


        readFileAsync(
                "path.txt",
                path -> readFileAsync(path, System.out::println, Throwable::printStackTrace),
                Throwable::printStackTrace
        );

        System.in.read();
    }
}
