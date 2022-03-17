package com.ordjoy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPersonalInfo {

    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String cardNumber;
    private BigDecimal accountBalance;
    private Integer discountPercentageLevel;
}