package es.uji.ei1027.sdg.dao;

import es.uji.ei1027.sdg.model.ParticipanteAccion;
import es.uji.ei1027.sdg.model.ParticipanteIniciativa;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ParticipanteIniciativaRowMapper implements RowMapper<ParticipanteIniciativa> {
    @Override
    public ParticipanteIniciativa mapRow(ResultSet rs, int rowNum) throws SQLException {
        ParticipanteIniciativa participante = new ParticipanteIniciativa();
        participante.setId_miembro(rs.getInt("id_miembro"));
        participante.setId_iniciativa(rs.getInt("id_iniciativa"));

        return participante;
    }
}
