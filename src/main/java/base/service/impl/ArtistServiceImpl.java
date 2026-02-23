package base.service;

import base.entity.Artist;
import base.repository.impl.ArtistRepositoryImpl;

import java.util.List;

public class ArtistService {

    private final ArtistRepositoryImpl artistRepository;

    public ArtistService(ArtistRepositoryImpl artistRepository) {
        this.artistRepository = artistRepository;
    }

    public List<Artist> getAllArtists(){
        return artistRepository.findAllArtist();
    }

    public Artist createArtist(Artist artist){
        // Es obligatorio nombrarlo
        if (artist.getName()  == null || artist.getName().isBlank() ){
            throw new IllegalArgumentException("Artist name cannot be empty");
        }

        //  no nombres repetidos
        if (artistRepository.findByName(artist.getName()) != null) {
            throw new IllegalArgumentException("Artist already exists");
        }

        artistRepository.save(artist);
        return artist;
    }

    public Artist getArtistByName(String name) {
        Artist found = artistRepository.findByName(name);
        if (found == null) {
            throw new RuntimeException("Artist not found");
        }
        return found;
    }

    public void deleteArtist(int id) {
        boolean deleted = artistRepository.deleteById(id);
        if (!deleted) {
            throw new RuntimeException("Artist not found");
        }
    }
}
