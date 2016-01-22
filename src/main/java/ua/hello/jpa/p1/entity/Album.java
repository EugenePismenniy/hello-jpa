package ua.hello.jpa.p1.entity;

import javax.persistence.*;
import java.util.List;

/**
 * @author dn100985pev on 22.01.16 15:11.
 */
@Entity
public class Album {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long albumId;

	private String title;

	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> tracks;

	public Album() {
	}

	public Long getAlbumId() {
		return albumId;
	}

	public void setAlbumId(Long albumId) {
		this.albumId = albumId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getTracks() {
		return tracks;
	}

	public void setTracks(List<String> tracks) {
		this.tracks = tracks;
	}
}
