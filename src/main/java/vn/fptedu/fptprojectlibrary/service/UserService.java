package vn.fptedu.fptprojectlibrary.service;


import vn.fptedu.fptprojectlibrary.dto.UserDto;
import vn.fptedu.fptprojectlibrary.model.User;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    User userLogin();
}
