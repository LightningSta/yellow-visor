package com.yellow.dash.model;


import com.google.gson.JsonArray;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

@Getter
@Setter
public class Quest {
    private String title;
    //private int countTo;
    //private int countCurrent;
    private ArrayList<String> tags;
    private Boolean completed;
    //private ArrayList<Quest> quests;

    public Quest(String title,Boolean completed, ArrayList<String> tags) {
        this.title = title;
        this.tags = tags;
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Quest{" +
                "title='" + title + '\'' +
                ", completed='" + completed + '\'' +
                ", tags=" + tags +
                '}';
    }

    public JSONObject toJSON(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title",title);
        jsonObject.put("completed",completed);
        jsonObject.put("tags",tags);
        return jsonObject;
    }

    public Quest(JSONObject json) {
        this.title = json.getString("title");
        this.completed=json.optBoolean("completed");
        if(json.optJSONArray("tags")!=null){
            JSONArray tagsArray = json.optJSONArray("tags");
            for (int i = 0; i < tagsArray.length(); i++) {
                tags.add(tagsArray.getString(i));
            }
        }
    }
    public Quest(){

    }
}
