package base.service;

import base.entity.Track;
import base.repository.impl.TrackRepositoryImpl;

import java.util.List;

public class TrackService {

    private final TrackRepositoryImpl trackRepository;

    public TrackService(TrackRepositoryImpl trackRepository) {
        this.trackRepository = trackRepository;
    }

    public List<Track> getAllTracks() {
        return trackRepository.findAllTracks();
    }

    public Track createTrack(Track track) {
        // Validaciones mínimas (negocio)
        if (track.getTitle() == null || track.getTitle().isBlank()) {
            throw new IllegalArgumentException("Track title cannot be empty");
        }

        // Evitar títulos repetidos (regla opcional, como hiciste con Artist)
        if (trackRepository.findByTitle(track.getTitle()) != null) {
            throw new IllegalArgumentException("Track already exists");
        }

        // (Opcional recomendado) duración no negativa
        if (track.getDuration() < 0) {
            throw new IllegalArgumentException("Track duration cannot be negative");
        }

        trackRepository.save(track);
        return track;
    }

    public Track getTrackByTitle(String title) {
        Track found = trackRepository.findByTitle(title);
        if (found == null) {
            throw new RuntimeException("Track not found");
        }
        return found;
    }

    public void deleteTrack(int id) {
        boolean deleted = trackRepository.deleteById(id);
        if (!deleted) {
            throw new RuntimeException("Track not found");
        }
    }
}