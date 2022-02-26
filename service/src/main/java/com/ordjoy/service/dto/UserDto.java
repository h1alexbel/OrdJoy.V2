package com.ordjoy.service.dto;

import com.ordjoy.database.model.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto implements Serializable {

    private Long id;
    private String login;
    private String email;
    private Role role;
    private UserPersonalInfo personalInfo;
}