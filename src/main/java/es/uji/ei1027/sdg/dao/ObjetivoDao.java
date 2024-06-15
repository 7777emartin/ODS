package es.uji.ei1027.sdg.dao;


import es.uji.ei1027.sdg.model.Objetivo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ObjetivoDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public void addObjetivo(Objetivo objetivo) {
        jdbcTemplate.update("INSERT INTO Objetivo(nombre,id_ods,descripcion) VALUES( ?,?,? )", objetivo.getNombre(), objetivo.getId_ods(), objetivo.getDescripcion()
        );
    }

    /*Eliminar Objetivo */
    public void deleteObjetivo(int id_objetivo) {
        jdbcTemplate.update("DELETE from Objetivo where id_objetivo=?", id_objetivo);
    }

    /* Actualitza atributos de Objetivo
   (excepto la clave primária) */
    public void updateObjetivo(Objetivo objetivo) {
        jdbcTemplate.update("UPDATE Objetivo SET descripcion =?, nombre=? WHERE id_objetivo=?", objetivo.getDescripcion(), objetivo.getNombre(), objetivo.getId_objetivo());
    }

    /* Geters de los objetivos. Nulos en el caso de no existir. */
    public Objetivo getObjetivo(int id_objetivo) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Objetivo WHERE id_objetivo=?", new ObjetivoRowMapper(), id_objetivo);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Objetivo> getObjetivosOds(int id_ods) {
        try {
            return jdbcTemplate.query("SELECT * FROM Objetivo WHERE id_ods=? ORDER BY id_objetivo", new ObjetivoRowMapper(), id_ods);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }



}
