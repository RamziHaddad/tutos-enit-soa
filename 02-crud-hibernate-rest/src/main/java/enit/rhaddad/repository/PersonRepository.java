package enit.rhaddad.repository;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import enit.rhaddad.domain.Person;
import enit.rhaddad.exceptions.*;

@ApplicationScoped
public class PersonRepository {
    

    @Inject
    EntityManager em;
    public List<Person> findAll(){
        return em.createQuery("from Person",Person.class).getResultList();
    }
    public Person findById(UUID id) throws EntityNotFoundException{
        Person p = em.find(Person.class, id);
        if(p!=null){
            return p;
        }
        throw new EntityNotFoundException("cannot find person");
    }
    @Transactional
    public Person insert(Person p) throws EntityAlreadyExistsException{
        if(p.getId() == null){
            p.setId(UUID.randomUUID());
            try{
                em.persist(p);
                return p;
            }catch(EntityExistsException e){
                throw new EntityAlreadyExistsException("person already exists");
            }
        }
        throw new EntityAlreadyExistsException("person has already an id");
    }
    @Transactional
    public Person update(Person p) throws EntityNotFoundException{
        try{
            return em.merge(p);
        }catch(IllegalArgumentException e){

        }
        throw new EntityNotFoundException("cannot find person");
    }

    @Transactional
    public void delete(UUID id){
        Person p = em.find(Person.class, id);
        if(p!=null){
            em.remove(p);
        }
    }

}
