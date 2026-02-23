package base.repository;

import base.entity.Track;
import java.util.List;

public interface TrackRepository {
    List<Track> findAllTracks();
    void save(Track track);
    Track findByTitle(String title);
    boolean deleteById(int id);
    boolean exists(Track track);
}