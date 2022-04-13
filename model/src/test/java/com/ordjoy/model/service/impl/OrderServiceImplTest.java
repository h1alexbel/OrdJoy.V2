package com.ordjoy.model.service.impl;

import com.ordjoy.model.config.PersistenceConfigTest;
import com.ordjoy.model.dto.TrackDto;
import com.ordjoy.model.dto.UserDto;
import com.ordjoy.model.dto.UserTrackOrderDto;
import com.ordjoy.model.entity.order.OrderStatus;
import com.ordjoy.model.service.OrderService;
import com.ordjoy.model.service.TrackService;
import com.ordjoy.model.service.UserService;
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
class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private TrackService trackService;
    @Autowired
    private TestDataImporter testDataImporter;

    @BeforeEach
    public void init() {
        testDataImporter.cleanTestData();
        testDataImporter.importTestData();
    }

    @Test
    @DisplayName("find all Orders with limit & offset")
    void listOrders() {
        List<UserTrackOrderDto> userTrackOrders = orderService.list(10, 1);
        assertThat(userTrackOrders).hasSize(2);
    }

    @Test
    @DisplayName("make order test case")
    void makeOrder() {
        Optional<TrackDto> maybeTrack = trackService.findTrackByTitle("Unforgiv8en");
        Optional<UserDto> maybeUser = userService.findUserByLogin("johnyy66");
        if (maybeUser.isPresent() && maybeTrack.isPresent()) {
            TrackDto trackDto = maybeTrack.get();
            UserDto userDto = maybeUser.get();
            UserTrackOrderDto savedUserTrackOrder = orderService.save(UserTrackOrderDto.builder()
                    .price(new BigDecimal(1111))
                    .track(trackDto)
                    .user(userDto)
                    .build());
            Optional<UserTrackOrderDto> maybeOrder = orderService.findById(savedUserTrackOrder.getId());
            assertThat(maybeOrder).isNotEmpty();
        }
    }

    @Test
    @DisplayName("update order status test case")
    void updateOrderStatus() {
        orderService.updateOrderStatus(OrderStatus.CANCELLED, 2L);
        Optional<UserTrackOrderDto> orderById = orderService.findById(2L);
        orderById.ifPresent(orderDto -> assertThat(orderDto.getStatus())
                .isEqualTo(OrderStatus.CANCELLED));
    }

    @Test
    @DisplayName("calculate order price")
    void calculateOrderPrice() {
        Optional<UserTrackOrderDto> orderById = orderService.findById(1L);
        orderById.ifPresent(orderDto -> {
            BigDecimal finalPrice = orderService.calculateOrderPrice(orderDto.getId());
            orderDto.setPrice(finalPrice);
            orderService.update(orderDto);
        });
        orderService.findById(1L)
                .ifPresent(orderDto ->
                        assertThat(orderDto.getPrice()).isNotEqualTo(new BigDecimal(15)));
    }

    @Test
    @DisplayName("find orders by price test case")
    void findOrdersByPrice() {
        List<UserTrackOrderDto> ordersByPrice = orderService.findOrdersByPrice(new BigDecimal(15));
        assertThat(ordersByPrice).hasSize(1);
    }

    @Test
    @DisplayName("find orders by user id test case")
    void findOrdersByUserId() {
        Optional<UserDto> maybeUser = userService.findUserByLogin("johnyy66");
        maybeUser.ifPresent(userDto -> {
            List<UserTrackOrderDto> ordersByUserId = orderService.findOrdersByUserId(userDto.getId());
            assertThat(ordersByUserId).hasSize(1);
        });
    }

    @Test
    @DisplayName("find orders by user email test case")
    void findOrdersByUserEmail() {
        Optional<UserDto> maybeUser = userService.findUserByLogin("johnyy66");
        maybeUser.ifPresent(userDto -> {
            List<UserTrackOrderDto> ordersByUserId = orderService
                    .findOrdersByUserEmail(userDto.getEmail(), 20, 0);
            assertThat(ordersByUserId).hasSize(1);
        });
    }

    @Test
    @DisplayName("find orders by track id")
    void findOrdersByTrackId() {
        Optional<TrackDto> maybeTrack = trackService.findTrackByTitle("Jail");
        maybeTrack.ifPresent(trackDto -> {
            List<UserTrackOrderDto> ordersByUserId = orderService.findOrdersByTrackId(trackDto.getId());
            assertThat(ordersByUserId).hasSize(1);
        });
    }
}