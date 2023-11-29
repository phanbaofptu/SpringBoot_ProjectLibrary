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
import vn.fptedu.fptprojectlibrary.model.Technology;
import vn.fptedu.fptprojectlibrary.repository.RoleRepository;
import vn.fptedu.fptprojectlibrary.repository.SemesterRepository;
import vn.fptedu.fptprojectlibrary.repository.TechnologyRepository;
import vn.fptedu.fptprojectlibrary.repository.UserRepository;
import vn.fptedu.fptprojectlibrary.service.UserService;

import java.util.List;

@Controller
public class AdminTechnologyController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TechnologyRepository technologyRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;


    @GetMapping("/admin/technology-manager")
    public String technologyList(Model model) {
        List<Technology> technologyList = technologyRepository.findAll();
        model.addAttribute("technologyList", technologyList);
        return "technology-list";
    }
    @GetMapping("/admin/technology-add")
    public String addTechnology(Model model) {
        Technology technology = new Technology();
        model.addAttribute("technology", technology);
        return "technology-add";
    }

    @PostMapping("/admin/technology-add")
    public String addTechnology(@Valid Technology technology, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "technology-add";
        }
        technology.setName(technology.getName().toUpperCase().trim());
        technologyRepository.save(technology);
        return "redirect:/admin/technology-add?success";
    }

    @GetMapping("/admin/technology-update/id={id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Technology technology = technologyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid technology Id:" + id));
        model.addAttribute("technology", technology);
        return "technology-update";
    }

    @PostMapping("/admin/technology-update/id={id}")
    public String updateTechnology(@PathVariable("id") long id, @Valid Technology technology,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            technology.setId(id);
            return "technology-update";
        }
        technologyRepository.save(technology);
        return "redirect:/admin/technology-update/id="+id+"?success";
    }

    @GetMapping("/admin/technology-delete/id={id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {

        Technology technology = technologyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid technology Id:" + id));
        technologyRepository.delete(technology);
        return "redirect:/admin/technology-manager";
    }


}
