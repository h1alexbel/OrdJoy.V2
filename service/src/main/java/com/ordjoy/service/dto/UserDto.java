package com.ordjoy.service.dto;

import com.ordjoy.database.model.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "reviews")
@Builder
public class UserDto implements Serializable {

    private Long id;
    private String login;
    private String email;
    private Role role;
    private UserPersonalInfo personalInfo;
    private List<ReviewDto> reviews;
}