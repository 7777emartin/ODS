package es.uji.ei1027.sdg.dao;


import es.uji.ei1027.sdg.model.Iniciativa;
import es.uji.ei1027.sdg.model.Suscriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SuscriptorDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    /* Añadir Suscriptor */
    public void addSuscriptor(int id_miembro, int id_ods) {
        jdbcTemplate.update("INSERT INTO Suscriptor VALUES(?,?)", id_miembro, id_ods
        );
    }

    /*Eliminar Suscriptor */
    public void deleteSuscriptor(int id_miembro, int id_ods) {
        jdbcTemplate.update("DELETE from Suscriptor where id_miembro=? AND id_ods=?",
                id_miembro, id_ods);
    }

    public void deleteSuscriptor(Suscriptor suscriptor) {
        jdbcTemplate.update("DELETE FROM Suscriptor WHERE  id_miembro = ? AND id_ods=?", suscriptor.getId_miembro(), suscriptor.getId_ods());
    }


    /* Geters de las Suscriptors. Nulos en el caso de no existir. */


    public Suscriptor getSuscriptoresOds(int id_ods) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Suscriptor WHERE id_ods=?", new SuscriptorRowMapper(), id_ods);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    public Suscriptor getOdsSuscriptorMiembro(int id_miembro, int id_ods) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Suscriptor WHERE id_miembro=? and id_ods=?", new SuscriptorRowMapper(), id_miembro,id_ods);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Suscriptor> getSuscriptoresMiembro(int id_miembro) {
        try {
            return jdbcTemplate.query("SELECT * FROM Suscriptor WHERE id_miembro=?", new SuscriptorRowMapper(),id_miembro);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Suscriptor>();
        }
    }

    /* Devuelve todos los Suscriptores */
    public List<Suscriptor> getSuscriptores() {
        try {
            return jdbcTemplate.query("SELECT * FROM Suscriptor", new SuscriptorRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Suscriptor>();
        }
    }

}
