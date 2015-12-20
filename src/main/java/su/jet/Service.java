package su.jet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Denis Anfertev/Jet Infosystems
 *         12/15/15 4:39 PM
 */
@Path("/")
public class Service {

    @GET
    @Path("/async-name")
    @Produces(MediaType.TEXT_PLAIN)
    public void asyncGet(@Suspended final AsyncResponse asyncResponse) {
        CompletableFuture.completedFuture("bla")
                .thenApplyAsync(asyncResponse::resume)
                .exceptionally(asyncResponse::resume);

        asyncResponse.setTimeout(1, TimeUnit.SECONDS);
        asyncResponse.setTimeoutHandler(ar -> ar.resume(new TimeoutException()));
    }

}
