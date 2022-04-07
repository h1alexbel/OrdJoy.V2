package com.ordjoy.web.controller;

import com.ordjoy.model.dto.UserDto;
import com.ordjoy.model.dto.UserTrackOrderDto;
import com.ordjoy.model.entity.order.OrderStatus;
import com.ordjoy.model.service.OrderService;
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
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/order")
@SessionAttributes(AttributeUtils.SESSION_ORDER)
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/admin/all")
    public String getAllOrders(
            @RequestParam(value = UrlPathUtils.LIMIT_PARAM) int limit,
            @RequestParam(value = UrlPathUtils.OFFSET_PARAM) int offset, Model model) {
        List<UserTrackOrderDto> orders = orderService.list(limit, offset);
        model.addAttribute(AttributeUtils.ORDERS, orders);
        return PageUtils.ORDERS_PAGE;
    }

    @GetMapping("/user/all")
    public String getUserOrders(
            @SessionAttribute(AttributeUtils.SESSION_USER) UserDto userDto,
            Model model) {
        List<UserTrackOrderDto> userOrders = orderService.findOrdersByUserId(userDto.getId());
        model.addAttribute(AttributeUtils.ORDERS, userOrders);
        return PageUtils.USER_ORDERS;
    }

    @GetMapping("/admin/completed")
    public String getCompletedOrders(Model model) {
        List<UserTrackOrderDto> completedOrders = orderService
                .findOrdersByStatus(OrderStatus.COMPLETED);
        model.addAttribute(AttributeUtils.ORDERS, completedOrders);
        return PageUtils.ORDERS_PAGE;
    }

    @GetMapping("/admin/cancelled")
    public String getCancelledOrders(Model model) {
        List<UserTrackOrderDto> cancelledOrders = orderService
                .findOrdersByStatus(OrderStatus.CANCELLED);
        model.addAttribute(AttributeUtils.ORDERS, cancelledOrders);
        return PageUtils.ORDERS_PAGE;
    }

    @GetMapping("/admin/accepted")
    public String getAcceptedOrders(Model model) {
        List<UserTrackOrderDto> acceptedOrders = orderService
                .findOrdersByStatus(OrderStatus.ACCEPTED);
        model.addAttribute(AttributeUtils.ORDERS, acceptedOrders);
        return PageUtils.ORDERS_PAGE;
    }

    @GetMapping("/admin/inProgress")
    public String getInProgressOrders(Model model) {
        List<UserTrackOrderDto> inProgressOrders = orderService
                .findOrdersByStatus(OrderStatus.PROCESSING);
        model.addAttribute(AttributeUtils.ORDERS, inProgressOrders);
        return PageUtils.ORDERS_PAGE;
    }

    @GetMapping("/user/makeOrder")
    public String makeOrderPage() {
        return PageUtils.CREATE_ORDER_FORM;
    }

    @PostMapping("/user/makeOrder")
    public String makeOrder(UserTrackOrderDto orderDto, Model model) {
        UserTrackOrderDto savedOrder = orderService.save(orderDto);
        log.debug(LoggingUtils.ORDER_WAS_CREATED_IN_CONTROLLER, savedOrder);
        BigDecimal priceAfterDiscount = orderService.calculateOrderPrice(orderDto.getId());
        log.debug(LoggingUtils.ORDER_PRICE_WAS_CALCULATED_IN_CONTROLLER, priceAfterDiscount);
        savedOrder.setPrice(priceAfterDiscount);
        orderService.update(savedOrder);
        log.debug(LoggingUtils.ORDER_WAS_UPDATED_IN_CONTROLLER, savedOrder);
        model.addAttribute(AttributeUtils.SESSION_ORDER, savedOrder);
        log.debug(LoggingUtils.USER_BALANCE_WAS_SUBTRACTED_IN_CONTROLLER, savedOrder, savedOrder.getUser());
        return UrlPathUtils.REDIRECT_USER_ORDERS_PAGE;
    }

    @GetMapping("/admin/{id}")
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

    @GetMapping("/admin/account/{id}")
    public String getOrdersByUserId(
            @PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long userId, Model model) {
        List<UserTrackOrderDto> ordersByUserId = orderService.findOrdersByUserId(userId);
        model.addAttribute(AttributeUtils.ORDERS, ordersByUserId);
        return PageUtils.ORDERS_PAGE;
    }

    @GetMapping("/admin/account/")
    public String getOrdersByUserLogin(
            @RequestParam(value = UrlPathUtils.USERNAME_PARAM) String login, Model model) {
        List<UserTrackOrderDto> ordersByUserLogin = orderService.findOrdersByUserLogin(login);
        model.addAttribute(AttributeUtils.ORDERS, ordersByUserLogin);
        return PageUtils.ORDERS_PAGE;
    }

    @GetMapping("/admin/track/{id}")
    public String getOrdersByTrackId(
            @PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long trackId, Model model) {
        List<UserTrackOrderDto> ordersByTrackId = orderService.findOrdersByTrackId(trackId);
        model.addAttribute(AttributeUtils.ORDERS, ordersByTrackId);
        return PageUtils.ORDERS_PAGE;
    }

    @GetMapping("/admin/track/")
    public String getOrdersByTrackTitle(
            @RequestParam(UrlPathUtils.TITLE_PARAM) String trackTitle, Model model) {
        List<UserTrackOrderDto> ordersByTrackTitle = orderService.findOrdersByTrackTitle(trackTitle);
        model.addAttribute(AttributeUtils.ORDERS, ordersByTrackTitle);
        return PageUtils.ORDERS_PAGE;
    }

    @GetMapping("/admin/updateStatus/{id}")
    public String updateStatusForm(
            @PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long id,
            Model model) {
        Optional<UserTrackOrderDto> maybeOrder = orderService.findById(id);
        maybeOrder.ifPresent(orderDto -> model.addAttribute(AttributeUtils.REQUEST_ORDER, orderDto));
        return PageUtils.UPDATE_STATUS_FORM;
    }

    @PostMapping("/admin/updateStatus")
    public String updateOrderStatus(
            UserTrackOrderDto orderDto,
            OrderStatus status) {
        orderService.updateOrderStatus(status, orderDto.getId());
        log.debug(LoggingUtils.ORDER_STATUS_WAS_UPDATED_IN_CONTROLLER, status, orderDto);
        return UrlPathUtils.REDIRECT_ORDER_MAIN_PAGE + orderDto.getId();
    }

    @GetMapping("/admin/{id}/remove")
    public String deleteOrder(
            @PathVariable(UrlPathUtils.ID_PATH_VARIABLE) Long orderId,
            SessionStatus status) {
        orderService.deleteOrder(orderId);
        status.setComplete();
        log.debug(LoggingUtils.ORDER_WAS_DELETED_IN_CONTROLLER, orderId);
        return UrlPathUtils.REDIRECT_ORDERS_PAGE_WITH_DEFAULT_LIMIT_OFFSET;
    }
}