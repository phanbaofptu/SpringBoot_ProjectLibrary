package vn.fptedu.fptprojectlibrary.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import vn.fptedu.fptprojectlibrary.model.Semester;
import vn.fptedu.fptprojectlibrary.repository.RoleRepository;
import vn.fptedu.fptprojectlibrary.repository.SemesterRepository;
import vn.fptedu.fptprojectlibrary.repository.UserRepository;
import vn.fptedu.fptprojectlibrary.service.UserService;

import java.util.List;

@Controller
public class SemesterController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SemesterRepository semesterRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;


    @GetMapping("/admin/semester-manager")
    public String semesterList(Model model) {
        List<Semester> semesterList = semesterRepository.findAll();
        model.addAttribute("semesterList", semesterList);
        return "semester-list";
    }
    @GetMapping("/admin/semester-add")
    public String addSemester(Model model) {
        Semester semester = new Semester();
        model.addAttribute("semester", semester);
        return "semester-add";
    }

    @PostMapping("/admin/semester-add")
    public String addUser(@Valid Semester semester, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "semester-add";
        }
        semester.setName(semester.getName().toUpperCase().replace(" ",""));
        semesterRepository.save(semester);
        return "redirect:/admin/semester-add?success";
    }

    @GetMapping("/admin/semester-update/id={id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Semester semester = semesterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid semester Id:" + id));
        model.addAttribute("semester", semester);
        return "semester-update";
    }

    @PostMapping("/admin/semester-update/id={id}")
    public String updateSemester(@PathVariable("id") long id, @Valid Semester semester,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            semester.setId(id);
            return "semester-update";
        }
        semesterRepository.save(semester);
        return "redirect:/admin/semester-update/id="+id+"?success";
    }

    @GetMapping("/admin/semester-delete/id={id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {

        Semester semester = semesterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid semester Id:" + id));
        semesterRepository.delete(semester);
        return "redirect:/admin/semester-manager";
    }


}
