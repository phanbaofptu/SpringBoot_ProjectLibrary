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
public class ChangePasswordDto {
    private Long id;

    @NotEmpty(message = "Please enter old password.")
    private String oldPassword;

    @NotEmpty(message = "Please enter new password.")
    private String newPassword;

    @NotEmpty(message = "Please enter re-new password.")
    private String reNewPassword;

}
