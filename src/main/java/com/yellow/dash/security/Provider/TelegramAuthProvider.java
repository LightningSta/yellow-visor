package com.yellow.dash.security.Provider;

import com.yellow.dash.model.Person;
import com.yellow.dash.repository.PersonRepository;
import com.yellow.dash.security.details.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TelegramAuthProvider implements AuthenticationProvider {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String telegramId = (String) authentication.getPrincipal();
        Person person = personRepository.findByTg(telegramId); // Поиск по telegramId
        if (person == null) {
            throw new UsernameNotFoundException("User with this Telegram ID not found.");
        }

        PersonDetails personDetails = new PersonDetails(person);

        return new UsernamePasswordAuthenticationToken(personDetails, null, personDetails.getAuthorities());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
