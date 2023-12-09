package vn.fptedu.fptprojectlibrary.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.fptedu.fptprojectlibrary.dto.UserDto;
import vn.fptedu.fptprojectlibrary.model.Role;
import vn.fptedu.fptprojectlibrary.model.User;
import vn.fptedu.fptprojectlibrary.repository.RoleRepository;
import vn.fptedu.fptprojectlibrary.repository.UserRepository;
import vn.fptedu.fptprojectlibrary.util.TbConstants;

import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(UserDto userDto) {
        Role role = roleRepository.findByName(TbConstants.Roles.USER);

        if (role == null)
            role = roleRepository.save(new Role(TbConstants.Roles.USER));

        User user = new User(userDto.getName(), userDto.getUsername(), userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()),
               userDto.getStudentId(), Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User userLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User userLogin = userRepository.findByEmail(currentPrincipalName);
        return userLogin;
    }
}
