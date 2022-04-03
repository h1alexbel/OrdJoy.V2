package com.ordjoy.model.util;

import com.ordjoy.model.entity.EntityState;
import com.ordjoy.model.entity.order.OrderStatus;
import com.ordjoy.model.entity.order.UserTrackOrder;
import com.ordjoy.model.entity.review.AlbumReview;
import com.ordjoy.model.entity.review.MixReview;
import com.ordjoy.model.entity.review.TrackReview;
import com.ordjoy.model.entity.track.Album;
import com.ordjoy.model.entity.track.Mix;
import com.ordjoy.model.entity.track.Track;
import com.ordjoy.model.entity.user.Role;
import com.ordjoy.model.entity.user.User;
import com.ordjoy.model.entity.user.UserData;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Component
public class TestDataImporter {

    private final SessionFactory sessionFactory;

    @Autowired
    public TestDataImporter(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void importTestData() {
        Session session = sessionFactory.getCurrentSession();

        User johnDoe = saveUser(session, "johndow@gmail.com", "johnyy66",
                "b1uoghsio", Role.USER, "John", "Doe",
                LocalDate.of(2001, 2, 1), new BigDecimal(15),
                0);

        User alex = saveUser(session, "alex@gmail.com", "alex_0921",
                "1231bkjg", Role.USER, "Alex", "Stag",
                LocalDate.of(1980, 9, 12), new BigDecimal(0),
                100);

        User dogCat = saveUser(session, "dogcat123@gmail.com", "dogCat",
                "guihantw151", Role.DBA, "Dog", "Cat",
                LocalDate.of(2000, 2, 10), new BigDecimal(0),
                0);

        User admin77 = saveUser(session, "mainAdmin@gmail.com", "admin77",
                "10-85bnjkfs", Role.ADMIN, "Admin", "Admin",
                LocalDate.of(1998, 2, 1), new BigDecimal(0),
                0);

        Album dondaAlbum = saveAlbum(session, "Kanye West - Donda");
        Album blackAlbum = saveAlbum(session, "Metalica - Black Album");
        Album beerbongsAndBentleysAlbum = saveAlbum(session, "Post Malone - Beerbongs & Bentleys");

        Track praiseGod = saveTrack(session,
                "https://www.youtube.com/watch?v=9sJZOGxRxwM",
                "Praise God", dondaAlbum);
        dondaAlbum.addTrackToAlbum(praiseGod);

        Track jail = saveTrack(session,
                "https://www.youtube.com/watch?v=IviYsgJXG5k",
                "Jail", dondaAlbum);
        dondaAlbum.addTrackToAlbum(jail);

        Track nothingElseMatters = saveTrack(session,
                "https://www.youtube.com/watch?v=tAGnKpE4NCI",
                "Nothing Else Matters", blackAlbum);
        blackAlbum.addTrackToAlbum(nothingElseMatters);

        Track unforgiven = saveTrack(session,
                "https://www.youtube.com/watch?v=Ckom3gf57Yw",
                "Unforgiven", blackAlbum);
        blackAlbum.addTrackToAlbum(unforgiven);

        Track betterNow = saveTrack(session,
                "https://www.youtube.com/watch?v=JdkhJhtxFl4",
                "Better now", beerbongsAndBentleysAlbum);
        beerbongsAndBentleysAlbum.addTrackToAlbum(betterNow);

        Track paranoid = saveTrack(session,
                "https://www.youtube.com/watch?v=LOqrbkMyCoo",
                "Paranoid", beerbongsAndBentleysAlbum);
        beerbongsAndBentleysAlbum.addTrackToAlbum(paranoid);

        Mix hipHopMix = saveMix(session, "Hip-Hop", "Best of hip-hop!");
        Mix travisScottMix = saveMix(session, "Travis scott hits", "Best of Travis scott");

        UserTrackOrder alexJailOrder = saveOrder(session,
                new BigDecimal(15), OrderStatus.ACCEPTED, jail, alex);
        alex.addOrderToUser(alexJailOrder);

        UserTrackOrder johnDoeBetterNowOrder = saveOrder(session,
                new BigDecimal(10), OrderStatus.ACCEPTED, betterNow, johnDoe);
        johnDoe.addOrderToUser(johnDoeBetterNowOrder);

        UserTrackOrder alexPraiseGodOrder = saveOrder(session,
                new BigDecimal(5), OrderStatus.ACCEPTED, praiseGod, alex);
        alex.addOrderToUser(alexPraiseGodOrder);

        saveAlbumReview(session, dondaAlbum, alex,
                "Good Album by YE!");
        saveAlbumReview(session, blackAlbum, alex,
                "The classic one.");
        saveAlbumReview(session,
                beerbongsAndBentleysAlbum, johnDoe,
                "Good enough in 2022");

        saveAlbumReview(session, dondaAlbum, johnDoe,
                "Not fun of YE, but nice, 7/10");

        saveTrackReview(session, paranoid, johnDoe, "Best song ever");
        saveTrackReview(session, nothingElseMatters, admin77, "My fav!");

        saveMixReview(session, travisScottMix, alex,
                "To be honest not the best songs pick. This artist has more crutial songs");
        saveMixReview(session, hipHopMix, johnDoe, "I love this mix.");

        Mix mix = saveMix(session, "Jazz hits", "Best of jazz");
        Album album = saveAlbum(session, "Jazz 2022");

        Track track = saveTrack(session,
                "https://www.youtube.com/watch?v=neV3EPgvZ3g",
                "Relaxing jazz", album);
        mix.addTrack(track);
    }

    private User saveUser(Session session,
                          String email,
                          String login,
                          String password,
                          Role role,
                          String firstName,
                          String lastName,
                          LocalDate birthDate,
                          BigDecimal balance,
                          Integer discountPercentageLevel) {
        User user = User.builder()
                .email(email)
                .login(login)
                .password(password)
                .role(role)
                .userData(UserData.builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .accountBalance(balance)
                        .discountPercentageLevel(discountPercentageLevel)
                        .birthDate(birthDate)
                        .build())
                .build();
        user.setEntityState(EntityState.ACTIVE);
        session.save(user);
        return user;
    }

    public void cleanTestData() {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from User ").executeUpdate();
        session.createQuery("delete from UserTrackOrder ").executeUpdate();
        session.createQuery("delete from Mix ").executeUpdate();
        session.createQuery("delete from Album ").executeUpdate();
        session.createQuery("delete from Track ").executeUpdate();
        session.createQuery("delete from AlbumReview ").executeUpdate();
        session.createQuery("delete from TrackReview ").executeUpdate();
        session.createQuery("delete from MixReview ").executeUpdate();
    }

    private Album saveAlbum(Session session, String title) {
        Album album = Album.builder()
                .title(title)
                .build();
        album.setEntityState(EntityState.ACTIVE);
        session.save(album);
        return album;
    }

    private Track saveTrack(Session session, String url, String title, Album album) {
        Track track = Track.builder()
                .url(url)
                .title(title)
                .album(album)
                .build();
        track.setEntityState(EntityState.ACTIVE);
        session.save(track);
        return track;
    }

    private Mix saveMix(Session session, String title, String description) {
        Mix mix = Mix.builder()
                .title(title)
                .description(description)
                .build();
        mix.setEntityState(EntityState.ACTIVE);
        session.save(mix);
        return mix;
    }

    private UserTrackOrder saveOrder(Session session,
                                            BigDecimal price,
                                            OrderStatus status,
                                            Track track,
                                            User user) {
        UserTrackOrder order = UserTrackOrder.builder()
                .price(price)
                .status(status)
                .user(user)
                .track(track)
                .build();
        order.setEntityState(EntityState.ACTIVE);
        session.save(order);
        return order;
    }

    private AlbumReview saveAlbumReview(Session session, Album album, User user, String reviewText) {
        AlbumReview albumReview = AlbumReview.builder()
                .album(album)
                .user(user)
                .reviewText(reviewText)
                .build();
        albumReview.setEntityState(EntityState.ACTIVE);
        session.save(albumReview);
        return albumReview;
    }

    private TrackReview saveTrackReview(Session session, Track track, User user, String reviewText) {
        TrackReview trackReview = TrackReview.builder()
                .track(track)
                .user(user)
                .reviewText(reviewText)
                .build();
        trackReview.setEntityState(EntityState.ACTIVE);
        session.save(trackReview);
        return trackReview;
    }

    private MixReview saveMixReview(Session session, Mix mix, User user, String reviewText) {
        MixReview mixReview = MixReview.builder()
                .mix(mix)
                .user(user)
                .reviewText(reviewText)
                .build();
        mixReview.setEntityState(EntityState.ACTIVE);
        session.save(mixReview);
        return mixReview;
    }
}