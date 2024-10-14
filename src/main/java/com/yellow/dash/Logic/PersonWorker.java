package com.yellow.dash.Logic;


import com.yellow.dash.model.Person;
import com.yellow.dash.repository.PersonRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonWorker {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean save(String body){
        try {
            JSONObject obj = new JSONObject(body);
            Person person = new Person(obj);
            person.setPassword(passwordEncoder.encode(person.getPassword()));
            if(personRepository.findByNick(person.getNick()) == null){
                personRepository.save(person);
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            return false;
        }
    }
}
