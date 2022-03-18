package com.ordjoy.model.dto;

import com.ordjoy.model.entity.user.Role;
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
@ToString(exclude = {"reviews", "userTrackOrders"})
@Builder
public class UserDto implements Serializable {

    private Long id;
    private String login;
    private String password;
    private String email;
    private Role role;
    private UserPersonalInfo personalInfo;
    private List<ReviewDto> reviews;
    private List<UserTrackOrderDto> userTrackOrders;
}