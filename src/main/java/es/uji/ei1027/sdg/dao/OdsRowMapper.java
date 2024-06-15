package es.uji.ei1027.sdg.dao;

import es.uji.ei1027.sdg.model.Ods;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OdsRowMapper implements RowMapper<Ods> {
    @Override
    public Ods mapRow(ResultSet rs, int rowNum) throws SQLException {
        Ods ods = new Ods();
        ods.setId_ods(rs.getInt("id_ods"));
        ods.setNumero(rs.getInt("numero"));
        ods.setNombre(rs.getString("nombre"));
        ods.setFoto_ref(rs.getString("foto_ref"));
        ods.setDescripcion(rs.getString("descripcion"));

        return ods;
    }
}
