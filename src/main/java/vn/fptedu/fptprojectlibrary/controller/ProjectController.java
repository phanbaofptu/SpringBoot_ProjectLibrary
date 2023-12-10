package vn.fptedu.fptprojectlibrary.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import vn.fptedu.fptprojectlibrary.model.Project;
import vn.fptedu.fptprojectlibrary.model.Semester;
import vn.fptedu.fptprojectlibrary.model.User;
import vn.fptedu.fptprojectlibrary.repository.*;
import vn.fptedu.fptprojectlibrary.service.UserService;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Controller
public class ProjectController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TechnologyRepository technologyRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserService userService;


    @GetMapping("/student/register-project")
    public String registerForm(Model model) {
        Project project = new Project();
        model.addAttribute("project", project);
        model.addAttribute("technology", technologyRepository.findTechnologiesByOrderByCategory());
        return "student-register-project";
    }

    @PostMapping("/student/register-project")
    public String registerProject(@Valid Project project, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "student-register-project";
        }
        project.setName(project.getName().trim());
        project.setGroup(userService.userLogin().getGroup());
        project.setStatus("new");
        ZoneId zone = ZoneId.of("Asia/Ho_Chi_Minh");
        ZonedDateTime now = ZonedDateTime.now(zone);
        Timestamp timestamp = Timestamp.valueOf(now.toLocalDateTime());
        project.setDateRegister(timestamp);
        projectRepository.save(project);
        return "redirect:/student/register-project?success";
    }

    @GetMapping("/student/my-project")
    public String myProject(Model model) {
        User userLogin = userService.userLogin();
        List<Project> projectList = projectRepository.findProjectByGroupId(userLogin.getGroup().getId());
        model.addAttribute("projectList", projectList);
        return "student-my-project";
    }



}
