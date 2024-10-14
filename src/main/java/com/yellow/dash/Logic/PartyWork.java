package com.yellow.dash.Logic;

import com.yellow.dash.model.Groupp;
import com.yellow.dash.model.Person;
import com.yellow.dash.repository.GrouppRepository;
import com.yellow.dash.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PartyWork {
    @Autowired
    private GrouppRepository grouppRepository;
    @Autowired
    private PersonRepository personRepository;

    public void creatyParty(String name, String namesmember) {
        ArrayList<Person> persons = new ArrayList<>();
        for (int i = 0; i < namesmember.split(",").length; i++) {
            String req = namesmember.split(",")[i];
            if(req!=null  && !req.equals(" ")){
                Person person = personRepository.findByNick(req);
                if(person!=null){
                    persons.add(person);
                }
            }
        }
        Person person = new Person(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        person.setId(personRepository.findByNick(person.getNick()).getId());
        persons.add(person);
        Groupp group = new Groupp(name);
        group.setPersons(persons);
        for (int i = 0; i < persons.size(); i++) {
            List<Groupp> grouppList = persons.get(i).getGroups()==null?
                    new ArrayList<>():
                    persons.get(i).getGroups();
            grouppList.add(group);
            persons.get(i).setGroups(grouppList);
        }
        for (int i = 0; i < persons.size(); i++) {
            personRepository.save(persons.get(i));
        }
        grouppRepository.save(group);
    }
    public void creatyParty(String name, ArrayList<Person> persons) {
        Groupp group = new Groupp(name);
        group.setPersons(persons);
        for (int i = 0; i < persons.size(); i++) {
            List<Groupp> grouppList = persons.get(i).getGroups()==null?
                    new ArrayList<>():
                    persons.get(i).getGroups();
            grouppList.add(group);
            persons.get(i).setGroups(grouppList);
        }
        for (int i = 0; i < persons.size(); i++) {
            personRepository.save(persons.get(i));
        }
        grouppRepository.save(group);
    }

}
