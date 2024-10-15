package com.yellow.dash.Logic;


import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class CommitMech extends ProjectsAnalys {


    protected void incrimentCountCommit(String projectName){
        JSONObject obj = readFile(pathToFs+projectName+"/"+maininfo+".json");
        obj.put("commit-count",obj.getInt("commit-count")+1);
        FileWriteJson(obj,pathToFs+projectName+"/"+maininfo+".json");
    }

    public void saveCommit(String projectName) {
        LocalDateTime date = LocalDateTime.now();
        String dateS = date.getSecond()+"-"
                + date.getMinute()+"-"
                + date.getHour()+"-"
                + date.getDayOfMonth()+"-"
                + date.getMonth()+"-"
                + date.getYear();
        FileWriteJson(readFile(pathToFs+projectName+"/"+baseInfo+".json"),pathToFs+projectName+"/"+commitName+"/"+dateS+".json");
        incrimentCountCommit(projectName);
    }

    public JSONObject setCommit(String projectName, String commit) {
        FileWriteJson(readFile(pathToFs+projectName+"/"+commitName+"/"+commit),pathToFs+projectName+"/"+baseInfo+".json");
        return readFile(pathToFs+projectName+"/"+baseInfo+".json");
    }

    public JSONObject getCommitList(String projectName) {
        ArrayList<String> list = new ArrayList<>();
        File file = new File(pathToFs+projectName+"/"+commitName);
        for (int i = 0; i < file.listFiles().length; i++) {
            list.add(file.listFiles()[i].getName());
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("commits",list);
        return jsonObject;
    }

}
