package com.yellow.dash.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;
import org.json.JSONObject;

@Getter
@Setter
public class Rule {
    private Boolean candelete;
    private Boolean canedit;
    private Boolean canUStage;
    private Boolean canCommit;
    private Boolean canModerate;

    public Rule(Boolean candelete, Boolean canedit, Boolean canUStage, Boolean canCommit, Boolean canModerate) {
        this.candelete = candelete;
        this.canedit = canedit;
        this.canUStage = canUStage;
        this.canCommit = canCommit;
        this.canModerate = canModerate;
    }

    public JSONObject getJsonObject(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("candelete",candelete);
        jsonObject.put("canedit",canedit);
        jsonObject.put("canUStage",canUStage);
        jsonObject.put("canCommit",canCommit);
        jsonObject.put("canModerate",canModerate);
        return jsonObject;
    }
}
