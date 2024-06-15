package es.uji.ei1027.sdg.dao;

import es.uji.ei1027.sdg.model.ParticipanteAccion;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ParticipanteAccionRowMapper implements RowMapper<ParticipanteAccion> {
    @Override
    public ParticipanteAccion mapRow(ResultSet rs, int rowNum) throws SQLException {
        ParticipanteAccion participante = new ParticipanteAccion();
        participante.setId_miembro(rs.getInt("id_miembro"));
        participante.setId_accion(rs.getInt("id_accion"));

        return participante;
    }
}
