package ass.core;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/api")
@RegisterRestClient(configKey="sms77_api")
public interface Sms77Client {
    @POST
    @Path("/sms")
    Response sendSms(@HeaderParam("X-Api-Key") String apiKey, @QueryParam("from") String from, @QueryParam("to") String to, @QueryParam("text") String msg);

    @GET
    @Path("/balance")
    String getBalance(@HeaderParam ("X-Api-Key") String apiKey);
}
