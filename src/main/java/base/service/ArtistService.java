package base.service;


import base.entity.Artist;

import java.util.List;

public interface ArtistService {
    List<Artist> getAllArtists();
    void createArtist(Artist artist);
    Artist getArtistByName(String name );
    void deleteArtist(int id);


}
