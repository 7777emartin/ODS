package es.uji.ei1027.sdg.model;

import java.time.LocalDate;

public class ParticipanteIniciativa {
    private int id_miembro;
    private int id_iniciativa;

    public ParticipanteIniciativa() {
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

}