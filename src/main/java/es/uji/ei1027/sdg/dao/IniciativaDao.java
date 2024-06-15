package es.uji.ei1027.sdg.dao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import es.uji.ei1027.sdg.model.Iniciativa;
import es.uji.ei1027.sdg.model.MiembroUji;
import es.uji.ei1027.sdg.model.Objetivo;
import es.uji.ei1027.sdg.model.Ods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Repository
public class IniciativaDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* Añadir iniciativa */


    public void addIniciativa(Iniciativa iniciativa) {
        jdbcTemplate.update("INSERT INTO iniciativa(nombre,id_miembro,id_ods,descripcion,motivacion,fecha_inicio,fecha_fin,estado,clasificacion) VALUES( ? , ? , ? , ? , ? ,?, ? , ? ,?)",
                iniciativa.getNombre(), iniciativa.getId_miembro(), iniciativa.getId_ods(), iniciativa.getDescripcion(),iniciativa.getMotivacion(), LocalDate.now(),iniciativa.getFechaFin(),"Revision pendiente",iniciativa.getClasificacion()
        );
    }

    /*Eliminar iniciativa */
    public void deleteIniciativa(int id_iniciativa) {
        jdbcTemplate.update("DELETE from iniciativa where id_iniciativa=?  ORDER BY fecha_inicio ASC",id_iniciativa);
    }
    public void finalizarPropuesta(int id_iniciativa) {
        jdbcTemplate.update("UPDATE iniciativa SET estado='Finalizado' WHERE id_iniciativa=?",id_iniciativa);
    }


    public List<Iniciativa> getIniciativaListODSEstado(int id_ods,String estado) {
        try {
            //estado.toUpperCase(Locale.ROOT);
            return jdbcTemplate.query("SELECT * FROM iniciativa WHERE id_ods=? and estado=?  ORDER BY fecha_inicio ASC",new IniciativaRowMapper(),id_ods,estado);
        } catch (EmptyResultDataAccessException e) {
             return null;
        }
    }

    public List<Iniciativa> getIniciativaList_SiODS_NoEstado(int id_ods) {
        try {
            return jdbcTemplate.query("SELECT * FROM iniciativa WHERE id_ods=? AND (estado='Finalizado' OR estado='En progreso')  ORDER BY fecha_inicio ASC",
                    new IniciativaRowMapper(), id_ods);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Iniciativa> getIniciativaList_NoODS_SiEstado(String estado){
        try {
            estado.toUpperCase(Locale.ROOT);
            return jdbcTemplate.query("SELECT * FROM iniciativa WHERE estado=?  ORDER BY fecha_inicio ASC",new IniciativaRowMapper(),estado);

        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }


    /* Actualitza atributos de iniciativa
   (excepto la clave primária) */
    public void updateIniciativa(Iniciativa iniciativa) {
        jdbcTemplate.update("UPDATE iniciativa SET id_miembro= ?,id_ods= ?,nombre = ?, descripcion = ? , motivacion = ?,estado = ?, comentario = ?, fecha_modificacion = CURRENT_DATE WHERE id_iniciativa=?", iniciativa.getId_miembro(), iniciativa.getId_ods(), iniciativa.getNombre(), iniciativa.getDescripcion(), iniciativa.getMotivacion(),iniciativa.getEstado(), iniciativa.getComentario(), iniciativa.getId_iniciativa());
    }

    /* Geters de las iniciativas. Nulos en el caso de no existir. */
    public Iniciativa getIniciativa(int id_iniciativa) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM iniciativa WHERE id_iniciativa=? ", new IniciativaRowMapper(), id_iniciativa);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Iniciativa> getIniciativasPublico() {
        try {
            return jdbcTemplate.query("SELECT * FROM iniciativa WHERE estado='Finalizado' OR estado='En progreso'", new IniciativaRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    public List<Iniciativa> getIniciativasMiembro(int id_miembro) {
        try {
            return jdbcTemplate.query("SELECT * FROM iniciativa WHERE id_miembro=? ORDER BY  id_iniciativa DESC, fecha_inicio ASC", new IniciativaRowMapper(), id_miembro);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }



    public List<Iniciativa> getIniciativaOds(int id_ods) {
        try {
            return jdbcTemplate.query("SELECT * FROM iniciativa WHERE estado='Revision pendiente' and id_ods = ? ORDER BY  id_iniciativa DESC, fecha_inicio ASC", new IniciativaRowMapper(), id_ods);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Iniciativa>();
        }
    }

    public List<Iniciativa> getPropuestasPendientes() {
        try {
            return jdbcTemplate.query("SELECT * FROM iniciativa WHERE estado='Revision pendiente' ORDER BY  id_iniciativa DESC, fecha_inicio ASC", new IniciativaRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Iniciativa>();
        }
    }

    public List<Iniciativa> getPropuestasPendientesByODS(int id_ods) {
        try {
            return jdbcTemplate.query("SELECT * FROM iniciativa WHERE estado='Revision pendiente' AND id_ods=? ORDER BY  id_iniciativa DESC, fecha_inicio ASC", new IniciativaRowMapper(), id_ods);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Iniciativa>();
        }
    }



    public void updateFechaFinIniciativa(int id_iniciativa) {
        jdbcTemplate.update("UPDATE iniciativa SET fecha_fin = current_date WHERE id_iniciativa = ?", id_iniciativa);
    }

    //Aqui voy a meter las consultas de un id_miembro específico


    public List<Iniciativa> getIniciativasPublicoPersonal(int id_miembro) {
        try {
            return jdbcTemplate.query("SELECT * FROM iniciativa WHERE id_miembro=?  ORDER BY  id_iniciativa DESC, fecha_inicio ASC, fecha_modificacion DESC", new IniciativaRowMapper(), id_miembro);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    public List<Iniciativa> getIniciativaList_NoODS_SiEstadoPersonal(int id_miembro,String estado){
        try {
            estado.toUpperCase(Locale.ROOT);
            return jdbcTemplate.query("SELECT * FROM iniciativa WHERE id_miembro=? AND estado=?  ORDER BY  id_iniciativa DESC, fecha_inicio ASC",new IniciativaRowMapper(),id_miembro, estado);

        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }


    public List<Iniciativa>  getIniciativaList_SiODS_NoEstadoPersonal(int id_miembro,int id_ods) {
        try {
            return jdbcTemplate.query("SELECT * FROM iniciativa WHERE id_ods=? AND id_miembro=?  ORDER BY  id_iniciativa DESC, fecha_inicio ASC",
                    new IniciativaRowMapper(), id_ods,id_miembro);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Iniciativa> getIniciativaListODSEstadoPersonal(int id_miembro, int id_ods,String estado) {
        try {
            //estado.toUpperCase(Locale.ROOT);
            return jdbcTemplate.query("SELECT * FROM iniciativa WHERE id_miembro=? AND id_ods=? AND estado=?  ORDER BY  id_iniciativa DESC, fecha_inicio ASC",new IniciativaRowMapper(),id_miembro,id_ods,estado);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Iniciativa getUltimaIniciativa(int id_miembro) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM iniciativa WHERE id_miembro = ? ORDER BY id_iniciativa DESC LIMIT 1", new IniciativaRowMapper(),id_miembro);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public boolean existeParticipante(int idMiembro, int idIniciativa) {
        String sql = "SELECT COUNT(*) FROM ParticipanteIniciativa WHERE id_miembro = ? AND id_iniciativa = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, idMiembro, idIniciativa);
        return count > 0;
    }



}
