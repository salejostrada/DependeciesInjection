package base.service.impl;

import base.entity.Track;
import base.repository.TrackRepository;
import base.service.TrackService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackServiceImpl implements TrackService {

    private final TrackRepository trackRepository;

    public TrackServiceImpl(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Override
    public List<Track> getAllTracks() {
        return trackRepository.findAllTracks();
    }

    @Override
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

    @Override
    public Track getTrackByTitle(String title) {
        Track found = trackRepository.findByTitle(title);
        if (found == null) {
            throw new RuntimeException("Track not found");
        }
        return found;
    }

    @Override
    public void deleteTrack(int id) {
        boolean deleted = trackRepository.deleteById(id);
        if (!deleted) {
            throw new RuntimeException("Track not found");
        }
    }
}