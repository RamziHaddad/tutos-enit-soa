package enit.rhaddad.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

import enit.rhaddad.domain.Person;
import enit.rhaddad.exceptions.*;

@ApplicationScoped
public class PersonRepository {
    

    private List<Person> persons = new ArrayList<Person>();

    public List<Person> findAll(){
        return persons;
    }
    public Person findById(UUID id) throws EntityNotFoundException{
        Person p = new Person(id, "", "", 0);
        if(persons.contains(p)){
            int pos = persons.indexOf(p);
            return persons.get(pos);
        }
        throw new EntityNotFoundException("cannot find person");
    }
    public Person insert(Person p) throws EntityAlreadyExistsException{
        if(p.getId() == null){
            p.setId(UUID.randomUUID());
            persons.add(p);
            return p;
        }
        throw new EntityAlreadyExistsException("person already exists");
    }
    public Person update(Person p) throws EntityNotFoundException{
        if(persons.contains(p)){
            int pos = persons.indexOf(p);
            persons.get(pos).setFirstName(p.getFirstName());
            persons.get(pos).setLastName(p.getLastName());
            persons.get(pos).setAge(p.getAge());
            return p;
        }
        throw new EntityNotFoundException("cannot find person");
    }
    public void delete(Person p){
        persons.remove(p);
    }
    public void delete(UUID id){
        Person p = new Person(id, "", "", 0);
        delete(p);
    }

}
