package enit.rhaddad.service;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import enit.rhaddad.domain.Person;
import enit.rhaddad.exceptions.EntityAlreadyExistsException;
import enit.rhaddad.exceptions.EntityNotFoundException;
import enit.rhaddad.repository.PersonRepository;

@ApplicationScoped
public class PersonService {
    

    @Inject
    PersonRepository personsRepo;

    public List<Person> findAll(){
        return personsRepo.findAll();
    }
    public Person findById(UUID id) throws EntityNotFoundException{
        return personsRepo.findById(id);
    }

    public Person create(Person p) throws EntityAlreadyExistsException{
        return personsRepo.insert(p);
    }
    public Person update(Person p) throws EntityNotFoundException{
        return personsRepo.update(p);      
    }

    public void remove(UUID id){
        personsRepo.delete(id);
    }

}
