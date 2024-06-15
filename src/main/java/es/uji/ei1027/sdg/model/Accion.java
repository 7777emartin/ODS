package es.uji.ei1027.sdg.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Accion {
    private int id_accion;
    private String nombre;
    private int id_iniciativa;
    private int id_objetivo;
    private String resultadoEsperado;
    private String progresion;
    private  String resultado;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaModificacion;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFin;

    public Accion() {
    }

    public int getId_accion() {
        return id_accion;
    }

    public void setId_accion(int id_accion) {
        this.id_accion = id_accion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId_iniciativa() {
        return id_iniciativa;
    }

    public void setId_iniciativa(int id_iniciativa) {
        this.id_iniciativa = id_iniciativa;
    }

    public int getId_objetivo() {
        return id_objetivo;
    }

    public void setId_objetivo(int id_objetivo) {
        this.id_objetivo = id_objetivo;
    }

    public String getResultadoEsperado() {
        return resultadoEsperado;
    }

    public void setResultadoEsperado(String resultadoEsperado) {
        this.resultadoEsperado = resultadoEsperado;
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

    public String getProgresion() {
        return progresion;
    }

    public void setProgresion(String progresion) {
        this.progresion = progresion;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public LocalDate getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDate fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    @Override
    public String toString() {
        return "Accion{" +
                "id_accion=" + id_accion +
                ", nombre='" + nombre + '\'' +
                ", id_iniciativa=" + id_iniciativa +
                ", id_objetivo=" + id_objetivo +
                ", resultadoEsperado='" + resultadoEsperado + '\'' +
                ", progresion='" + progresion + '\'' +
                ", resultado='" + resultado + '\'' +
                ", fechaModificacion=" + fechaModificacion +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                '}';
    }
}
