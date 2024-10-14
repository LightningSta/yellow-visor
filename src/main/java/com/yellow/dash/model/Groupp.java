package com.yellow.dash.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name="groupp")
public class Groupp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique=true, name = "group_name")
    private String group_name;

    @ManyToMany
    @JoinTable(name = "grouppersons",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> persons;

    public Groupp(String group_name) {
        this.group_name = group_name;
    }

    public Groupp() {

    }
}
