package es.uji.ei1027.sdg.dao;


import es.uji.ei1027.sdg.model.MiembroUji;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.management.Query;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

@Repository
public class MiembroUjiDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //Añadir un nuevo miembro a la BBDD
    public void addUjiMember(MiembroUji miembroUji) {
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();

        jdbcTemplate.update("INSERT INTO Miembrouji(nombre,password,telefono,correo ,tipo) VALUES(?,?,?,?,?)",
                miembroUji.getNombre(), passwordEncryptor.encryptPassword(miembroUji.getPassword()), miembroUji.getTelefono(), miembroUji.getCorreo(), miembroUji.getTipo());
    }

    //Eliminar un miembro con su al
    public void deleteMember(int id_miembro) {
        jdbcTemplate.update("DELETE FROM Miembrouji WHERE id_miembro=? ", id_miembro);
    }

    //Seleccionar un miembro de con su al
    public MiembroUji getUjiMember(int id_miembro) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Miembrouji WHERE id_miembro=?",
                    new MiembroUjiRowMapper(), id_miembro);

        } catch (EmptyStackException e) {
            return null;
        }
    }

    //Actualizar miembro

    public void updateUjiMember(MiembroUji miembroUji) {
        jdbcTemplate.update(
                "UPDATE Miembrouji SET nombre=?,password = ?,telefono = ?,correo = ?,tipo = ? WHERE id_miembro=?", miembroUji.getNombre(), miembroUji.getPassword(), miembroUji.getTelefono(), miembroUji.getCorreo(),
                miembroUji.getTipo(), miembroUji.getId_miembro()
        );
    }
    //Listar todos los ujiMembers

    public List<MiembroUji> getUjiMemberList() {
        try {
            return jdbcTemplate.query("SELECT * FROM miembrouji",
                    new MiembroUjiRowMapper());
        } catch (EmptyStackException e) {
            return new ArrayList<>();
        }
    }

    public MiembroUji getMiembroUjiCorreo(String correo) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Miembrouji WHERE correo=?",
                    new MiembroUjiRowMapper(), correo);

        } catch (EmptyStackException e) {
            return null;
        }
    }

    public boolean existeCorreo(String correo) {
        try {
            return jdbcTemplate.query("SELECT * FROM miembrouji WHERE correo=?",
                    new MiembroUjiRowMapper(),correo).size() == 0;

        } catch (EmptyStackException e) {
            return false;
        }
    }



    public MiembroUji loadUserByUsername(String correo, String password) {
        MiembroUji user = getMiembroUjiCorreo(correo);
        if (user == null)
            return null; // Usuari no trobat
        // Contrasenya
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        if (passwordEncryptor.checkPassword(password, user.getPassword())) {
            // Es deuria esborrar de manera segura el camp password abans de tornar-lo
            return user;
        } else {
            return null; // bad login!
        }
    }


    public boolean comprobarCorreo(String correo) {
        String sql = "SELECT COUNT(*) FROM Miembrouji WHERE correo = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, correo);
        return count > 0;
    }
}
