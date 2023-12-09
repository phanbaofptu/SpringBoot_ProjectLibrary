package vn.fptedu.fptprojectlibrary.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.fptedu.fptprojectlibrary.dto.ChangePasswordDto;
import vn.fptedu.fptprojectlibrary.model.Material;
import vn.fptedu.fptprojectlibrary.model.Technology;
import vn.fptedu.fptprojectlibrary.model.User;
import vn.fptedu.fptprojectlibrary.repository.UserRepository;
import vn.fptedu.fptprojectlibrary.service.UserService;

import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;


    @GetMapping("/user")
    public String registrationForm() {
        return "user";
    }

    @GetMapping("/user/change-password")
    public String changePassword(Model model){
        ChangePasswordDto changePasswordDto = new ChangePasswordDto();
        model.addAttribute("userPassword", changePasswordDto);
        return "change-password";
    }
    @PostMapping("/user/change-password")
    public String changPasswordSubmit(@Valid ChangePasswordDto changePasswordDto, BindingResult result, Model model) {
        String error="";
        User userLogin = userService.userLogin();
        if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getReNewPassword())){
            error+="Re-New password is not same! ";
            model.addAttribute("error",error);
            return "change-password";
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (passwordEncoder.matches(changePasswordDto.getOldPassword(),userLogin.getPassword())){
            userLogin.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
            userRepository.save(userLogin);
            return "redirect:/user/change-password?success";
        }else{
            error+="Old password is incorrect! ";
            model.addAttribute("error",error);
            return "change-password";
        }
    }
}
