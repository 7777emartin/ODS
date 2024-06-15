package es.uji.ei1027.sdg.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


import es.uji.ei1027.sdg.model.Iniciativa;



public class IniciativaValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Iniciativa.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Iniciativa iniciativa = (Iniciativa) obj;
        if (iniciativa.getNombre().trim().equals(""))
            errors.rejectValue("nombre", "obligatorio nombre",
                    "Obligatorio introducir un nombre");
        if(iniciativa.getDescripcion().trim().equals(""))
            errors.rejectValue("descripcion","obligatorio descripcion",
                    "Obligatorio introducir descripción");
        if(iniciativa.getMotivacion().trim().equals(""))
            errors.rejectValue("motivacion","obligatorio motivacion",
                    "Obligatorio introducir motivacion");
        if(iniciativa.getId_ods()==0)
            errors.rejectValue("id_ods","obligatorio motivacion",
                    "Obligatorio seleccionar una ODS");
        if( iniciativa.getClasificacion() != null && iniciativa.getClasificacion().trim().equals(""))
            errors.rejectValue("clasificacion", "obligatorio clasificacion",
                    "Obligatorio seleccionar una clasificacion");

    }

}
