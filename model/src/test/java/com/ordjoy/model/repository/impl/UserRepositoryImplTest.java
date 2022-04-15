package com.ordjoy.model.repository.impl;

import com.ordjoy.model.config.PersistenceConfigTest;
import com.ordjoy.model.entity.user.User;
import com.ordjoy.model.repository.OrderRepository;
import com.ordjoy.model.repository.UserRepository;
import com.ordjoy.model.util.TestDataImporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PersistenceConfigTest.class)
@Transactional
class UserRepositoryImplTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private TestDataImporter testDataImporter;

    @BeforeEach
    public void init() {
        testDataImporter.cleanTestData();
        testDataImporter.importTestData();
    }

    @Test
    @DisplayName("update User's cardNumber test case")
    void updateUser() {
        Optional<User> maybeUser = userRepository.findByLogin("johnyy66");
        assertThat(maybeUser).isNotEmpty();
        User user = maybeUser.get();
        user.getUserData().setCardNumber("12867541892");
        userRepository.update(user);
        Optional<User> updatedByEmail = userRepository.findByEmail("johndow@gmail.com");
        updatedByEmail.ifPresent(it -> assertThat(it.getUserData().getCardNumber())
                .isEqualTo("12867541892"));
    }

    @Test
    @DisplayName("find user by login test case")
    void findByLogin() {
        Optional<User> maybeUser = userRepository.findByLogin("johnyy66");
        assertThat(maybeUser).isNotEmpty();
        User user = maybeUser.get();
        assertNotNull(user);
        assertThat(user.getLogin()).isEqualTo("johnyy66");
    }

    @Test
    @DisplayName("find user by email test case")
    void findByEmail() {
        Optional<User> maybeUser = userRepository.findByEmail("johndow@gmail.com");
        assertThat(maybeUser).isNotEmpty();
        User user = maybeUser.get();
        assertNotNull(user);
        assertThat(user.getEmail()).isEqualTo("johndow@gmail.com");
    }
}