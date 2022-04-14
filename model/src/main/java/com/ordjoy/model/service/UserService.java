package com.ordjoy.model.service;

import com.ordjoy.model.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @see UserDetailsService
 * @see org.springframework.security.core.userdetails.User
 * @see org.springframework.security.core.GrantedAuthority
 */
public interface UserService extends UserDetailsService, GenericCRUDService<UserDto, Long> {

    /**
     * Checks is UserDto has rights to register in service, must be 13yo+
     *
     * @param userDto UserDto to verify data
     * @return boolean value {@code true} if user has rights {@code false} if not
     */
    boolean isUserHasRightsToRegister(UserDto userDto);

    /**
     * Adds new UserDto with ADMIN Authority
     *
     * @param userDto admin to save
     * @return UserDto that represents admin with identifier
     */
    UserDto addNewAdmin(UserDto userDto);

    /**
     * Update UserDto's balance amount by subtracting order cost
     *
     * @param orderCost {@link com.ordjoy.model.dto.UserTrackOrderDto} cost
     * @param userId    UserDto Identifier account balance whom will be subtracted from actual balance
     */
    void subtractBalanceFromUser(BigDecimal orderCost, Long userId);

    /**
     * Updates UserDto's discount percentage level
     *
     * @param valueToSet new Discount percentage level that will be set
     * @param userId     UserDto Identifier discount percentage level whom will be updated
     */
    void updateDiscountPercentageLevel(Integer valueToSet, Long userId);

    /**
     * Updates UserDto's balance amount by adding new value of account balance
     *
     * @param amountToAdd Balance amount that be added
     * @param userId      UserDto Identifier balance whom will be updated
     */
    void updateUserBalanceAmount(BigDecimal amountToAdd, Long userId);

    /**
     * Finds UserDto from the DB by its email
     *
     * @param email unique User email
     * @return {@link Optional} of UserDto if present or empty {@link Optional}
     */
    Optional<UserDto> findUserByEmail(String email);

    /**
     * Gets actual user info from DB
     *
     * @param id UserDto Identifier
     * @return {@link Optional} of UserDto if present or empty {@link Optional}
     */
    Optional<UserDto> getSynchronizedUser(Long id);

    /**
     * Finds UserDto from the DB by its login
     *
     * @param login unique User login
     * @return {@link Optional} of UserDto if present or empty {@link Optional}
     */
    Optional<UserDto> findUserByLogin(String login);

    /**
     * Checks is UserDto email exists, because of UserDto email must be unique
     *
     * @param email unique UserDto email
     * @return boolean value {@code true} if exists {@code false} if not
     */
    boolean isEmailExists(String email);

    /**
     * Checks is UserDto login exists, because of UserDto login must be unique
     *
     * @param login unique UserDto login
     * @return boolean value {@code true} if exists {@code false} if not
     */
    boolean isLoginExists(String login);
}