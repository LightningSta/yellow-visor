package com.yellow.dash.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Project {
    private String projectName;
    private String projectDate;
    private Integer IDCreator;

    public Project(String projectName, String projectDate, Integer IDCreator) {
        this.projectName = projectName;
        this.projectDate = projectDate;
        this.IDCreator = IDCreator;
    }


}
