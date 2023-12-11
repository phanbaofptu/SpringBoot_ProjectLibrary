package vn.fptedu.fptprojectlibrary.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import vn.fptedu.fptprojectlibrary.dto.ChangePasswordDto;
import vn.fptedu.fptprojectlibrary.model.Group;
import vn.fptedu.fptprojectlibrary.model.Semester;
import vn.fptedu.fptprojectlibrary.model.Technology;
import vn.fptedu.fptprojectlibrary.model.User;
import vn.fptedu.fptprojectlibrary.repository.GroupRepository;
import vn.fptedu.fptprojectlibrary.repository.SemesterRepository;
import vn.fptedu.fptprojectlibrary.repository.UserRepository;
import vn.fptedu.fptprojectlibrary.service.UserService;

import java.util.List;
import java.util.Optional;

@Controller
public class GroupController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private SemesterRepository semesterRepository;


    @GetMapping("/teacher/group-manager")
    public String list(Model model) {
        User userLogin = userService.userLogin();
        List<Group> groupList = groupRepository.findByMentorId(userLogin.getId());
        model.addAttribute("groupList", groupList);
        model.addAttribute("type", "My Group List");
        return "group-list";
    }

    @GetMapping("/teacher/group-manager/all")
    public String listAll(Model model) {

        List<Group> groupList = groupRepository.findAll();
        model.addAttribute("groupList", groupList);
        model.addAttribute("type", "All Group List");

        return "group-list";
    }
    @GetMapping("/teacher/group-add")
    public String registrationForm(Model model) {
        Group group = new Group();
        model.addAttribute("group", group);
        List<Semester> semesterList = semesterRepository.findAll();
        model.addAttribute("semesterList", semesterList);
        List<User> userList = userRepository.findAll();
        model.addAttribute("userList", userList);
        return "group-add";
    }

    @PostMapping("/teacher/group-add")
    public String add(@Valid Group group, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "group-add";
        }
        String groupName = group.getName().toUpperCase().trim();
        group.setName(groupName);
        User userLogin = userService.userLogin();
        group.setMentorId(userLogin.getId());
        for (User member : group.getMembers()) {
            if (member.getGroups()==null){
                member.setGroups(group);
            }
            else {
                model.addAttribute("message","User "+ member.getUsername() +" have Groups");
                return "redirect:/teacher/group-add?error=User "+ member.getUsername() +" have Groups";
            }
        }
        groupRepository.save(group);
        return "redirect:/teacher/group-add?success";
    }

    @GetMapping("/teacher/group-update/id={id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid group Id:" + id));
        model.addAttribute("group", group);
        List<Semester> semesterList = semesterRepository.findAll();
        model.addAttribute("semesterList", semesterList);
        List<User> userList = userRepository.findAll();
        model.addAttribute("userList", userList);
        return "group-update";
    }

    @PostMapping("/teacher/group-update/id={id}")
    public String update(@PathVariable("id") long id, @Valid Group group,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            group.setId(id);
            return "group-update";
        }
        Group oldGroup = groupRepository.findById(id).get();
        for (User member : oldGroup.getMembers()) {
            member.setGroups(null);
        }
        for (User member : group.getMembers()) {
            if (member.getGroups()==null){
                member.setGroups(group);
            }
            else {
                model.addAttribute("message","User "+ member.getUsername() +" have Groups");
                return "redirect:/teacher/group-update/id="+id+"?error=User "+ member.getUsername() +" have Groups";
            }
        }
        userRepository.saveAll(oldGroup.getMembers());

        groupRepository.save(group);
        return "redirect:/teacher/group-update/id="+id+"?success";
    }

    @GetMapping("/teacher/group-delete/id={id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {

        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid group Id:" + id));
        for (User member : group.getMembers()) {
            member.setGroups(null);
        }
        userRepository.saveAll(group.getMembers());
        groupRepository.delete(group);
        return "redirect:/teacher/group-manager";
    }

    @GetMapping("/student/my-group")
    public String myGroup(Model model) {
        User userLogin = userService.userLogin();
        model.addAttribute("group", userLogin.getGroups());
        return "student-my-group";
    }

    @GetMapping("/teacher/group-view-detail/id={id}")
    public String groupDetail(@PathVariable("id") long id,Model model) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid group Id:" + id));
        model.addAttribute("group", group);
        return "group-detail";
    }


}
