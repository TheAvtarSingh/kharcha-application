package org.kharcha.kharcha.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class UserDTO {

    private String userId;

    private String userEmail;

    private String userPassword;

}
