package enit.rhaddad.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedMap;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import enit.rhaddad.api.TokenResponse;

@ApplicationScoped
public class TokenManager{

    @ConfigProperty(name = "quarkus.oidc.auth-server-url")
    String url;

    @ConfigProperty(name = "quarkus.oidc.client-id")
    String clientId;

    @Inject
    @RestClient
    private TokenService tokenService;



    public TokenResponse getAccessToken(String username, String password) {
        Form form = (new Form())
            .param("grant_type", "password")
            .param("username", username)
            .param("password", password)
            .param("client_id", clientId);
        TokenResponse currentToken;
        synchronized(TokenManager.class) {
            currentToken = tokenService.grantToken(form.asMap());
        }
        return currentToken;
    }

    @RegisterRestClient(configKey = "keycloak-token")
    @Produces({"application/json"})
    @Consumes({"application/x-www-form-urlencoded"})
    public interface TokenService {
        @POST
        @Path("/protocol/openid-connect/token")
        TokenResponse grantToken(MultivaluedMap<String, String> var2);

        @POST
        @Path("/protocol/openid-connect/token")
        TokenResponse refreshToken(MultivaluedMap<String, String> var2);
    }
}