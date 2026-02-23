package base.repository;

import base.entity.Artist;
import java.util.List;

public interface ArtistRepository {
    List<Artist> findAllArtist();
    void save(Artist artist);
    Artist findByName(String name);
    boolean deleteById(int id);
    Boolean exists(Artist artist);
}