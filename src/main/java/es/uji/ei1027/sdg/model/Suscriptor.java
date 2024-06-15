package es.uji.ei1027.sdg.model;

import java.time.LocalDate;

public class Suscriptor {

    private int id_miembro;
    private int id_ods;


    public Suscriptor() {
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

    @Override
    public String toString() {
        return "Suscriptor{" +
                "id_miembro=" + id_miembro +
                ", id_ods=" + id_ods +
                '}';
    }
}
