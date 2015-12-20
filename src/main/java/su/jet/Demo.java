package su.jet;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

/**
 * @author Denis Anfertev/Jet Infosystems
 *         12/15/15 4:37 PM
 */
public class Demo {
    public static final String BASE_URI = "http://127.0.0.1:8080/demo/";

    public static void main(String[] args) throws IOException {
        final ResourceConfig rc = new ResourceConfig().packages("su.jet");
        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
        System.out.println("Jersey app started at " + BASE_URI + "\nHit enter to stop it...");
        System.in.read();
        server.shutdownNow();
    }
}
