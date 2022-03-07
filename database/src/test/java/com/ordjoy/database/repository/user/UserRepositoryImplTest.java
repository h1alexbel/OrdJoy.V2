package com.ordjoy.database.repository.user;

import com.ordjoy.database.config.HibernateConfigTest;
import com.ordjoy.database.model.review.Review;
import com.ordjoy.database.model.user.User;
import com.ordjoy.database.util.TestDataImporter;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.AfterTestMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HibernateConfigTest.class)
@Transactional
class UserRepositoryImplTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionFactory sessionFactory;

    @BeforeEach
    public void importTestData() {
        TestDataImporter.importTestData(sessionFactory);
    }

    @AfterTestMethod
    public void flush() {
        sessionFactory.close();
    }

    @Test
    @DisplayName("update User's login test case")
    void updateUser() {
        Optional<User> maybeUser = userRepository.findById(1L);
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            user.setLogin("logg");
            userRepository.update(user);
            Optional<User> updatedByEmail = userRepository.findByEmail("johndow@gmail.com");
            updatedByEmail.ifPresent(it -> assertThat(it.getLogin()).isEqualTo("logg"));
        }
    }

    @Test
    @DisplayName("delete user test case")
    void deleteUser() {
        Optional<User> maybeUser = userRepository.findById(1L);
        maybeUser.ifPresent(user -> userRepository.deleteById(user));
        assertThat(userRepository.findById(1L)).isEmpty();
    }

    @Test
    @DisplayName("update user's discountPercentageLevel test case")
    void updateDiscountLevel() {
        Optional<User> maybeUser = userRepository.findById(1L);
        maybeUser.ifPresent(user -> sessionFactory.getCurrentSession().evict(user));
        userRepository.updateDiscountLevel(20, 1L);
        Optional<User> byId = userRepository.findById(1L);
        byId.ifPresent(it -> assertThat(it.getUserData().getDiscountPercentageLevel()).isEqualTo(20));
    }

    @Test
    @DisplayName("update user's account balance test case")
    void updateBalanceAmount() {
        Optional<User> maybeUser = userRepository.findById(1L);
        maybeUser.ifPresent(user -> sessionFactory.getCurrentSession().evict(user));
        userRepository.updateBalanceAmount(new BigDecimal(15), 1L);
        Optional<User> byId = userRepository.findById(1L);
        byId.ifPresent(it -> assertThat(it.getUserData().getAccountBalance())
                .isEqualTo(new BigDecimal("30.00")));
    }

    @Test
    @DisplayName("find user by login test case")
    void findByLogin() {
        Optional<User> maybeUser = userRepository.findByLogin("johnyy66");
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            assertNotNull(user);
            assertThat(user.getLogin()).isEqualTo("johnyy66");
        }
    }

    @Test
    @DisplayName("find user by email test case")
    void findByEmail() {
        Optional<User> maybeUser = userRepository.findByEmail("johndow@gmail.com");
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            assertNotNull(user);
            assertThat(user.getEmail()).isEqualTo("johndow@gmail.com");
        }
    }

    @Test
    @DisplayName("find user by login and password test case")
    void findByLoginAndPassword() {
        Optional<User> maybeUser =
                userRepository.findByLoginAndPassword("alex_0921", "1231bkjg");
        assertAll(() -> maybeUser.ifPresent(user -> assertThat(user.getLogin()).isEqualTo("alex_0921")),
                () -> maybeUser.ifPresent(user -> assertThat(user.getPassword()).isEqualTo("1231bkjg"))
        );
    }

    @Test
    @DisplayName("find Reviews by user login test case")
    void findReviewsByUserLogin() {
        List<Review> reviews = userRepository.findReviewsByUserLogin("johnyy66");
        assertThat(reviews).isNotEmpty();
    }
}