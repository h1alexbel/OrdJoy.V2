package com.ordjoy.database.repository.track;

import com.ordjoy.database.model.track.Mix;
import com.ordjoy.database.model.track.Track;
import com.ordjoy.database.repository.AbstractGenericCRUDRepository;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TrackRepositoryImpl extends AbstractGenericCRUDRepository<Track, Long>
        implements TrackRepository {

    @Override
    public void addTrackToMix(Track track, Mix mix) {
        Session session = sessionFactory.getCurrentSession();
        session.createNativeQuery("INSERT INTO audio_storage.mix_tracks(mix_id, track_id) VALUES " +
                               "((SELECT id FROM audio_storage.mix WHERE title = ?)," +
                               "(SELECT id FROM audio_storage.track WHERE title = ?))")
                .setParameter(1, mix.getTitle())
                .setParameter(2, track.getTitle())
                .executeUpdate();
    }

    @Override
    public Optional<Track> findByTitle(String title) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select t from Track t where t.title = :title",
                        Track.class)
                .setParameter("title", title)
                .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public List<Track> findTracksByAlbumId(Long albumId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select t from Track t join t.album a where a.id = :id",
                        Track.class)
                .setParameter("id", albumId)
                .getResultList();
    }

    @Override
    public List<Track> findTracksByAlbumName(String albumName) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select t from Track t join t.album a where a.title = :albumName",
                        Track.class)
                .setParameter("albumName", albumName)
                .getResultList();
    }
}