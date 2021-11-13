package enit.rhaddad.api;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import enit.rhaddad.service.TokenManager;


@Path("/api")
public class TokenResource {

    @Inject
    TokenManager tokenManager;

    @POST
    @Path("/token")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TokenResponse getToken(TokenRequest req) {
        System.out.println("-----------token requested");
        TokenResponse res = tokenManager.getAccessToken(req.getEmail(),req.getPassword());
        System.out.println(res);
        return res;
    }
}