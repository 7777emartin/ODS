package es.uji.ei1027.sdg.dao;

import es.uji.ei1027.sdg.model.Iniciativa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class HomeDao {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int getNumeroIniciativasEnProgreso() {
        try {
            return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM iniciativa WHERE estado = 'En progreso'", Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }


    public int getNumeroIniciativasTotal (){
        try {
            return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM iniciativa ", Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    public Iniciativa getUltimaIniciativa() {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM iniciativa  ORDER BY id_iniciativa DESC LIMIT 1", new IniciativaRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public int obtenerUsuarioMasIniciativas() {
        try {
            return jdbcTemplate.queryForObject("SELECT id_miembro " +
                    "FROM Iniciativa " +
                    "GROUP BY id_miembro " +
                    "ORDER BY COUNT(*) DESC " +
                    "LIMIT 1", Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    public int obtenerODSConMasIniciativas() {
        try {
            return jdbcTemplate.queryForObject("SELECT id_ods " +
                    "FROM Iniciativa " +
                    "GROUP BY id_ods " +
                    "ORDER BY COUNT(*) DESC " +
                    "LIMIT 1", Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    public int obtenerIniciativasSinAcciones() {
        try {
            return jdbcTemplate.queryForObject("SELECT COUNT(*) AS num_iniciativas_sin_acciones\n" +
                    "FROM Iniciativa\n" +
                    "WHERE id_iniciativa NOT IN (\n" +
                    "    SELECT id_iniciativa\n" +
                    "    FROM Accion\n" +
                    ");\n", Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }



}
