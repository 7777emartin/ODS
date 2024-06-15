package es.uji.ei1027.sdg.controller;

import es.uji.ei1027.sdg.model.MiembroUji;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return MiembroUji.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        MiembroUji miembroUji = (MiembroUji) obj;
        if (miembroUji.getCorreo().trim().equals("")) {
            errors.rejectValue("correo", "obligatorio",
                    "Obligatorio introducir correo");
        }
        if (miembroUji.getPassword().trim().equals("")) {
            errors.rejectValue("password", "obligatorio",
                    "Obligatorio introducir contraseña");
        }
    }
}
