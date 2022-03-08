package com.ordjoy.database.repository.order;

import com.ordjoy.database.config.HibernateConfigTest;
import com.ordjoy.database.model.order.OrderStatus;
import com.ordjoy.database.model.order.UserTrackOrder;
import com.ordjoy.database.model.track.Track;
import com.ordjoy.database.model.user.User;
import com.ordjoy.database.repository.track.TrackRepository;
import com.ordjoy.database.repository.user.UserRepository;
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

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HibernateConfigTest.class)
@Transactional
class OrderRepositoryImplTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TrackRepository trackRepository;
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
    @DisplayName("update order status test case")
    void updateOrderStatus() {
        Optional<UserTrackOrder> maybeOrder = orderRepository.findById(1L);
        maybeOrder.ifPresent(order -> sessionFactory.getCurrentSession().evict(order));
        orderRepository.updateStatus(OrderStatus.IN_PROGRESS, 1L);
        Optional<UserTrackOrder> afterUpdate = orderRepository.findById(1L);
        afterUpdate.ifPresent(order -> assertThat(order.getStatus())
                .isEqualTo(OrderStatus.IN_PROGRESS));
    }

    @Test
    @DisplayName("update order price test case")
    void updateOrderPrice() {
        Optional<UserTrackOrder> maybeOrder = orderRepository.findById(1L);
        maybeOrder.ifPresent(order -> sessionFactory.getCurrentSession().evict(order));
        orderRepository.updatePrice(new BigDecimal(1), 1L);
        Optional<UserTrackOrder> afterUpdate = orderRepository.findById(1L);
        afterUpdate.ifPresent(order -> assertThat(order.getPrice()).isEqualTo("1.00"));
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
        Optional<User> maybeUser = userRepository.findById(2L);
        maybeUser.ifPresent(user -> assertThat(orderRepository.findOrdersByUserId(user.getId()))
                .isNotEmpty().hasSize(2));
    }

    @Test
    @DisplayName("subtract user's balance test case")
    void subtractBalance() {
        Optional<User> maybeUser = userRepository.findById(1L);
        maybeUser.ifPresent(user -> sessionFactory.getCurrentSession().evict(user));
        orderRepository.subtractBalance(new BigDecimal(15), 1L);
        Optional<User> after = userRepository.findById(1L);
        after.ifPresent(user -> assertThat(user.getUserData().getAccountBalance()).isEqualTo("0.00"));
    }

    @Test
    @DisplayName("find orders by user's email test case")
    void findOrdersByUserEmail() {
        List<UserTrackOrder> ordersByUserEmail = orderRepository.findOrdersByUserEmail("alex@gmail.com");
        assertThat(ordersByUserEmail).isNotEmpty().hasSize(2);
    }

    @Test
    @DisplayName("find orders by user's login test case")
    void findOrdersByUserLogin() {
        List<UserTrackOrder> ordersByUserEmail = orderRepository.findOrdersByUserLogin("alex_0921");
        assertThat(ordersByUserEmail).isNotEmpty().hasSize(2);
    }

    @Test
    @DisplayName("find orders by track id")
    void findOrdersByTrackId() {
        Optional<Track> maybeTrack = trackRepository.findById(2L);
        maybeTrack.ifPresent(track -> assertThat(orderRepository.findOrdersByTrackId(track.getId()))
                .isNotEmpty().hasSize(1));
    }

    @Test
    @DisplayName("find orders by track title")
    void findOrdersByTrackTitle() {
        List<UserTrackOrder> byTrackTitle = orderRepository.findOrdersByTrackTitle("Jail");
        assertThat(byTrackTitle).hasSize(1);
    }

    @Test
    @DisplayName("find orders by status test case")
    void findOrdersByStatus() {
        List<UserTrackOrder> byStatus = orderRepository.findOrdersByStatus(OrderStatus.ACCEPTED);
        assertThat(byStatus).hasSize(3);
    }
}