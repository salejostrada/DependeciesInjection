package base.service;

import base.entity.Track;

import java.util.List;

public interface TrackService {
    List<Track> getAllTracks();
    Track createTrack(Track track);
    Track getTrackByTitle(String title);
    void deleteTrack(int id);
}
