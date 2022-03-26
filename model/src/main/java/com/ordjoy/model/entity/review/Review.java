package com.ordjoy.model.entity.review;

import com.ordjoy.model.entity.AuditableEntity;
import com.ordjoy.model.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "review", schema = "review_storage")
@Inheritance
@DiscriminatorColumn(name = "review_type")
@SQLDelete(sql = "UPDATE review_storage.review SET state = 'NOT_ACTIVE' WHERE id = ?",
        check = ResultCheckStyle.COUNT)
@Where(clause = "state = 'ACTIVE'")
public abstract class Review extends AuditableEntity<Long> {

    @Column(name = "review_text", length = 512, nullable = false)
    protected String reviewText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    protected User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Review review = (Review) o;
        return id != null && Objects.equals(id, review.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}