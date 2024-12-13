package fr.eni.tp.filmotheque.dall;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.tp.filmotheque.bo.Film;
import fr.eni.tp.filmotheque.bo.Genre;
import fr.eni.tp.filmotheque.bo.Participant;

@Repository
@Primary
public class FilmDAOImpl implements FilmDAO {

	private static final String INSERT = "INSERT INTO FILM (titre, annee, duree, synopsis, id_realisateur, id_genre) VALUES (:titre, :annee, :duree, :synopsis, :id_realisateur, :id_genre)";
	private static final String FIND_BY_ID = "SELECT f.id, f.titre, annee, duree, synopsis, f.id_realisateur, r.nom AS nom, r.prenom AS prenom, f.id_genre, g.titre AS titre_genre FROM FILM f INNER JOIN PARTICIPANT r ON f.id_realisateur = r.id INNER JOIN GENRE g ON f.id_genre = g.id WHERE f.id = :idFilm";
	private static final String FIND_ALL = "SELECT f.id, f.titre, annee, duree, synopsis, f.id_realisateur, r.nom AS nom, r.prenom AS prenom, f.id_genre, g.titre AS titre_genre FROM FILM f INNER JOIN PARTICIPANT r ON f.id_realisateur = r.id INNER JOIN GENRE g ON f.id_genre = g.id";
	private static final String FIND_TITLE_BY_ID = "SELECT id, titre FROM FILM where id=:id";
	private static final String COUNT_TITRE = "SELECT COUNT(*) FROM FILM WHERE titre = :titre";
	
	private NamedParameterJdbcTemplate jdbcTemplate;

	public FilmDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void create(Film film) {
		// Manipulation de la clef primaire auto-générée : IDENTIY
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("titre", film.getTitre());
		map.addValue("annee", film.getAnnee());
		map.addValue("duree", film.getDuree());
		map.addValue("synopsis", film.getSynopsis());
		map.addValue("id_realisateur", film.getRealisateur() != null ? film.getRealisateur().getId() : null);
		map.addValue("id_genre", film.getGenre() != null ? film.getGenre().getId() : null);
		this.jdbcTemplate.update(INSERT, map, keyHolder);
		
		if (keyHolder.getKey() != null) {
			film.setId(keyHolder.getKey().longValue());
		}
	}

	@Override
	public Film read(long id) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idFilm", id);
		return this.jdbcTemplate.queryForObject(FIND_BY_ID, map, new FilmRowMap());
	}

	@Override
	public List<Film> findAll() {
		return jdbcTemplate.query(FIND_ALL, new FilmRowMap());
	}

	@Override
	public Film findTitre(long id, String titre) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("id", id);
		map.addValue("titre", titre);
		return jdbcTemplate.queryForObject(FIND_TITLE_BY_ID, map, new FilmRowMap());
	}
	
	class FilmRowMap implements RowMapper<Film> {
		/**
		 * le resultSet contient une ligne de résultat de l'exécution de la requete avec
		 * des colonnes
		 */
		@Override
		public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
			Film film = new Film();
			film.setId(rs.getLong("id"));
			film.setTitre(rs.getString("titre"));
			film.setAnnee(rs.getInt("annee"));
			film.setDuree(rs.getInt("duree"));
			film.setSynopsis(rs.getString("synopsis"));

			Participant realisateur = new Participant();
			realisateur.setId(rs.getLong("id_realisateur"));
			realisateur.setNom(rs.getString("nom"));
			realisateur.setPrenom(rs.getString("prenom"));
			film.setRealisateur(realisateur);

			Genre genre = new Genre();
			genre.setId(rs.getLong("id_genre"));
			genre.setTitre(rs.getString("titre_genre"));
			film.setGenre(genre);

			return film;
		}
	}

	@Override
	public boolean existeTitreFilm(String titre) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("titre", titre);
		int nbTitre = jdbcTemplate.queryForObject(COUNT_TITRE, map, Integer.class);
		return nbTitre > 0 ? true : false;
	}
}
