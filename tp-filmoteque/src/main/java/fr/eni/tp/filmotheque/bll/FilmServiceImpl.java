package fr.eni.tp.filmotheque.bll;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import fr.eni.tp.filmotheque.bo.Film;
import fr.eni.tp.filmotheque.bo.Genre;
import fr.eni.tp.filmotheque.bo.Participant;
import fr.eni.tp.filmotheque.dall.FilmDAO;
import fr.eni.tp.filmotheque.dall.GenreDAO;
import fr.eni.tp.filmotheque.dall.ParticipantDAO;
import fr.eni.tp.filmotheque.exception.BusinessException;

@Service
@Primary
public class FilmServiceImpl implements FilmService {
	
	private FilmDAO filmDAO;
	private GenreDAO genreDAO;
	private ParticipantDAO participantDAO;

	public FilmServiceImpl(FilmDAO filmDAO, GenreDAO genreDAO, ParticipantDAO participantDAO) {
		this.filmDAO = filmDAO;
		this.genreDAO = genreDAO;
		this.participantDAO = participantDAO;
	}

	@Override
	public List<Film> consulterFilms() {
		return this.filmDAO.findAll();
	}

	@Override
	public Film consulterFilmParId(long id) {
		Film f = this.filmDAO.read(id);
		List<Participant> acteurs = participantDAO.findActeurs(id);
		
		if (acteurs != null) {
			f.setActeurs(acteurs);
		}
		return f;
	}

	@Override
	public List<Genre> consulterGenres() {
		return genreDAO.findAll();
	}

	@Override
	public List<Participant> consulterParticipants() {
		return participantDAO.findAll();
	}

	@Override
	public Genre consulterGenreParId(long id) {
		return genreDAO.read(id);
	}

	@Override
	public Participant consulterParticipantParId(long id) {
		return participantDAO.read(id);
	}

	@Override
	public void creerFilm(Film film) throws BusinessException {
		BusinessException be = new BusinessException();
		boolean valide = validerTitreFilm(film.getTitre(), be);
		valide &= validerListeActeurs(film.getActeurs(), be);
		if (valide) {
			// Créer le film dans la base de données
			filmDAO.create(film);
		} else {
			throw be;
		}
	    // Récupérer l'ID du film créé (si `film` contient déjà son ID après insertion)
	    long idFilm = film.getId();
 
	    // Associer les acteurs au film
	    if (film.getActeurs() != null) {
	        for (Participant acteur : film.getActeurs()) {
	            participantDAO.createActeur(acteur.getId(), idFilm);
	        }
	    }
	}
	
	private boolean validerTitreFilm(String titreFilm, BusinessException be) {
		boolean titreFilmExiste = this.filmDAO.existeTitreFilm(titreFilm);
		if (titreFilmExiste) {
			be.addMessage("Ce titre de film existe déjà.");
		}
		return !titreFilmExiste;
	}
	
	private boolean validerListeActeurs(List<Participant> listeActeurs, BusinessException be) {
		if (listeActeurs.isEmpty()) {
			be.addMessage("La liste des acteurs est obligatoire.");
		}
		return !listeActeurs.isEmpty();
	}

}
