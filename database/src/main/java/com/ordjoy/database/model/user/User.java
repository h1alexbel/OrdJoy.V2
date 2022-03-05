package com.ordjoy.database.model.user;

import com.ordjoy.database.model.AuditableEntity;
import com.ordjoy.database.model.BaseEntity;
import com.ordjoy.database.model.review.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "user_account", schema = "user_storage")
public class User extends AuditableEntity<Long> {

    @Column(length = 128, unique = true, nullable = false)
    private String login;

    @Column(length = 64, unique = true, nullable = false)
    private String email;

    @Column(length = 128, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 16, nullable = false)
    private Role role;

    @Embedded
    private UserData userData;

    @OneToMany(mappedBy = "user")
    @Builder.Default
    @ToString.Exclude
    private List<Review> reviews = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}