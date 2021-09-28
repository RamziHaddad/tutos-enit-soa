package enit.rhaddad.api;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import enit.rhaddad.service.PersonService;
import enit.rhaddad.domain.Person;
import enit.rhaddad.exceptions.EntityAlreadyExistsException;
import enit.rhaddad.exceptions.EntityNotFoundException;

@Path("/api/v1/persons")
public class PersonResourceV1 {

    @Inject
    PersonService personService;

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello RESTEasy";
    }

    @GET
    public List<Person> findall() {
        return  personService.findAll();
    }

    @GET
    @Path("/{id}")
    public Person findById(@PathParam("id") UUID id) throws EntityNotFoundException {
        return  personService.findById(id);
    }

    @POST
    public Person create(Person p) throws EntityAlreadyExistsException {
        return  personService.create(p);
    }

    @PUT
    public Person update(Person p) throws EntityNotFoundException{
        return  personService.update(p);
    }
}