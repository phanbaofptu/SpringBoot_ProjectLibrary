package vn.fptedu.fptprojectlibrary.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import vn.fptedu.fptprojectlibrary.model.*;
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
        project.setGroup(userService.userLogin().getGroups());
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
        List<Project> projectList = projectRepository.findProjectByGroupId(userLogin.getGroups().getId());
        model.addAttribute("projectList", projectList);
        return "student-my-project";
    }

    @GetMapping("/student/project-detail/id={id}")
    public String projectDetail(Model model,@PathVariable("id") long id) {
        Project project = projectRepository.findById(id).orElseThrow();
        model.addAttribute("project", project);
        return "student-project-detail";
    }

    @GetMapping("/teacher/project-detail/id={id}")
    public String projectDetailForTeacher(Model model,@PathVariable("id") long id) {
        Project project = projectRepository.findById(id).orElseThrow();
        model.addAttribute("project", project);
        return "project-detail";
    }

    @GetMapping("/student/project-update/id={id}")
    public String projectUpdate(Model model,@PathVariable("id") long id) {
        Project project = projectRepository.findById(id).orElseThrow();
        model.addAttribute("project", project);
        model.addAttribute("technology", technologyRepository.findTechnologiesByOrderByCategory());
        return "student-project-update";
    }

    @PostMapping("/student/project-update/id={id}")
    public String updateProject(@PathVariable("id") long id,@Valid Project project, BindingResult result, Model model) {
        if (result.hasErrors()) {
            project.setId(id);
            return "student-project-update";
        }
        Project projectFound = projectRepository.findById(id).orElseThrow();
        projectFound.setName(project.getName().trim());
        projectFound.setGroup(userService.userLogin().getGroups());
        projectFound.setStatus("new");
        ZoneId zone = ZoneId.of("Asia/Ho_Chi_Minh");
        ZonedDateTime now = ZonedDateTime.now(zone);
        Timestamp timestamp = Timestamp.valueOf(now.toLocalDateTime());
        projectFound.setDateRegister(timestamp);
        projectRepository.save(projectFound);
        return "redirect:/student/project-update/id="+id+"?success";

    }

    @GetMapping("/student/project-delete/id={id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {

        Project project = projectRepository.findById(id).orElseThrow();
        projectRepository.delete(project);
        return "redirect:/student/my-project";
    }

    @PostMapping("/teacher/project-update/id={id}")
    public String updateProjectForTeacher(@PathVariable("id") long id,@Valid Project project, BindingResult result, Model model) {
        if (result.hasErrors()) {
            project.setId(id);
            return "project-detail";
        }
        Project projectFound = projectRepository.findById(id).orElseThrow();
        ZoneId zone = ZoneId.of("Asia/Ho_Chi_Minh");
        ZonedDateTime now = ZonedDateTime.now(zone);
        Timestamp timestamp = Timestamp.valueOf(now.toLocalDateTime());
        projectFound.setDateStatus(timestamp);
        projectFound.setComment(project.getComment());
        projectFound.setStatus(project.getStatus());
        projectRepository.save(projectFound);
        return "redirect:/teacher/project-detail/id="+id+"?success";

    }

    @GetMapping("/teacher/project-manager/all")
    public String projectList(Model model) {
        List<Project> projectList = projectRepository.findAll();
        model.addAttribute("projectList", projectList);
        model.addAttribute("type", "All Project List");
        return "project-list";
    }

    @GetMapping("/teacher/project-manager")
    public String myProjectList(Model model) {
        User userLogin = userService.userLogin();
        List<Project> projectList = projectRepository.findByMentorId(userLogin.getId());
        model.addAttribute("projectList", projectList);
        model.addAttribute("type", "My Group Project List");
        return "project-list";
    }

}
