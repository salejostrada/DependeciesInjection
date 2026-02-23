package base.repository;

import base.entity.Artist ;
import java.util.ArrayList;
import java.util.List;

//Create Read Update Delete
public class ArtistRepositoryImpl {

    private final List<Artist> artists = new ArrayList<>() ;

    //Obtener artistas
    public List<Artist> findAllArtist(){ return new ArrayList<>(artists); }

    //


    //Crear artistas
    public void save(Artist artist) {
        // comportamiento “técnico” CRUD: si existe mismo id, reemplaza
        for (int i = 0; i < artists.size(); i++) {
            if (artists.get(i).getId() == artist.getId()) {
                artists.set(i, artist);
                return;
            }
        }
        artists.add(artist);
    }

    public Artist findByName(String name) {
        for (Artist a : artists) {
            if (a.getName() != null && a.getName().equalsIgnoreCase(name)) {
                return a;
            }
        }
        return null;
    }


    //delete
    public boolean deleteById(int id) {
        for (int i = 0; i < artists.size(); i++) {
            if (artists.get(i).getId() == id) {
                artists.remove(i);
                return true;
            }
        }
        return false;
    }
}
