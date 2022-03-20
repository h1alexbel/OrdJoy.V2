package com.ordjoy.model.dto;

import com.ordjoy.model.entity.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserDto implements Serializable {

    private Long id;
    private String login;
    private String password;
    private String email;
    private Role role;
    private UserPersonalInfo personalInfo;
}