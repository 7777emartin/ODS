package es.uji.ei1027.sdg.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


import es.uji.ei1027.sdg.model.Objetivo;



public class ObjetivoValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Objetivo.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Objetivo objetivo = (Objetivo) obj;
        if (objetivo.getNombre().trim().equals(""))
            errors.rejectValue("nombre", "obligatorio nombre",
                    "Obligatorio introducir un nombre");
        if(objetivo.getDescripcion().trim().equals(""))
            errors.rejectValue("descripcion","obligatorio descripcion",
                    "Obligatorio introducir descripción");

    }
}

