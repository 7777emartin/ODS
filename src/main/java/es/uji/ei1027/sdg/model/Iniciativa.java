package es.uji.ei1027.sdg.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Iniciativa {
    private int id_iniciativa;
    private String nombre;
    private int id_miembro;
    private int id_ods;
    private String descripcion;
    private String estado;
    private String motivacion;
    private  String clasificacion;
    private String comentario;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaModificacion;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFin;

    public Iniciativa() {
    }

    public String getMotivacion() {
        return motivacion;
    }

    public void setMotivacion(String motivacion) {
        this.motivacion = motivacion;
    }
    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }
    public int getId_iniciativa() {
        return id_iniciativa;
    }

    public void setId_iniciativa(int id_iniciativa) {
        this.id_iniciativa = id_iniciativa;
    }

    public int getId_miembro() {
        return id_miembro;
    }

    public void setId_miembro(int id_miembro) {
        this.id_miembro = id_miembro;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDate fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    @Override
    public String toString() {
        return "Iniciativa{" +
                "id_iniciativa=" + id_iniciativa +
                ", nombre='" + nombre + '\'' +
                ", id_miembro=" + id_miembro +
                ", id_ods=" + id_ods +
                ", descripcion='" + descripcion + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", estado='" + estado + '\'' +
                ", comentario='" + comentario + '\'' +
                ", clasificacion='" + clasificacion + '\'' +
                '}';
    }

}
