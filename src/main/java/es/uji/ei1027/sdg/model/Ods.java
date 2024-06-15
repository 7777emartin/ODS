package es.uji.ei1027.sdg.model;

public class Ods {


    private int id_ods;
    private int numero;
    private String nombre;
    private String foto_ref;
    private String descripcion;


    public Ods() {
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getFoto_ref() {
        return foto_ref;
    }

    public void setFoto_ref(String foto_ref) {
        this.foto_ref = foto_ref;
    }


    public int getId_ods() {
        return id_ods;
    }

    public void setId_ods(int id_ods) {
        this.id_ods = id_ods;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Ods{" +
                "id_ods=" + id_ods +
                ", numero=" + numero +
                ", nombre='" + nombre + '\'' +
                ", foto_ref='" + foto_ref + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
