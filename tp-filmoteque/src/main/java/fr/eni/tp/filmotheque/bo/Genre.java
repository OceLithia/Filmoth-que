package fr.eni.tp.filmotheque.bo;

import java.util.Objects;

public class Genre {

	private long id;
	private String titre;

	public Genre() {
	}

	public Genre(String titre) {
		this.titre = titre;
	}

	public Genre(long id, String titre) {
		this.id = id;
		this.titre = titre;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Genre other = (Genre) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Genre [id=" + id + ", titre=" + titre + "]";
	}

}
