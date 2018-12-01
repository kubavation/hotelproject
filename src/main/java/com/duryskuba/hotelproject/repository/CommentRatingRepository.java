package com.duryskuba.hotelproject.repository;

import com.duryskuba.hotelproject.model.CommentRating;
import com.duryskuba.hotelproject.model.CommentRatingPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false) //todo zmien w tamtyn na placeRatingPk?
public interface CommentRatingRepository extends JpaRepository<CommentRating, Long> {

    @Query("select r.rating from CommentRating r where r.id.commentId = :commentId")
    List<Integer> getRatingOfComment(@Param("commentId") Long commentId);

    @Query("select count(*) from CommentRating r where r.id.commentId = :commentId and r.id.personId = :personId")
    int ifRatingExists(@Param("commentId") Long commentId, @Param("personId") Long personId);

    @Query("select r from CommentRating r where r.id.commentId = :commentId and r.id.personId = :personId")
    CommentRating findByPk(@Param("commentId") Long commentId, @Param("personId") Long id);
}
