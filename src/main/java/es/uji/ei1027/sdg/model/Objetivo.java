package es.uji.ei1027.sdg.model;

public class Objetivo {
    private int id_objetivo;
    private String nombre;
    private int id_ods;
    private String descripcion;

    public Objetivo() {
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public int getId_objetivo() {
        return id_objetivo;
    }

    public void setId_objetivo(int id_objetivo) {
        this.id_objetivo = id_objetivo;
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
        return "Objetivo{" +
                "id_objetivo=" + id_objetivo +
                ", nombre='" + nombre + '\'' +
                ", id_ods=" + id_ods +
                '}';
    }
}
