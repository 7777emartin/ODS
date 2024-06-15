package es.uji.ei1027.sdg.dao;

import es.uji.ei1027.sdg.model.MiembroUji;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

public class MiembroUjiRowMapper implements RowMapper<MiembroUji> {
    @Override
    public MiembroUji mapRow(ResultSet rs, int rowNum) throws SQLException {
        MiembroUji miembroUji = new MiembroUji();
        miembroUji.setId_miembro(rs.getInt("id_miembro"));
        miembroUji.setNombre(rs.getString("nombre"));
        miembroUji.setPassword(rs.getString("password"));
        miembroUji.setTelefono(rs.getString("telefono"));
        miembroUji.setCorreo(rs.getString("correo"));
        miembroUji.setTipo(rs.getString("tipo"));

        return miembroUji;
    }
}
