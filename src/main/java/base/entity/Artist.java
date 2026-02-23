package base.entity;
import java.util.Objects;
import java.util.Set;
import java.util.HashSet;

public class Artist {

    private int id;
    private String name;
    private String nationality;
    private Set<Track> tracks; //Relación muchos a muchos

    public Artist( ) {  this.tracks = new HashSet<>(); }

    public Artist(int id, String name, String nationality, Set<Track> tracks) {
        this.id = id;
        this.name = name;
        this.nationality = nationality;
        this.tracks = (tracks != null ) ? tracks : new HashSet<>() ;
    }

    public Set<Track> getTracks() {return tracks;}

    public void setTracks(Set<Track> tracks) {this.tracks = tracks;}

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getNationality() {return nationality;}

    public void setNationality(String nationality) {this.nationality = nationality;}

    public void addTrack(Track track){
        if(track == null ) return ;
        this.tracks.add(track);
    }

    public void removeTrack(Track track){
        if(track == null ) return ;
        this.tracks.remove(track);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Artist)) return false;
        Artist artist = (Artist) o;
        return id == artist.id;
    }

    //hashCode conectado solo al id
    @Override
    public int hashCode(){
        return Objects.hash(id);
    }


}
