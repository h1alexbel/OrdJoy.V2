package com.ordjoy.model.entity.user;

import com.ordjoy.model.entity.AuditableEntity;
import com.ordjoy.model.entity.order.UserTrackOrder;
import com.ordjoy.model.entity.review.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Loader;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQuery;
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
@SQLDelete(sql = "UPDATE user_storage.user_account SET state = 'NOT_ACTIVE' WHERE id = ?",
        check = ResultCheckStyle.COUNT)
@Where(clause = "state = 'ACTIVE'")
@Loader(namedQuery = "exclude_not_active_users")
@NamedQuery(name = "exclude_not_active_users",
        query = "select u from User u where u.entityState = 'ACTIVE'")
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

    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @Builder.Default
    @ToString.Exclude
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @Builder.Default
    @ToString.Exclude
    private List<UserTrackOrder> orders = new ArrayList<>();

    public void addOrderToUser(UserTrackOrder order) {
        orders.add(order);
        order.setUser(this);
    }

    public void addReviewToUser(Review review) {
        reviews.add(review);
        review.setUser(this);
    }

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