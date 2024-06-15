package es.uji.ei1027.sdg.dao;

import es.uji.ei1027.sdg.model.Accion;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class AccionRowMapper implements RowMapper<Accion> {
    @Override
    public Accion mapRow(ResultSet rs, int rowNum) throws SQLException {
        Accion accion = new Accion();
        accion.setId_accion(rs.getInt("id_accion"));
        accion.setNombre(rs.getString("nombre"));
        accion.setId_objetivo(rs.getInt("id_objetivo"));
        accion.setId_iniciativa(rs.getInt("id_iniciativa"));
        accion.setResultadoEsperado(rs.getString("resultado_esperado"));
        accion.setFechaInicio((rs.getObject("fecha_inicio", LocalDate.class)));
        accion.setFechaFin((rs.getObject("fecha_fin", LocalDate.class)));
        accion.setResultado(rs.getString("resultado"));
        accion.setProgresion(rs.getString("progresion"));
        accion.setFechaModificacion(rs.getObject("fecha_modificacion", LocalDate.class));

        return accion;
    }
}
