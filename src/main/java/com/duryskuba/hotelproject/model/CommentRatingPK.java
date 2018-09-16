package com.duryskuba.hotelproject.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class CommentRatingPK implements Serializable {

    @NotNull
    @Column(name = "COMMENT_ID")
    private Long commentId;

    @NotNull
    @Column(name = "PERSON_ID")
    private Long personId;

    public CommentRatingPK(Long commentId, Long personId) {
        this.commentId = commentId;
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentRatingPK that = (CommentRatingPK) o;
        return Objects.equals(commentId, that.commentId) &&
                Objects.equals(personId, that.personId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentId, personId);
    }
}
