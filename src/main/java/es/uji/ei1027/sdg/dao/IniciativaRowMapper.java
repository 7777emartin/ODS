package es.uji.ei1027.sdg.dao;

import es.uji.ei1027.sdg.model.Iniciativa;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class IniciativaRowMapper implements RowMapper<Iniciativa> {
    @Override
    public Iniciativa mapRow(ResultSet rs, int rowNum) throws SQLException {
        Iniciativa iniciativa = new Iniciativa();
        iniciativa.setId_iniciativa(rs.getInt("id_iniciativa"));
        iniciativa.setNombre(rs.getString("nombre"));
        iniciativa.setId_miembro(rs.getInt("id_miembro"));
        iniciativa.setId_ods(rs.getInt("id_ods"));
        iniciativa.setMotivacion(rs.getString("motivacion"));
        iniciativa.setDescripcion(rs.getString("descripcion"));
        iniciativa.setComentario(rs.getString("comentario"));
        iniciativa.setFechaInicio((rs.getObject("fecha_inicio", LocalDate.class)));
        iniciativa.setFechaFin((rs.getObject("fecha_fin", LocalDate.class)));
        iniciativa.setFechaModificacion(rs.getObject("fecha_modificacion", LocalDate.class));
        iniciativa.setEstado(rs.getString("estado"));
        iniciativa.setClasificacion(rs.getString("clasificacion"));

        return iniciativa;
    }
}
