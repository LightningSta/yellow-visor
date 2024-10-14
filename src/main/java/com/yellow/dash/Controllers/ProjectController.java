package com.yellow.dash.Controllers;

import com.yellow.dash.Logic.ProjectsAnalys;
import com.yellow.dash.model.Person;
import com.yellow.dash.repository.PersonRepository;
import com.yellow.dash.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class ProjectController {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ProjectsAnalys projectsAnalys;

    @Autowired
    private MailService mailService;

    @PostMapping("/createProject")
    public String createProject(@RequestBody String json,HttpSession session) {
        System.out.println(json);
        Person person = new Person(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        person.setId(personRepository.findByNick(person.getNick()).getId());
        projectsAnalys.createProject(new JSONObject(json).getString("project_name"),person);
        session.setAttribute("IdWork",new JSONObject(json).getString("project_name"));
        return "redirect:/project";
    }
    @GetMapping("/loadproject")
    @ResponseBody
    public String loadProject(HttpSession session) {
        String idwork = (String) session.getAttribute("IdWork");
        System.out.println(idwork);
        return projectsAnalys.getProject(idwork).toString(2);
    }
    @DeleteMapping("/deleteCard")
    @ResponseBody
    public String deleteCard(HttpSession session,@RequestBody String json) {
        String idwork = (String) session.getAttribute("IdWork");
        projectsAnalys.deleteQuest(idwork,
                new JSONObject(json).getString("title"),
                new JSONObject(json).getString("place")
                );
        return  projectsAnalys.getProject(idwork).toString(2);
    }

    @PatchMapping("/upgradeCard")
    @ResponseBody
    public String upgradeCard(HttpSession session,@RequestBody String json) {
        String idwork = (String) session.getAttribute("IdWork");
        projectsAnalys.upgradeCard(idwork,
                new JSONObject(json).getString("title"),
                new JSONObject(json).getString("place")
        );
        return  projectsAnalys.getProject(idwork).toString(2);
    }


    @GetMapping("/getPermitions")
    @ResponseBody
    public String getRoles(HttpSession session) {
        String idwork = (String) session.getAttribute("IdWork");
        Person person = new Person(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        person.setId(personRepository.findByNick(person.getNick()).getId());
        return projectsAnalys.getRules(person,idwork).toString(2);
    }

    @PostMapping("/saveCard")
    @ResponseBody
    public String saveCard(@RequestBody String json,HttpSession session) {
        String idwork = (String) session.getAttribute("IdWork");
        projectsAnalys.updateCards(new JSONObject(json),idwork);
        try {
            mailService.sendAll(idwork);
        } catch (MessagingException e) {
            System.out.println("Не удалось отправить рассылку");
        }
        return "success";
    }

    @GetMapping("/home")
    public String home(Model model,HttpSession session) {
        Person person = new Person(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        person.setId(personRepository.findByNick(person.getNick()).getId());
        model.addAttribute("projects",projectsAnalys.getProjects(person));
        return "home";
    }
    @GetMapping("/project")
    public String test(HttpSession session,@RequestParam(value = "id",required = false) String id) {
        session.setAttribute("IdWork",id);
        System.out.println(session.getAttribute("IdWork"));
        return "index";
    }
}
