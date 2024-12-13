package fr.eni.tp.filmotheque.dall;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.tp.filmotheque.bo.Genre;

@Repository
@Primary
public class GenreDAOImpl implements GenreDAO {

	private static final String FIND_BY_ID = "SELECT id, titre FROM GENRE WHERE id = :idGenre";
	
	private static final String FIND_ALL = "SELECT id, titre FROM GENRE";
	
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	public GenreDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Genre read(long id) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idGenre", id);
		return jdbcTemplate.queryForObject(FIND_BY_ID, map, new BeanPropertyRowMapper<>(Genre.class));
	}

	@Override
	public List<Genre> findAll() {
		return jdbcTemplate.query(FIND_ALL, new BeanPropertyRowMapper<>(Genre.class));
	}

}
