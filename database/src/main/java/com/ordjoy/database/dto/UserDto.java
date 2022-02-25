package com.ordjoy.database.dto;

import com.ordjoy.database.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

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
    private List<ReviewDto> reviews;
}