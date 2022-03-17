package com.ordjoy.model.repository.mix;

import com.ordjoy.model.entity.review.MixReview;
import com.ordjoy.model.entity.track.Mix;
import com.ordjoy.model.entity.track.Track;
import com.ordjoy.model.repository.GenericCRUDRepository;

import java.util.List;
import java.util.Optional;

public interface MixRepository extends GenericCRUDRepository<Mix, Long> {

    Optional<Mix> findByTitle(String title);

    List<Track> findTracksByMixTitle(String mixTitle);

    List<MixReview> findMixReviewsByMixTitle(String mixTitle);

    List<MixReview> findMixReviewsByMixId(Long mixId);
}