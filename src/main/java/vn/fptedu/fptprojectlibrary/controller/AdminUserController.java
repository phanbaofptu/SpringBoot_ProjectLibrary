package vn.fptedu.fptprojectlibrary.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.fptedu.fptprojectlibrary.dto.UserDto;
import vn.fptedu.fptprojectlibrary.model.Role;
import vn.fptedu.fptprojectlibrary.model.User;
import vn.fptedu.fptprojectlibrary.repository.RoleRepository;
import vn.fptedu.fptprojectlibrary.repository.UserRepository;
import vn.fptedu.fptprojectlibrary.service.UserService;

import java.util.List;
import java.util.Optional;

@Controller
public class AdminUserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String welcome(Model model){
        return "index";
    }

    @GetMapping("/admin/user-manager")
    public String userList(Model model) {
        List<User> userList = userRepository.findAll();
        model.addAttribute("userList", userList);
        return "user-list";
    }
    @GetMapping("/admin/user-add")
    public String addUser(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "user-add";
    }

    @PostMapping("/admin/user-add")
    public String addUserSubmit(
            @Valid @ModelAttribute("user") UserDto userDto,
            BindingResult result,
            Model model) {
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if (existingUser != null)
            result.rejectValue("email", null,
                    "User already registered !!!");

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "user-add";
        }

        userService.saveUser(userDto);
        return "redirect:/admin/user-add?success";
    }

    @GetMapping("/admin/user-update/id={id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        List<Role> roleList = roleRepository.findAll();
        model.addAttribute("user", user);
        model.addAttribute("roleList", roleList);
        return "user-update";
    }

    @PostMapping("/admin/user-update/id={id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "user-update";
        }
        Optional<User> userFound = userRepository.findById(id);
        user.setPassword(userFound.get().getPassword());
        userRepository.save(user);
        return "redirect:/admin/user-update/id="+id+"?success";
    }

    @GetMapping("/admin/user-delete/id={id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        user.setRoles(null);
        userRepository.save(user);
        userRepository.delete(user);
        return "redirect:/admin/user-manager";
    }

}
