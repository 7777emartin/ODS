package es.uji.ei1027.sdg.dao;

import es.uji.ei1027.sdg.model.Suscriptor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class SuscriptorRowMapper implements RowMapper<Suscriptor> {
    @Override
    public Suscriptor mapRow(ResultSet rs, int rowNum) throws SQLException {
        Suscriptor suscriptor = new Suscriptor();
        suscriptor.setId_miembro(rs.getInt("id_miembro"));
        suscriptor.setId_ods(rs.getInt("id_ods"));


        return suscriptor;
    }
}