package es.uji.ei1027.sdg.dao;

import es.uji.ei1027.sdg.model.ParticipanteAccion;
import es.uji.ei1027.sdg.model.ParticipanteIniciativa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;


@Repository

public class ParticipanteAccionDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    /* Añadir Participante */
    public void addParticipante(ParticipanteAccion participanteAccion) {
        jdbcTemplate.update("INSERT INTO participanteAccion(id_miembro,id_accion) VALUES(?, ?)",
                participanteAccion.getId_miembro(), participanteAccion.getId_accion());
    }
    public void addParticipante(int id_miembro, int id_accion) {
        jdbcTemplate.update("INSERT INTO participanteIniciativa(id_miembro,id_iniciativa) VALUES(?, ?)",
                id_miembro, id_accion);
    }

    public List<ParticipanteAccion> getParticipantes(int id_accion) {
        try {
            return jdbcTemplate.query("SELECT * FROM participanteAccion WHERE id_accion=?", new ParticipanteAccionRowMapper(), id_accion);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public void deleteParticipante(int id_miembro, int id_accion) {
        jdbcTemplate.update("DELETE from participanteAccion where id_miembro=? AND id_accion=?",
                id_miembro,id_accion);
    }

    public boolean existeParticipante(int idMiembro, int idIniciativa) {
        String sql = "SELECT COUNT(*) FROM ParticipanteIniciativa WHERE id_miembro = ? AND id_iniciativa = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, idMiembro, idIniciativa);
        return count > 0;
    }



}