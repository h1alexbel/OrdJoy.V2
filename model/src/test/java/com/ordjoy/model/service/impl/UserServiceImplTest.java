package com.ordjoy.model.service.impl;

import com.ordjoy.model.config.PersistenceConfigTest;
import com.ordjoy.model.dto.UserDto;
import com.ordjoy.model.dto.UserPersonalInfo;
import com.ordjoy.model.service.UserService;
import com.ordjoy.model.util.TestDataImporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PersistenceConfigTest.class)
@Transactional
class UserServiceImplTest {

    @Autowired
    private UserService userService;
    @Autowired
    private TestDataImporter testDataImporter;

    @BeforeEach
    public void init() {
        testDataImporter.cleanTestData();
        testDataImporter.importTestData();
    }

    @Test
    @DisplayName("find all users with limit and offset")
    void listUsers() {
        List<UserDto> users = userService.list(10, 0);
        assertThat(users).hasSize(4);
    }

    @Test
    @DisplayName("save user test case")
    void saveUser() {
        UserDto savedUser = userService.save(UserDto.builder()
                .login("Alexx")
                .email("alexey@gmail.com")
                .password("secret-password")
                .personalInfo(UserPersonalInfo.builder()
                        .birthDate(LocalDate.of(2003, 10, 9))
                        .build())
                .build());
        assertThat(savedUser.getId()).isNotNull();
    }

    @Test
    @DisplayName("is user has rights to register test case")
    void isUserHasRightsToRegister() {
        boolean result = userService.isUserHasRightsToRegister(UserDto.builder()
                .personalInfo(UserPersonalInfo.builder()
                        .birthDate(LocalDate.of(2013, 12, 11))
                        .build())
                .build());
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("update user discount percentage level test case")
    void updateDiscountPercentageLevel() {
        Optional<UserDto> maybeUser = userService.findUserByLogin("johnyy66");
        if (maybeUser.isPresent()) {
            UserDto userDto = maybeUser.get();
            userService.updateDiscountPercentageLevel(20, userDto.getId());
            Optional<UserDto> byLogin = userService.findUserByLogin("johnyy66");
            byLogin.ifPresent(user ->
                    assertThat(user.getPersonalInfo().getDiscountPercentageLevel())
                            .isEqualTo(20));
        }
    }

    @Test
    @DisplayName("update user account balance test case")
    void updateUserBalanceAmount() {
        Optional<UserDto> maybeUser = userService.findUserByLogin("johnyy66");
        if (maybeUser.isPresent()) {
            UserDto userDto = maybeUser.get();
            userService.updateUserBalanceAmount(new BigDecimal(20), userDto.getId());
            Optional<UserDto> byLogin = userService.findUserByLogin("johnyy66");
            byLogin.ifPresent(user ->
                    assertThat(user.getPersonalInfo().getAccountBalance())
                            .isEqualTo(new BigDecimal(35)));
        }
    }

    @Test
    @DisplayName("find user by email test case")
    void findUserByEmail() {
        Optional<UserDto> maybeUser = userService.findUserByEmail("alex@gmail.com");
        assertThat(maybeUser).isNotEmpty();
    }

    @Test
    @DisplayName("find user by login test case")
    void findUserByLogin() {
        Optional<UserDto> maybeUser = userService.findUserByLogin("johnyy66");
        assertThat(maybeUser).isNotEmpty();
    }

    @Test
    @DisplayName("is email exists test case")
    void isEmailExists() {
        boolean result = userService.isEmailExists("dogcat123@gmail.com");
        assertThat(result).isTrue();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("is email exists null and empty case")
    void isEmailExistsNullAndEmptyCase(String email) {
        assertDoesNotThrow(() -> userService.isEmailExists(email));
    }

    @Test
    @DisplayName("is login exists tests case")
    void isLoginExists() {
        boolean result = userService.isLoginExists("name)");
        assertThat(result).isFalse();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("is login exists null and empty case")
    void isLoginExistsNullAndEmptyCase(String login) {
        assertDoesNotThrow(() -> userService.isLoginExists(login));
    }

    @Test
    @DisplayName("delete user test case")
    void deleteUser() {
        Optional<UserDto> maybeUser = userService.findUserByLogin("johnyy66");
        if (maybeUser.isPresent()) {
            UserDto userDto = maybeUser.get();
            userService.deleteUser(userDto.getId());
            assertThat(userService.findUserByLogin("johnyy66")).isEmpty();
        }
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("delete user null case")
    void deleteUserNullCase(Long id) {
        assertDoesNotThrow(() -> userService.deleteUser(id));
    }
}