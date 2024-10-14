package com.yellow.dash.repository;

import com.yellow.dash.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    Person findByLogin(String login);
    Person findByTg(String tg);
    Person findByNick(String nick);
    Person findById(int id);
}
