package es.uji.ei1027.sdg.dao;

import es.uji.ei1027.sdg.model.Accion;
import es.uji.ei1027.sdg.model.Objetivo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AccionDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* Añadir acción */
    public void addAccion(Accion accion) {
        jdbcTemplate.update("INSERT INTO Accion(nombre,id_objetivo,id_iniciativa,resultado_esperado,fecha_inicio,fecha_fin) VALUES(?, ?, ?, ?, ?,?)",
                accion.getNombre(), accion.getId_objetivo(),accion.getId_iniciativa(),accion.getResultadoEsperado(), accion.getFechaInicio(),accion.getFechaFin());
    }

    /*Eliminar acción */
    public void deleteAccion(int id_accion) {
        jdbcTemplate.update("DELETE from Accion where id_accion=?",
                id_accion);
    }

    /* Actualitza atributos de acción
   (excepto la clave primária) */
    public void updateAccion(Accion accion) {
        jdbcTemplate.update("UPDATE Accion SET id_iniciativa = ?, id_objetivo = ?, nombre = ?, resultado_esperado = ?, progresion = ?, resultado = ?, fecha_modificacion = CURRENT_DATE  WHERE id_accion= ?", accion.getId_iniciativa(),accion.getId_objetivo(),accion.getNombre(),accion.getResultadoEsperado(),accion.getProgresion(), accion.getResultado(), accion.getId_accion());
    }

    /* Geters de las acciones. Nulos en el caso de no existir. */
    public Accion getAccion(int id_accion) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Accion WHERE id_accion=? ", new AccionRowMapper(), id_accion);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Accion> getAccionesIniciativa(int id_iniciativa) {
        try {
            return jdbcTemplate.query("SELECT * FROM accion WHERE id_iniciativa=?", new AccionRowMapper(), id_iniciativa);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Accion>();
        }
    }


    /* Devuelve todas las acciones */
    public List<Accion> getAcciones() {
        try {
            return jdbcTemplate.query("SELECT * FROM Accion", new AccionRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Accion>();
        }
    }

    public boolean finalizarAccion(int id_accion) {
        try {
            jdbcTemplate.update("UPDATE accion SET fecha_fin = current_date WHERE id_accion=?", id_accion);
            return true;
        } catch (DataAccessException e) {
            // Aquí puedes manejar el error de restricción violada
            System.out.println("Se produjo un error al finalizar la acción: " + e.getMessage());
            return false;
        }
    }


    public boolean existeParticipante(int idMiembro, int id_accion) {
        String sql = "SELECT COUNT(*) FROM ParticipanteAccion WHERE id_miembro = ? AND id_accion = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, idMiembro, id_accion);
        return count > 0;
    }


}
