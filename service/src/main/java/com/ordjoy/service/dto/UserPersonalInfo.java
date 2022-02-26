package com.ordjoy.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPersonalInfo {

    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Integer discountPercentageLevel;
}