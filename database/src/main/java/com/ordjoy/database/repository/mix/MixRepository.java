package com.ordjoy.database.repository.mix;

import com.ordjoy.database.model.review.MixReview;
import com.ordjoy.database.model.track.Mix;
import com.ordjoy.database.repository.GenericCRUDRepository;

import java.util.List;
import java.util.Optional;

public interface MixRepository extends GenericCRUDRepository<Mix, Long> {

    Optional<Mix> findByTitle(String title);

    List<MixReview> findMixReviewsByMixName(String mixName);

    List<MixReview> findMixReviewsByMixId(Long mixId);
}