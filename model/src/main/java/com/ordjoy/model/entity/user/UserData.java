package com.ordjoy.model.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Embeddable
public class UserData {

    @Column(name = "first_name", length = 64)
    private String firstName;

    @Column(name = "last_name", length = 64)
    private String lastName;

    @Column(name = "birth_date", length = 128, nullable = false)
    private LocalDate birthDate;

    @Column(name = "card_number", length = 32)
    private String cardNumber;

    @Column(name = "account_balance", nullable = false)
    private BigDecimal accountBalance;

    @Column(name = "discount_percentage_level", nullable = false)
    private Integer discountPercentageLevel;
}