package base.service.impl;

import base.entity.Artist;
import base.repository.impl.ArtistRepositoryImpl;
import base.service.ArtistService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepositoryImpl artistRepository;

    public ArtistServiceImpl(ArtistRepositoryImpl artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override
    public List<Artist> getAllArtists(){
        return artistRepository.findAllArtist();
    }

    @Override
    public void createArtist(Artist artist){
        // Es obligatorio nombrarlo
        if (artist.getName()  == null || artist.getName().isBlank() ){
            throw new IllegalArgumentException("Artist name cannot be empty");
        }

        //  no nombres repetidos
        if (artistRepository.findByName(artist.getName()) != null) {
            throw new IllegalArgumentException("Artist already exists");
        }

        if(!artistRepository.exists(artist)) {
            artistRepository.save(artist);

        }
    }

    @Override
    public Artist getArtistByName(String name) {
        Artist found = artistRepository.findByName(name);
        if (found == null) {
            throw new RuntimeException("Artist not found");
        }
        return found;
    }

    @Override
    public void deleteArtist(int id) {
        boolean deleted = artistRepository.deleteById(id);
        if (!deleted) {
            throw new RuntimeException("Artist not found");
        }
    }


}
