package enit.rhaddad;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnknownCurrencyExceptionMapper
     implements ExceptionMapper<Exception> {

   public Response toResponse(Exception e) {
      System.out.println(e.getMessage());
      return Response.status(Response.Status.BAD_REQUEST).build();
   }
}