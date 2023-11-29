package vn.fptedu.fptprojectlibrary.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    @NotEmpty(message = "Please enter valid name.")
    private String name;

    @NotEmpty(message = "Please enter valid username.")
    private String username;

    @NotEmpty(message = "Please enter valid email.")
    @Email
    private String email;

    @NotEmpty(message = "Please enter valid password.")
    private String password;

    @NotEmpty(message = "Please enter valid student ID.")
    private String studentId;
}
