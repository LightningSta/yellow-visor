package com.yellow.dash.Logic;

import com.google.gson.JsonArray;
import com.yellow.dash.model.Person;
import com.yellow.dash.model.Project;
import com.yellow.dash.model.Quest;
import com.yellow.dash.model.Rule;
import com.yellow.dash.repository.PersonRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class ProjectsAnalys {

    @Value("${PathToFS}")
    protected String pathToFs;

    @Value("${CommitName}")
    protected String commitName;

    @Value("${BaseInfo}")
    protected String baseInfo;

    @Value("${MainInfo}")
    protected String maininfo;
    @Value("${roleInfo}")
    protected String role_info;


    @Autowired
    protected PersonRepository personRepository;


    protected boolean createFolderAndCommit(String project_name) {
        File folder = new File(pathToFs+project_name);
        if(!folder.exists()){
            folder.mkdir();
            File commit_folder = new File(pathToFs+project_name+"/"+commitName);
            commit_folder.mkdir();
            return true;
        }else{
            return false;
        }
    }

    public void deleteQuest(String project_name,String title,String place){
        System.out.println(project_name+" "+title+" "+place);
        JSONObject jsonObject = readFile(pathToFs+project_name+"/"+baseInfo+".json");
        JSONArray jsonArray = jsonObject.getJSONArray(place);
        System.out.println(title);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject que = jsonArray.getJSONObject(i);
            Quest  quest = new Quest(que);
            System.out.println(quest);
            if(quest.getTitle().equals(title)){
                jsonArray.remove(i);
            }
        }
        FileWriteJson(jsonObject, pathToFs+project_name+"/"+baseInfo+".json");
    }

    public void upgradeCard(String project_name,String title,String place){
        JSONObject jsonObject = readFile(pathToFs+project_name+"/"+baseInfo+".json");
        JSONArray jsonArray = jsonObject.getJSONArray(place);
        Quest findQuest = new Quest();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject que = jsonArray.getJSONObject(i);
            Quest  quest = new Quest(que);
            if(quest.getTitle().equals(title)){
                jsonArray.remove(i);
                findQuest=quest;
            }
        }
        if(place.equals("InProgress")){
            JSONArray jsonArray2 = jsonObject.getJSONArray("Ready");
            jsonArray2.put(findQuest.toJSON());
        }else if(place.equals("Starting")){
            JSONArray jsonArray2 = jsonObject.getJSONArray("InProgress");
            jsonArray2.put(findQuest.toJSON());
        }
        FileWriteJson(jsonObject, pathToFs+project_name+"/"+baseInfo+".json");
    }

    protected void FileWriteJson(JSONObject json, String path){
        File file = new File(path);
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(json.toString(2));
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void createProjectInfo(String pathTo){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Starting",new ArrayList<Quest>());
        jsonObject.put("InProgress",new ArrayList<Quest>());
        jsonObject.put("Ready",new ArrayList<Quest>() );
        FileWriteJson(jsonObject, pathTo+"/"+baseInfo+".json");
    }



    protected JSONObject readFile(String path){
        File file = new File(path);
        FileReader fr = null;
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        JSONTokener tokener = new JSONTokener(fr);
        JSONObject jsonObject = new JSONObject(tokener);
        return jsonObject;
    }
    protected void createRole(String pathTo,Person person){
        JSONObject jsonObject = new JSONObject();
        Rule rule = new Rule(false,false,false,false,false);
        jsonObject.put(String.valueOf(person.getId()),rule.getJsonObject());
        System.out.println(jsonObject.toString(2));

        FileWriteJson(jsonObject, pathTo+"/"+role_info+".json");
    }


    public void saveProjectInfo(String name,String category,String cparam){
        JSONObject jsonObject = readFile(pathToFs+name+"/"+baseInfo+".json");
        jsonObject.put(category,cparam);
        FileWriteJson(jsonObject, pathToFs+name+"/"+baseInfo+".json");
    }


    public void updateCards(JSONObject jsonObject, String idwork){
        JSONObject jsonObject1 = readFile(pathToFs+idwork+"/"+baseInfo+".json");
        ArrayList<Quest> quests = new ArrayList<>();
        JSONArray jsonArray = jsonObject1.getJSONArray(jsonObject.getString("place"));
        for (int i = 0; i < jsonArray.length(); i++) {
            Quest quest = new Quest(jsonArray.getJSONObject(i));
            quests.add(quest);
        }
        Quest quest = new Quest(jsonObject);
        quests.add(quest);
        saveProjectInfo(idwork,jsonObject.getString("place"), quests);
    }

    public void saveProjectInfo(String name,String category,ArrayList<Quest> quests){
        JSONObject jsonObject = readFile(pathToFs+name+"/"+baseInfo+".json");
        jsonObject.put(category,quests);
        FileWriteJson(jsonObject, pathToFs+name+"/"+baseInfo+".json");
    }

    public void createMainInfo(String name, Person person){
        String pathTo = pathToFs+name;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("project-name",name);
        String date =  LocalDate.now().toString();
        jsonObject.put("date-created",date);
        jsonObject.put("commit-count",0);
        ArrayList<Integer> usersaccess = new ArrayList<>();
        usersaccess.add(person.getId());
        jsonObject.put("IDCreator",person.getId());
        jsonObject.put("users-access",usersaccess);
        jsonObject.put("group-access",new ArrayList<Integer>());
        System.out.println(jsonObject.toString(2));
        FileWriteJson(jsonObject, pathTo+"/"+maininfo+".json");
    }

    protected ArrayList<Person> getPersons(String persons,Person person){
        ArrayList<Person> personList = new ArrayList<>();

        for (int i = 0; i < persons.split(",").length; i++) {
            personList.add(personRepository.findByNick(persons.split(",")[i]));
        }
        personList.add(person);
        return personList;
    }

    protected boolean ArrayContented(JSONArray jsonArray,Integer id){
        for (int i = 0; i < jsonArray.length(); i++) {
            if(jsonArray.getInt(i)==id){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Project> getProjects(Person person){
        ArrayList<Project> projects = new ArrayList<>();
        File dir = new File(pathToFs);
        if(dir.listFiles()!=null){
            for (File file : dir.listFiles()) {
                JSONObject jsonObject =readFile(file.getAbsolutePath()+"/"+maininfo+".json");
                if(ArrayContented(jsonObject.getJSONArray("users-access"),person.getId())){
                    Project project = new Project(jsonObject.getString("project-name"),
                            jsonObject.getString("date-created"),
                            jsonObject.getInt("IDCreator")

                    );
                    projects.add(project);
                }
            }
        }
        return projects;
    }

    public JSONObject getProject(String project_name){
        return readFile(pathToFs+project_name+"/"+baseInfo+".json");
    }
    public JSONObject getMainProject(String project_name){
        return readFile(pathToFs+project_name+"/"+maininfo+".json");
    }

    public void createProject(String name, Person person){
        File directory = new File(pathToFs);
        System.out.println(directory.exists());
        System.out.println(directory.isDirectory());
        if (!directory.exists()) {
            directory.mkdirs();
            System.out.println("Directory created");
        }
        if(createFolderAndCommit(name)){
            createMainInfo(name, person);
            createProjectInfo(pathToFs+name);
            createRole(pathToFs+name,person);
        }
    }

    public JSONObject getRules(Person person, String project_name){
        JSONObject jsonObject = readFile(pathToFs+project_name+"/"+role_info+".json");
        return jsonObject.getJSONObject(String.valueOf(person.getId()));
    }

    public ArrayList<Person> getPersonFromProject(String name_project){
        ArrayList<Person> persons = new ArrayList<>();
        JSONObject project = getMainProject(name_project);
        JSONArray jsonArray = project.getJSONArray("users-access");
        for (int i = 0; i < jsonArray.length(); i++) {
            Person person = personRepository.findById(jsonArray.getInt(i));
            if(person!=null){
                persons.add(person);
            }
        }
        return persons;
    }





}
