package base.entity;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Track {

    private int id;
    private String title;
    private String genre;
    private int duration ;// seconds
    private String albumTitle;
    private Set<Artist> artist;

    public Track() { this.artist = new HashSet<>(); }

    public Track(int id, String title, String genre, int duration, String albumTitle, Set<Artist> artist) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.albumTitle = albumTitle;
        this.artist = (artist != null ) ? artist : new HashSet<>();
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String getGenre() {return genre;}

    public void setGenre(String genre) {this.genre = genre;}

    public int getDuration() {return duration;}

    public void setDuration(int duration) {this.duration = duration;}

    public String getAlbumTitle() {return albumTitle;}

    public void setAlbumTitle(String albumTitle) {this.albumTitle = albumTitle;}

    public Set<Artist> getArtist() {return artist;}

    public void setArtist(Set<Artist> artist) {this.artist = artist;}

    public void addArtist(Artist artist){
        if(artist == null ) return;
        this.artist.add(artist);
    }

    public void removeArtist(Artist artist){
        if(artist == null ) return;
        this.artist.remove(artist);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Track)) return false;
        Track track = (Track) o;
        return id == track.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
