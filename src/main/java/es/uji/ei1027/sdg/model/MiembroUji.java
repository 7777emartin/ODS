package es.uji.ei1027.sdg.model;

public class MiembroUji {
    private  int id_miembro;
    private String nombre;
    private  String password;
    private String telefono;
    private String correo;
    private String tipo;




    public int getId_miembro() {
        return id_miembro;
    }

    public void setId_miembro(int id_miembro) {
        this.id_miembro = id_miembro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo= correo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }



    public MiembroUji() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "MiembroUji{" +
                "id_miembro=" + id_miembro +
                ", nombre='" + nombre + '\'' +
                ", password='" + password + '\'' +
                ", telefono='" + telefono + '\'' +
                ", correo='" + correo + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
