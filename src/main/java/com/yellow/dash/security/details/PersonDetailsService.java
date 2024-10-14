package com.yellow.dash.security.details;

import com.yellow.dash.model.Person;
import com.yellow.dash.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;


@Repository
public class PersonDetailsService implements UserDetailsService {

    @Autowired
    private PersonRepository personRepository;


    @Override
    public PersonDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person =  personRepository.findByLogin(username);
        if (person == null) {
            throw new UsernameNotFoundException("Person not found");
        }
        return new PersonDetails(person);
    }
}
