package com.ordjoy.web.controller;

import com.ordjoy.model.dto.TrackDto;
import com.ordjoy.model.dto.UserDto;
import com.ordjoy.model.dto.UserTrackOrderDto;
import com.ordjoy.model.entity.order.OrderStatus;
import com.ordjoy.model.service.OrderService;
import com.ordjoy.model.service.TrackService;
import com.ordjoy.model.util.LoggingUtils;
import com.ordjoy.web.util.AttributeUtils;
import com.ordjoy.web.util.PageUtils;
import com.ordjoy.web.util.UrlPathUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@SessionAttributes(AttributeUtils.SESSION_ORDER)
public class OrderController {

    private final OrderService orderService;
    private final TrackService trackService;

    @Autowired
    public OrderController(OrderService orderService, TrackService trackService) {
        this.orderService = orderService;
        this.trackService = trackService;
    }

    /**
     * Returns html page with all active orders
     *
     * @param limit  for UI pagination
     * @param offset for UI pagination
     * @return html page with all active orders
     * @see Model
     */
    @GetMapping("/admin/order/all")
    public String getAllOrders(
            @RequestParam(value = UrlPathUtils.LIMIT_PARAM) int limit,
            @RequestParam(value = UrlPathUtils.OFFSET_PARAM) int offset, Model model) {
        List<UserTrackOrderDto> orders = orderService.list(limit, offset);
        Long pages = orderService.getAllPages();
        model.addAttribute(AttributeUtils.PAGES, pages);
        model.addAttribute(AttributeUtils.ORDERS, orders);
        return PageUtils.ORDERS_PAGE;
    }

    /**
     * Returns html page with all active orders
     * with some UserDto predicate that they have
     *
     * @param userDto UserDto from the Session
     * @param limit   for UI pagination
     * @param offset  for UI pagination
     * @return html page with all active orders with some UserDto predicate
     * @see SessionAttribute
     * @see Model
     */
    @GetMapping("/user/order/all")
    public String getUserOrders(
            @SessionAttribute(AttributeUtils.SESSION_USER) UserDto userDto,
            @RequestParam(value = UrlPathUtils.LIMIT_PARAM) int limit,
            @RequestParam(value = UrlPathUtils.OFFSET_PARAM) int offset,
            Model model) {
        List<UserTrackOrderDto> userOrders = orderService
                .findOrdersByUserLogin(userDto.getLogin(), limit, offset);
        Long pages = orderService.getOrderWithUserLoginPredicatePages(userDto.getLogin());
        model.addAttribute(AttributeUtils.PAGES, pages);
        model.addAttribute(AttributeUtils.ORDERS, userOrders);
        return PageUtils.USER_ORDERS;
    }

    /**
     * Returns html page with all active orders
     * with cancelled status
     *
     * @param limit  for UI pagination
     * @param offset for UI pagination
     * @return html page with all active orders with cancelled status
     * @see Model
     */
    @GetMapping("/admin/order/cancelled")
    public String getCancelledOrders(
            @RequestParam(value = UrlPathUtils.LIMIT_PARAM) int limit,
            @RequestParam(value = UrlPathUtils.OFFSET_PARAM) int offset,
            Model model) {
        List<UserTrackOrderDto> cancelledOrders = orderService
                .findOrdersByStatus(OrderStatus.CANCELLED, limit, offset);
        Long pages = orderService.getOrderWithStatusPredicatePages(OrderStatus.CANCELLED);
        model.addAttribute(AttributeUtils.PAGES, pages);
        model.addAttribute(AttributeUtils.ORDERS, cancelledOrders);
        return PageUtils.CANCELLED_ORDERS_PAGE;
    }

    /**
     * Returns page that represents form to add UserTrackOrderDto
     *
     * @return page that represents form to add UserTrackOrderDto
     * @see Model
     */
    @GetMapping("/user/order/make-order")
    public String makeOrderPage(Model model) {
        model.addAttribute(AttributeUtils.ORDER, UserTrackOrderDto.builder().build());
        return PageUtils.CREATE_ORDER_FORM;
    }

    /**
     * Saves order and put it into session
     *
     * @param userDto  UserDto from the session
     * @param orderDto UserTrackOrderDto order from UI form
     * @return redirect to user orders page
     * @see Model
     */
    @PostMapping("/user/order/make-order")
    public String makeOrder(
            @SessionAttribute(AttributeUtils.SESSION_USER) UserDto userDto,
            UserTrackOrderDto orderDto, Model model) {
        String trackTitle = orderDto.getTrack().getTitle();
        Optional<TrackDto> maybeTrack = trackService.findTrackByTitle(trackTitle);
        if (maybeTrack.isPresent()) {
            TrackDto trackDto = maybeTrack.get();
            orderDto.setTrack(trackDto);
            orderDto.setUser(userDto);
            UserTrackOrderDto savedOrder = orderService.save(orderDto);
            log.debug(LoggingUtils.ORDER_WAS_CREATED_IN_CONTROLLER, savedOrder);
            BigDecimal priceAfterDiscount = orderService.calculateOrderPrice(savedOrder.getId());
            log.debug(LoggingUtils.ORDER_PRICE_WAS_CALCULATED_IN_CONTROLLER, priceAfterDiscount);
            savedOrder.setPrice(priceAfterDiscount);
            orderService.update(savedOrder);
            log.debug(LoggingUtils.ORDER_WAS_UPDATED_IN_CONTROLLER, savedOrder);
            model.addAttribute(AttributeUtils.SESSION_ORDER, savedOrder);
            return UrlPathUtils.REDIRECT_USER_ORDERS_PAGE;
        } else {
            return UrlPathUtils.REDIRECT_MAKE_ORDER;
        }
    }

    /**
     * @param orderId UserTrackOrderDto Identifier
     * @return html page that represents order with some info
     * @see Model
     */
    @GetMapping("/admin/order/{id}")
    public String getOrder(
            @PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long orderId, Model model) {
        Optional<UserTrackOrderDto> maybeOrder = orderService.findById(orderId);
        if (maybeOrder.isPresent()) {
            UserTrackOrderDto order = maybeOrder.get();
            model.addAttribute(AttributeUtils.REQUEST_ORDER, order);
            return PageUtils.ORDER_MAIN_PAGE;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Returns html page with all active orders
     * with some UserDto login predicate that they have
     *
     * @param login  UserDto login
     * @param limit  for UI pagination
     * @param offset for UI pagination
     * @return html page with all active orders with some UserDto login predicate
     * @see Model
     */
    @GetMapping("/admin/order/account")
    public String getOrdersByUserLogin(
            @RequestParam(value = UrlPathUtils.LIMIT_PARAM) int limit,
            @RequestParam(value = UrlPathUtils.OFFSET_PARAM) int offset,
            @RequestParam(value = UrlPathUtils.USERNAME_PARAM) String login, Model model) {
        List<UserTrackOrderDto> ordersByUserLogin = orderService
                .findOrdersByUserLogin(login, limit, offset);
        Long pages = orderService.getOrderWithUserLoginPredicatePages(login);
        for (UserTrackOrderDto userTrackOrderDto : ordersByUserLogin) {
            model.addAttribute(AttributeUtils.ORDER, userTrackOrderDto);
        }
        model.addAttribute(AttributeUtils.PAGES, pages);
        model.addAttribute(AttributeUtils.ORDERS, ordersByUserLogin);
        return PageUtils.ADMIN_USER_ORDERS_PAGE;
    }

    /**
     * Returns html page with all active orders
     * with some TrackDto title predicate that they have
     *
     * @param trackTitle Track title
     * @param limit      for UI pagination
     * @param offset     for UI pagination
     * @return html page with all active orders with some TrackDto title predicate
     * @see SessionAttribute
     * @see Model
     */
    @GetMapping("/admin/order/track")
    public String getOrdersByTrackTitle(
            @RequestParam(value = UrlPathUtils.LIMIT_PARAM) int limit,
            @RequestParam(value = UrlPathUtils.OFFSET_PARAM) int offset,
            @RequestParam(UrlPathUtils.TITLE_PARAM) String trackTitle, Model model) {
        List<UserTrackOrderDto> ordersByTrackTitle = orderService
                .findOrdersByTrackTitle(trackTitle, limit, offset);
        Long pages = orderService.getOrderWithTrackTitlePredicatePages(trackTitle);
        for (UserTrackOrderDto userTrackOrderDto : ordersByTrackTitle) {
            model.addAttribute(AttributeUtils.ORDER, userTrackOrderDto);
        }
        model.addAttribute(AttributeUtils.PAGES, pages);
        model.addAttribute(AttributeUtils.ORDERS, ordersByTrackTitle);
        return PageUtils.TRACK_ORDERS_PAGE;
    }

    /**
     * Sets status cancelled to UserTrackOrderDto by its id
     *
     * @param id UserTrackOrderDto Identifier
     * @return custom html page with some UserTrackOrderDto info
     */
    @GetMapping("/admin/order/cancel/{id}")
    public String cancelOrder(@PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long id) {
        orderService.updateOrderStatus(OrderStatus.CANCELLED, id);
        log.debug(LoggingUtils.ORDER_STATUS_WAS_UPDATED_IN_CONTROLLER, OrderStatus.CANCELLED, id);
        return UrlPathUtils.REDIRECT_ORDER_MAIN_PAGE + id;
    }

    /**
     * Sets status completed to UserTrackOrderDto by its id
     *
     * @param id UserTrackOrderDto Identifier
     * @return custom html page with some UserTrackOrderDto info
     */
    @GetMapping("/admin/order/complete/{id}")
    public String complete(@PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long id) {
        orderService.updateOrderStatus(OrderStatus.COMPLETED, id);
        log.debug(LoggingUtils.ORDER_STATUS_WAS_UPDATED_IN_CONTROLLER, OrderStatus.COMPLETED, id);
        return UrlPathUtils.REDIRECT_ORDER_MAIN_PAGE + id;
    }

    /**
     * Deletes (sets NOT ACTIVE) UserTrackOrderDto
     *
     * @param orderId UserTrackOrderDto Identifier
     * @return html page that represents all active orders
     */
    @GetMapping("/admin/order/{id}/remove")
    public String deleteOrder(
            @PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long orderId,
            SessionStatus status) {
        orderService.delete(orderId);
        status.setComplete();
        log.debug(LoggingUtils.ORDER_WAS_DELETED_IN_CONTROLLER, orderId);
        return UrlPathUtils.REDIRECT_ORDERS_PAGE_WITH_DEFAULT_LIMIT_OFFSET;
    }
}