package com.ordjoy.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class ReviewDto implements Serializable {

    protected Long id;
    protected String reviewText;
    protected UserDto user;
}