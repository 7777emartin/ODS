package es.uji.ei1027.sdg.dao;

import es.uji.ei1027.sdg.model.Objetivo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ObjetivoRowMapper implements RowMapper<Objetivo> {
    @Override
    public Objetivo mapRow(ResultSet rs, int rowNum) throws SQLException {
        Objetivo objetivo = new Objetivo();
        objetivo.setId_objetivo(rs.getInt("id_objetivo"));
        objetivo.setNombre(rs.getString("nombre"));
        objetivo.setId_ods(rs.getInt("id_ods"));
        objetivo.setDescripcion(rs.getString("descripcion"));

        return objetivo;
    }
}
