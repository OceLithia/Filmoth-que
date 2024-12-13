package fr.eni.tp.filmotheque.bo;

import java.util.Objects;

public class Avis {

	private long id;
	private int note;
	private String commentaire;
	private Membre membre;

	public Avis() {
	}

	public Avis(long id, int note, String commentaire, Membre membre) {
		this.id = id;
		this.note = note;
		this.commentaire = commentaire;
		this.membre = membre;
	}

	public Avis(int note, String commentaire, Membre membre) {
		this.note = note;
		this.commentaire = commentaire;
		this.membre = membre;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getNote() {
		return note;
	}

	public void setNote(int note) {
		this.note = note;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public Membre getMembre() {
		return membre;
	}

	public void setMembre(Membre membre) {
		this.membre = membre;
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
		Avis other = (Avis) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return String.format("Avis de %s [id=%s, note=%s, commentaire=%s]", membre, id, note, commentaire);
	}
	

}
