package org.kharcha.app.dto;

import lombok.Getter;

public class UserUpdateDTO {

    @Getter
    private String userEmail;
    @Getter
    private String oldPassword;
    @Getter
    private String newPassword;
    public UserUpdateDTO() {}
    public UserUpdateDTO(String userEmail, String oldPassword, String newPassword) {
        this.userEmail = userEmail;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public void setUserEmail(String userEmail) {}

}
