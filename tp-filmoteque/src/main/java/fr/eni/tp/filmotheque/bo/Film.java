package fr.eni.tp.filmotheque.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Film {

	
	private long id;
	@NotBlank(message = "Le titre est obligatoire.")
	@Size(max=250, message= "Le titre doit contenir maximum 250 caractères.")
	private String titre;
	@NotNull(message = "L'année est obligatoire.")
	@Min(1900)
	private int annee;
	@NotNull(message = "La durée est obligatoire.")
	@Min(1)
	private int duree;
	@NotBlank(message = "Le synopsis est obligatoire.")
	@Size(min=20,max=250)
	private String synopsis;
	@NotNull(message = "Le titre est obligatoire.")
	private Participant realisateur;
	private List<Participant> acteurs = new ArrayList<>();
	@NotNull(message = "Le titre est obligatoire.")
	private Genre genre;
	private List<Avis> avis = new ArrayList<>();

	public List<Participant> getActeurs() {
		return acteurs;
	}

	public void setActeurs(List<Participant> acteurs) {
		this.acteurs = acteurs;
	}

	public Film() {
	}

	public Film(long id, String titre, int annee, int duree, String synopsis) {
		this();
		this.id = (int) id;
		this.titre = titre;
		this.annee = annee;
		this.duree = duree;
		this.synopsis = synopsis;
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

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public Participant getRealisateur() {
		return realisateur;
	}

	public void setRealisateur(Participant realisateur) {
		this.realisateur = realisateur;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public Genre getGenre() {
		return genre;
	}

	public List<Avis> getAvis() {
		return avis;
	}

	public void setAvis(List<Avis> avis) {
		this.avis = avis;
	}

	@Override
	public String toString() {
		return "Film [id=" + id + ", titre=" + titre + ", annee=" + annee + ", duree=" + duree + ", synopsis="
				+ synopsis + ", realisateur=" + realisateur + ", acteurs=" + acteurs + ", genre=" + genre + ", avis="
				+ avis + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(acteurs, annee, avis, duree, genre, id, realisateur, synopsis, titre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Film other = (Film) obj;
		return Objects.equals(acteurs, other.acteurs) && annee == other.annee && Objects.equals(avis, other.avis)
				&& duree == other.duree && Objects.equals(genre, other.genre) && id == other.id
				&& Objects.equals(realisateur, other.realisateur) && Objects.equals(synopsis, other.synopsis)
				&& Objects.equals(titre, other.titre);
	}

}
