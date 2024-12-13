package fr.eni.tp.filmotheque.dall;

import java.util.List;

import fr.eni.tp.filmotheque.bo.Film;

public interface FilmDAO {

	void create(Film film);
	
	Film read(long id);
	
	List<Film> findAll();
	
	Film findTitre(long id, String titre);
	
	boolean existeTitreFilm(String titre);
	
}
