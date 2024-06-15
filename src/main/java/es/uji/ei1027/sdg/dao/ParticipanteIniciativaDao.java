package es.uji.ei1027.sdg.dao;

import es.uji.ei1027.sdg.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sound.midi.Track;
import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;

@Repository
public class ParticipanteIniciativaDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addParticipante(int id_miembro, int id_iniciativa) {
        jdbcTemplate.update("INSERT INTO participanteIniciativa(id_miembro,id_iniciativa) VALUES(?, ?)",
                id_miembro, id_iniciativa);
    }

    public void addParticipante(ParticipanteIniciativa participanteIniciativa) {
        try {
            jdbcTemplate.update("INSERT INTO ParticipanteIniciativa(id_miembro,id_iniciativa) VALUES( ?,?)", participanteIniciativa.getId_miembro(), participanteIniciativa.getId_iniciativa()
            );

        } catch (EmptyResultDataAccessException e) {
            System.out.println("Ha fallado al añadir participante!");
        }
    }





    public List<ParticipanteIniciativa> getParticipantes(int id_iniciativa) {
        try {
            return jdbcTemplate.query("SELECT * FROM participanteIniciativa WHERE id_iniciativa=?", new ParticipanteIniciativaRowMapper(), id_iniciativa);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void deleteParticipante(int id_miembro, int id_iniciativa) {
        jdbcTemplate.update("DELETE from participanteiniciativa where id_miembro=? AND id_iniciativa=?",
                id_miembro,id_iniciativa);
    }



}
