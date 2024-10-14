package com.yellow.dash.Controllers;

import com.yellow.dash.Logic.PartyWork;
import com.yellow.dash.Logic.ProjectsAnalys;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class PartyController {
    @Autowired
    private PartyWork partyWork;


    @PostMapping("/createGroup")
    @ResponseBody
    public String createGroup(Model model, @RequestBody String body) {
        JSONObject jsonObject = new JSONObject(body);
        String name = jsonObject.optString("group_name");
        String namesmember= jsonObject.optString("group_member");
        partyWork.creatyParty(name, namesmember);
        return "success";
    }
}
