package com.ordjoy.service.mapper.impl;

import com.ordjoy.database.model.user.User;
import com.ordjoy.service.dto.UserDto;
import com.ordjoy.service.dto.UserPersonalInfo;
import com.ordjoy.service.mapper.Mapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Deprecated(since = "1.2")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper implements Mapper<User, UserDto> {

    private static final UserMapper INSTANCE = new UserMapper();

    @Override
    public UserDto mapFrom(User user) {
        return UserDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .email(user.getEmail())
                .role(user.getRole())
                .personalInfo(UserPersonalInfo.builder()
                        .firstName(user.getUserData().getFirstName())
                        .lastName(user.getUserData().getLastName())
                        .birthDate(user.getUserData().getBirthDate())
                        .discountPercentageLevel(user.getUserData().getDiscountPercentageLevel())
                        .build())
                .build();
    }

    public static UserMapper getInstance() {
        return INSTANCE;
    }
}