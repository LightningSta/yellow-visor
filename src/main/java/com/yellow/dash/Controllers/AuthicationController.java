package com.yellow.dash.Controllers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.yellow.dash.Logic.PersonWorker;
import com.yellow.dash.model.Person;
import com.yellow.dash.model.Quest;
import com.yellow.dash.repository.PersonRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AuthicationController {


    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PersonWorker personWorker;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/registration")
    @ResponseBody
    public String registration(@RequestBody String body) {
        System.out.println(body);
        boolean success = personWorker.save(body);
        return success ? "Registration Successful" : "Registration Failed";
    }

    @PostMapping("/regTeleg")
    @ResponseBody
    public String regTeleg(@RequestBody String body) {
        Person person = new Person(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        person.setTg(new JSONObject(body).getString("tgid"));
        person.setId(personRepository.findByNick(person.getNick()).getId());
        System.out.println(person.getTg());
        personRepository.save(person);
        return "success";
    }


    @PostMapping("/findTg")
    public String authenticateTelegram(@RequestBody String telegramId, HttpServletRequest request) {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(new JSONObject(telegramId).getString("tgid"), null);
            WebAuthenticationDetails details = new WebAuthenticationDetailsSource().buildDetails(request);
            token.setDetails(details);
            Authentication auth = token;
            Authentication result = authenticationManager.authenticate(auth);
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(result);
            SecurityContextHolder.setContext(context);
            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
            return "redirect:/home";
        } catch (AuthenticationException e) {
            return "redirect:/?error=true";
        }
    }



}
