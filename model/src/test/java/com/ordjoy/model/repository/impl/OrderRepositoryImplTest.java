package com.ordjoy.model.repository.impl;

import com.ordjoy.model.config.PersistenceConfigTest;
import com.ordjoy.model.entity.order.OrderStatus;
import com.ordjoy.model.entity.order.UserTrackOrder;
import com.ordjoy.model.entity.track.Track;
import com.ordjoy.model.entity.user.User;
import com.ordjoy.model.repository.OrderRepository;
import com.ordjoy.model.repository.TrackRepository;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PersistenceConfigTest.class)
@Transactional
class OrderRepositoryImplTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TrackRepository trackRepository;
    @Autowired
    private TestDataImporter testDataImporter;

    @BeforeEach
    public void init() {
        testDataImporter.cleanTestData();
        testDataImporter.importTestData();
    }

    @Test
    @DisplayName("find orders by price test case")
    void findOrdersByPrice() {
        List<UserTrackOrder> ordersByPrice = orderRepository.findOrdersByPrice(new BigDecimal(15));
        assertThat(ordersByPrice).isNotEmpty();
    }

    @Test
    @DisplayName("find orders by user's id test case")
    void findOrdersByUserId() {
        Optional<User> maybeUser = userRepository.findByLogin("alex_0921");
        assertThat(maybeUser).isNotEmpty();
        maybeUser.ifPresent(user -> assertThat(orderRepository.findOrdersByUserId(user.getId()))
                .isNotEmpty().hasSize(2));
    }

    @Test
    @DisplayName("find orders by user's email test case")
    void findOrdersByUserEmail() {
        List<UserTrackOrder> ordersByUserEmail = orderRepository
                .findOrdersByUserEmail("alex@gmail.com", 20, 0);
        assertThat(ordersByUserEmail).isNotEmpty().hasSize(2);
    }

    @Test
    @DisplayName("find orders by user's login test case")
    void findOrdersByUserLogin() {
        List<UserTrackOrder> ordersByUserEmail = orderRepository
                .findOrdersByUserLogin("alex_0921", 20, 0);
        assertThat(ordersByUserEmail).isNotEmpty().hasSize(2);
    }

    @Test
    @DisplayName("find orders by track id")
    void findOrdersByTrackId() {
        Optional<Track> maybeTrack = trackRepository.findByTitle("Jail");
        maybeTrack.ifPresent(track -> assertThat(orderRepository.findOrdersByTrackId(track.getId()))
                .isNotEmpty().hasSize(1));
    }

    @Test
    @DisplayName("find orders by track title")
    void findOrdersByTrackTitle() {
        List<UserTrackOrder> byTrackTitle = orderRepository
                .findOrdersByTrackTitle("Jail", 20, 0);
        assertThat(byTrackTitle).hasSize(1);
    }

    @Test
    @DisplayName("find orders by status test case")
    void findOrdersByStatus() {
        List<UserTrackOrder> byStatus = orderRepository
                .findOrdersByStatus(OrderStatus.ACCEPTED, 20, 0);
        assertThat(byStatus).hasSize(3);
    }
}