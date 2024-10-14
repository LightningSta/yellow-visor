package com.yellow.dash.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique=true,name = "nick")
    @NotNull
    private String nick;

    @Column(unique=true,name = "login")
    @NotNull
    private String login;

    @Column(unique=true,name = "password")
    @NotNull
    private String password;

    @Column(unique=true,name = "tg")
    private String tg="";

    @Email()
    @NotNull
    @Column(unique=true,name = "email")
    private String email;


    @Column(name = "role")
    @NotNull
    private String role;

    @Column(name = "projects")
    private String projects="";

    @ManyToMany(mappedBy = "persons")
    private List<Groupp> groups;


    public Person(String nick, String login, String password, String tg, String email, String role, String projects, List<Groupp> groups) {
        this.nick = nick;
        this.login = login;
        this.password = password;
        this.tg = tg;
        this.email = email;
        this.role = role;
        this.projects = projects;
        this.groups = groups;
    }

    public Person() {

    }
    public Person(Object o){
        String obj = o.toString().substring(o.toString().indexOf("{"));
        obj=obj.replace("=",":");
        JSONObject jsonObject = new JSONObject(obj);
        this.nick = jsonObject.optString("nick");
        this.login = jsonObject.optString("login");
        this.password = jsonObject.optString("password");
        this.tg = jsonObject.optString("tg");
        this.email = jsonObject.optString("email");
        this.role = jsonObject.optString("role");
        this.projects = jsonObject.optString("projects");
    }
    public Person(JSONObject obj){
        System.out.println(obj+" IN person");
        String username = obj.getString("username");
        String password = obj.getString("password");
        String nick = obj.getString("nick");
        String email = obj.getString("email");
        setPassword(password);
        setNick(nick);
        setEmail(email);
        setLogin(username);
        setRole("USER");
    }

    @Override
    public String toString() {
        return "Person{" +
                "nick='" + nick + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", tg='" + tg + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", projects='" + projects + '\'' +
                '}';
    }
}
