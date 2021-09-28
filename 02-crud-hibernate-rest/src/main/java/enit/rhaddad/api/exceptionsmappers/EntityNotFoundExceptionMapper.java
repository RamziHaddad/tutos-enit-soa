package enit.rhaddad.api.exceptionsmappers;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import enit.rhaddad.exceptions.EntityNotFoundException;

@Provider
public class EntityNotFoundExceptionMapper
     implements ExceptionMapper<EntityNotFoundException> {

   public Response toResponse(EntityNotFoundException e) {
      return Response.status(Response.Status.NOT_FOUND).build();
   }
}