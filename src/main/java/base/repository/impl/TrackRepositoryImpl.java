package base.repository;

import java.util.ArrayList;
import java.util.List;

import base.entity.Track;

public class TrackRepository {

    private final List<Track> tracks = new ArrayList<>();

    // Obtener todos
    public List<Track> findAllTracks() {
        return new ArrayList<>(tracks);
    }

    // Crear / Update técnico: si existe mismo id, reemplaza
    public void save(Track track) {
        for (int i = 0; i < tracks.size(); i++) {
            if (tracks.get(i).getId() == track.getId()) {
                tracks.set(i, track);
                return;
            }
        }
        tracks.add(track);
    }

    // Buscar por título (equivalente a findByName en Artist)
    public Track findByTitle(String title) {
        for (Track t : tracks) {
            if (t.getTitle() != null && t.getTitle().equalsIgnoreCase(title)) {
                return t;
            }
        }
        return null;
    }


    // Eliminar por id
    public boolean deleteById(int id) {
        for (int i = 0; i < tracks.size(); i++) {
            if (tracks.get(i).getId() == id) {
                tracks.remove(i);
                return true;
            }
        }
        return false;
    }
}


