package es.uji.ei1027.sdg.controller;

import es.uji.ei1027.sdg.model.Accion;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;





public class AccionValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Accion.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Accion accion = (Accion) obj;
        if (accion.getNombre().trim().equals(""))
            errors.rejectValue("nombre", "obligatorio nombre",
                    "Obligatorio introducir un nombre");
        if( accion.getResultadoEsperado() != null && accion.getResultadoEsperado().trim().equals(""))
            errors.rejectValue("resultadoEsperado","obligatorio resultado esperado",
                    "Obligatorio introducir un resultado esperado");
        if(accion.getId_objetivo()==0)
            errors.rejectValue("id_objetivo","obligatorio objetivo",
                    "Obligatorio seleccionar un objetivo");

        if(accion.getFechaInicio() == null)
            errors.rejectValue("fechaInicio","obligatorio fechaInicio",
                    "Obligatorio seleccionar fecha Inicio");
        if(accion.getFechaFin() == null)
            errors.rejectValue("fechaFin","obligatorio fechaFin",
                    "Obligatorio seleccionar fecha Fin");
    }

}
