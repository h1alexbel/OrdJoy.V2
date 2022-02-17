package com.ordjoy.database.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "user_account", schema = "user_storage")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "login", length = 128, unique = true, nullable = false)
    private String login;
    @Column(name = "email", length = 64, unique = true, nullable = false)
    private String email;
    @Column(name = "password", length = 128, nullable = false)
    private String password;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "role", length = 16, nullable = false)
    private Role role;
    @Embedded
    private UserData userData;
}