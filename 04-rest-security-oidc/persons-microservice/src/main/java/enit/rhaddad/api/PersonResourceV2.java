package enit.rhaddad.api;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import enit.rhaddad.domain.Person;
import enit.rhaddad.exceptions.EntityAlreadyExistsException;
import enit.rhaddad.exceptions.EntityNotFoundException;
import enit.rhaddad.service.PersonService;

@Path("/api/v2/persons")
public class PersonResourceV2 {

    @Inject
    PersonService personService;

    @GET
    public List<Person> findall() {
        return  personService.findAll();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") UUID id) {
        try {
            Person p =  personService.findById(id);
            //return Response.status(Response.Status.OK).entity(p).build();
            return Response.ok(p).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    @POST
    public Response create(Person p) throws EntityAlreadyExistsException {
        p = personService.create(p);
        return Response.status(Response.Status.CREATED).entity(p).build();
    }

    @PUT
    public Response update(Person p) throws EntityNotFoundException{
        p = personService.update(p);
        return Response.status(Response.Status.OK).entity(p).build();
    }
}