package com.ordjoy.database.util;

import com.ordjoy.database.model.order.OrderStatus;
import com.ordjoy.database.model.order.UserTrackOrder;
import com.ordjoy.database.model.review.AlbumReview;
import com.ordjoy.database.model.review.MixReview;
import com.ordjoy.database.model.review.TrackReview;
import com.ordjoy.database.model.track.Album;
import com.ordjoy.database.model.track.Mix;
import com.ordjoy.database.model.track.Track;
import com.ordjoy.database.model.user.Role;
import com.ordjoy.database.model.user.User;
import com.ordjoy.database.model.user.UserData;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class TestDataImporter {

    public static void importTestData(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            User user = User.builder()
                    .login("abialiauski.dev")
                    .password("12312561")
                    .email("alex@gmail.com")
                    .userData(UserData.builder()
                            .amount(new BigDecimal(10))
                            .birthDate(LocalDate.of(1999, 9, 11))
                            .firstName("Alexey")
                            .lastName("Bialiauski")
                            .build())
                    .role(Role.ADMIN)
                    .build();
            saveUser(session, user);

            User ivanIvanovUser = User.builder()
                    .login("abc.dev")
                    .password("12312561")
                    .email("abc@gmail.com")
                    .userData(UserData.builder()
                            .amount(new BigDecimal(10))
                            .birthDate(LocalDate.of(1999, 9, 11))
                            .firstName("Ivan")
                            .lastName("Ivanov")
                            .build())
                    .role(Role.USER)
                    .build();
            saveUser(session, ivanIvanovUser);

            Album beerbongsAndBentleysAlbum = Album.builder()
                    .title("Beerbongs & Bentleys")
                    .build();

            saveAlbum(session, beerbongsAndBentleysAlbum);

            Track track = Track.builder()
                    .title("Better now")
                    .url("https://www.youtube.com/watch?v=nqggm9o2Z9k")
                    .album(beerbongsAndBentleysAlbum)
                    .build();
            saveTrack(session, track);

            Track anotherTrack = Track.builder()
                    .title("Paranoid")
                    .url("https://www.youtube.com/watch?v=Er-Jko8nD3c")
                    .album(beerbongsAndBentleysAlbum)
                    .build();
            saveTrack(session, anotherTrack);

            Mix mix = Mix.builder()
                    .title("Best of Metalica")
                    .description("Metalica hits!")
                    .build();
            saveMix(session, mix);

            AlbumReview goodReview = AlbumReview.builder()
                    .album(beerbongsAndBentleysAlbum)
                    .reviewText("Nice! i love this")
                    .user(user)
                    .build();
            saveAlbumReview(session, goodReview);

            AlbumReview badReview = AlbumReview.builder()
                    .album(beerbongsAndBentleysAlbum)
                    .user(user)
                    .reviewText("Bad! i dont like this")
                    .build();
            saveAlbumReview(session, badReview);

            TrackReview trackReview = TrackReview.builder()
                    .track(track)
                    .user(user)
                    .reviewText("Awesome track!")
                    .build();
            saveTrackReview(session, trackReview);

            MixReview mixReview = MixReview.builder()
                    .user(user)
                    .mix(mix)
                    .reviewText("Good enough")
                    .build();
            saveMixReview(session, mixReview);

            UserTrackOrder order = UserTrackOrder.builder()
                    .user(user)
                    .track(anotherTrack)
                    .price(new BigDecimal(200))
                    .status(OrderStatus.ACCEPTED)
                    .build();
            saveOrder(session, order);
        }
    }

    private static void saveUser(Session session, User user) {
        session.save(user);
    }

    private static void saveAlbum(Session session, Album album) {
        session.save(album);
    }

    private static void saveTrack(Session session, Track track) {
        session.save(track);
    }

    private static void saveMix(Session session, Mix mix) {
        session.save(mix);
    }

    private static void saveOrder(Session session, UserTrackOrder order) {
        session.save(order);
    }

    private static void saveAlbumReview(Session session, AlbumReview albumReview) {
        session.save(albumReview);
    }

    private static void saveTrackReview(Session session, TrackReview trackReview) {
        session.save(trackReview);
    }

    private static void saveMixReview(Session session, MixReview mixReview) {
        session.save(mixReview);
    }
}