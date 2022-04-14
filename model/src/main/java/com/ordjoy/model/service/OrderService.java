package com.ordjoy.model.service;

import com.ordjoy.model.dto.UserTrackOrderDto;
import com.ordjoy.model.entity.order.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService extends GenericCRUDService<UserTrackOrderDto, Long> {

    /**
     * Updates {@link UserTrackOrderDto} {@link OrderStatus}
     *
     * @param status new Status to set
     * @param id     UserTrackOrderDto Identifier
     */
    void updateOrderStatus(OrderStatus status, Long id);

    /**
     * Calculates order price by order id
     *
     * @param orderId UserTrackOrderDto Identifier
     * @return final price of UserTrackOrder with User's possible discounts
     */
    BigDecimal calculateOrderPrice(Long orderId);

    /**
     * Finds List of {@link UserTrackOrderDto} from DB by its price
     *
     * @param price UserTrackOrderDto price
     * @return List of UserTrackOrderDtos
     */
    List<UserTrackOrderDto> findOrdersByPrice(BigDecimal price);

    /**
     * Finds List of {@link UserTrackOrderDto} from DB by its price
     *
     * @param userId User Identifier
     * @return List of UserTrackOrderDtos
     */
    List<UserTrackOrderDto> findOrdersByUserId(Long userId);

    /**
     * Finds List of {@link UserTrackOrderDto} from DB by User's email
     *
     * @param email  unique User's email
     * @param limit  LIMIT for pagination
     * @param offset OFFSET for pagination
     * @return List of UserTrackOrderDtos
     */
    List<UserTrackOrderDto> findOrdersByUserEmail(String email, int limit, int offset);

    /**
     * Finds List of {@link UserTrackOrderDto} from DB by User's email
     *
     * @param login  unique User's login
     * @param limit  LIMIT for pagination
     * @param offset OFFSET for pagination
     * @return List of UserTrackOrderDtos
     */
    List<UserTrackOrderDto> findOrdersByUserLogin(String login, int limit, int offset);

    /**
     * Finds List of {@link UserTrackOrderDto} from DB by Track id
     *
     * @param trackId TrackDto's Identifier
     * @return List of UserTrackOrderDtos
     */
    List<UserTrackOrderDto> findOrdersByTrackId(Long trackId);

    /**
     * Finds List of {@link UserTrackOrderDto} from DB by Track title
     *
     * @param title  unique Track's title
     * @param limit  LIMIT for pagination
     * @param offset OFFSET for pagination
     * @return List of UserTrackOrderDtos
     */
    List<UserTrackOrderDto> findOrdersByTrackTitle(String title, int limit, int offset);

    /**
     * Finds List of {@link UserTrackOrderDto} from DB by its status
     *
     * @param status Order's status
     * @param limit  LIMIT for pagination
     * @param offset OFFSET for pagination
     * @return List of UserTrackOrderDtos
     */
    List<UserTrackOrderDto> findOrdersByStatus(OrderStatus status, int limit, int offset);

    /**
     * Finds count of pages that store Orders from DB which have User's email like in param
     *
     * @param email unique User email
     * @return Long value of pages count
     */
    Long getOrderWithUserEmailPredicatePages(String email);

    /**
     * Finds count of pages that store Orders from DB which have User's login like in param
     *
     * @param login unique User login
     * @return Long value of pages count
     */
    Long getOrderWithUserLoginPredicatePages(String login);

    /**
     * Finds count of pages that store Orders from DB which have Track's title like in param
     *
     * @param trackTitle unique Track title
     * @return Long value of pages count
     */
    Long getOrderWithTrackTitlePredicatePages(String trackTitle);

    /**
     * Finds count of pages that store Orders from DB which have status like in param
     *
     * @param status UserTrackOrderDto status
     * @return Long value of pages count
     */
    Long getOrderWithStatusPredicatePages(OrderStatus status);
}