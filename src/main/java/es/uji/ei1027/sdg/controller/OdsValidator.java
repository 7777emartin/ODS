package es.uji.ei1027.sdg.controller;

import es.uji.ei1027.sdg.model.Ods;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class OdsValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Ods.class.equals(cls);

    }

    @Override
    public void validate(Object obj, Errors errors) {
        Ods ods = (Ods) obj;
        if (ods.getNombre().trim().equals(""))
            errors.rejectValue("nombre", "obligatorio nombre",
                    "Obligatorio introducir un nombre");
        if(ods.getFoto_ref().trim().equals(""))
            errors.rejectValue("foto_ref","obligatorio descripcion",
                    "Obligatorio una referenica a una imagen");
        if(ods.getDescripcion().trim().equals(""))
            errors.rejectValue("descripcion","obligatorio descripcion",
                    "Obligatorio introducir descripción");
    }

}

