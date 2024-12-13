package fr.eni.tp.filmotheque.dall;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.tp.filmotheque.bo.Participant;

@Repository
@Primary
public class ParticipantDAOImpl implements ParticipantDAO {
	
	private static final String FIND_BY_ID = "SELECT id, nom, prenom FROM PARTICIPANT WHERE id = :id";
	private static final String FIND_ALL = "SELECT id, nom, prenom FROM PARTICIPANT";
	private static final String INSERT = "INSERT INTO ACTEURS (id_participant, id_film) VALUES (:idParticipant, :idFilm)";
	private static final String FIND_ACTEURS_BY_IDFILM = "SELECT id, nom, prenom FROM PARTICIPANT p INNER JOIN ACTEURS a ON a.id_participant = p.id WHERE id_film = :idFilm";

	private NamedParameterJdbcTemplate jdbcTemplate;
	
	public ParticipantDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Participant read(long id) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("id", id);
		return jdbcTemplate.queryForObject(FIND_BY_ID, map, 
				new BeanPropertyRowMapper<>(Participant.class));
	}

	@Override
	public List<Participant> findAll() {
		return jdbcTemplate.query(FIND_ALL, new BeanPropertyRowMapper<>(Participant.class));
	}

	@Override
	public List<Participant> findActeurs(long idFilm) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idFilm", idFilm);
		return jdbcTemplate.query(FIND_ACTEURS_BY_IDFILM, map, new BeanPropertyRowMapper<>(Participant.class));
	}

	@Override
	public void createActeur(long idParticipant, long idFilm) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idParticipant", idParticipant);
		map.addValue("idFilm", idFilm);
		jdbcTemplate.update(INSERT, map);
	}

}
