package su.jet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static su.jet.FileService.readFile;
import static su.jet.FileService.readFileAsync;

/**
 * @author Denis Anfertev/Jet Infosystems
 *         12/15/15 4:39 PM
 */
@Path("/")
public class Service {

    @GET
    @Path("sync-content")
    @Produces(MediaType.TEXT_PLAIN)
    public String getContent() {
        String path = readFile("path.txt");
        String content = readFile("file.txt");
        return path + " - " + content;
    }

    @GET
    @Path("async-content")
    @Produces(MediaType.TEXT_PLAIN)
    public String getContentAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<String> path = readFileAsync("path.txt");
        CompletableFuture<String> content = readFileAsync("file.txt");
        CompletableFuture<String> response = path.thenCombineAsync(content, (p, c) -> p + " - " + c);
        return response.get();
    }

    @GET
    @Path("future-content")
    @Produces(MediaType.TEXT_PLAIN)
    public void asyncGet(@Suspended final AsyncResponse asyncResponse) {
        CompletableFuture<String> path = readFileAsync("path.txt");
        CompletableFuture<String> content = readFileAsync("file.txt");
        path.thenCombineAsync(content, (p, c) -> p + " - " + c)
                .thenApplyAsync(asyncResponse::resume)
                .exceptionally(asyncResponse::resume);

        asyncResponse.setTimeout(1, TimeUnit.SECONDS);
        asyncResponse.setTimeoutHandler(ar -> ar.resume(new TimeoutException()));
    }
}
