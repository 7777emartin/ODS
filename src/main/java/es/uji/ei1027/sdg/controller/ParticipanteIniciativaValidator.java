package es.uji.ei1027.sdg.controller;


import es.uji.ei1027.sdg.model.MiembroUji;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ParticipanteIniciativaValidator implements Validator {

    @Override
    public boolean supports(Class<?> cls) {
        return MiembroUji.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        MiembroUji miembroUji = (MiembroUji) obj;
        if (miembroUji.getCorreo().length() == 0) {
            errors.rejectValue("correo", "obligatorio correo",
                    "Obligatorio introducir un correo");
        } else
            errors.rejectValue("correo", "obligatorio correo válido",
                    "Introduzca un correo válido");


    }
}


