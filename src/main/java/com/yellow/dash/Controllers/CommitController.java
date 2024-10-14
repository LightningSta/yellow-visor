package com.yellow.dash.Controllers;

import com.yellow.dash.Logic.CommitMech;
import com.yellow.dash.Logic.ProjectsAnalys;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class CommitController {

    @Autowired
    private CommitMech commitMech;


    @PostMapping("/commit")
    @ResponseBody
    public String commit(HttpSession session) {
        String name = (String) session.getAttribute("IdWork");
        commitMech.saveCommit(name);
        return "success";
    }
    @GetMapping("/commit")
    @ResponseBody
    public String setcommit(HttpSession session) {
        String name = (String) session.getAttribute("IdWork");
        return commitMech.getCommitList(name).toString(2);
    }

    @PostMapping("/commits")
    @ResponseBody
    public String setcommits(HttpSession session, @RequestBody String json) {
        System.out.println("work");
        String name = (String) session.getAttribute("IdWork");
        return commitMech.setCommit(name,new JSONObject(json).getString("commit")).toString(2);
    }
}
