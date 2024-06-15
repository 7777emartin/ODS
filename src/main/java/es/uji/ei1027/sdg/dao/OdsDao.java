package es.uji.ei1027.sdg.dao;


import es.uji.ei1027.sdg.model.Accion;
import es.uji.ei1027.sdg.model.Ods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OdsDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* Añadir Ods */
    public void addOds(Ods ods) {
        jdbcTemplate.update("INSERT INTO Ods(numero,nombre,foto_ref,descripcion) VALUES(?,?,?,?)",ods.getNumero(), ods.getNombre(),ods.getFoto_ref(),ods.getDescripcion());
    }

    /*Eliminar Ods */
    public void deleteOds(int id_ods) {
        jdbcTemplate.update("DELETE from Ods where id_ods=?", id_ods);
    }

    /* Actualitza atributos de Ods
   (excepto la clave primária) */
    public void updateOds(Ods ods) {
        jdbcTemplate.update("UPDATE Ods SET numero = ?,nombre = ?,foto_ref=?,descripcion=? WHERE id_ods = ?",ods.getNumero(),ods.getNombre(),ods.getFoto_ref(),ods.getDescripcion(), ods.getId_ods());
    }

    /* Geters de los Ods. Nulos en el caso de no existir. */
    public Ods getOds(int id_ods) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Ods WHERE id_ods=?", new OdsRowMapper(), id_ods);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }




    /* Devuelve todas las Odgs */
    public List<Ods> getListOds() {
        try {
            return jdbcTemplate.query("SELECT * FROM Ods ORDER BY numero ASC", new OdsRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Ods>();
        }
    }
}
