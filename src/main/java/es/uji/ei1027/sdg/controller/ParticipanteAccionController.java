package es.uji.ei1027.sdg.controller;

import es.uji.ei1027.sdg.dao.AccionDao;
import es.uji.ei1027.sdg.dao.IniciativaDao;
import es.uji.ei1027.sdg.dao.MiembroUjiDao;
import es.uji.ei1027.sdg.dao.ParticipanteAccionDao;
import es.uji.ei1027.sdg.model.MiembroUji;
import es.uji.ei1027.sdg.model.ParticipanteAccion;
import es.uji.ei1027.sdg.model.ParticipanteIniciativa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/participanteAccion")
public class ParticipanteAccionController {
    private ParticipanteAccionDao participanteAccionDao;

    private AccionDao accionDao;
    private MiembroUjiDao miembroUjiDao;
    private IniciativaDao iniciativaDao;

    @Autowired
    public void setParticipanteAccionDao(ParticipanteAccionDao participanteAccionDao) {
        this.participanteAccionDao = participanteAccionDao;
    }
    @Autowired
    public void setAccionDao(AccionDao accionDao) {
        this.accionDao = accionDao;
    }

    @Autowired
    public void setMiembroUjiDao(MiembroUjiDao miembroUjiDao) {
        this.miembroUjiDao = miembroUjiDao;
    }

    @Autowired
    public void setIniciativaDao(IniciativaDao iniciativaDao) {
        this.iniciativaDao = iniciativaDao;
    }

    //Add zone
    @RequestMapping(value = "/add/{id_accion:.+}")
    public String addParticipanteAccion(Model model, @PathVariable int id_accion) {
        ParticipanteAccion participanteAccion = new ParticipanteAccion();
        participanteAccion.setId_accion(id_accion);
        model.addAttribute("participanteAccion", participanteAccion);
        return "participanteAccion/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("participanteAccion") ParticipanteAccion participanteAccion, @RequestParam String correo, BindingResult bindingResult, Model model) {

        if (correo.equals("")){
            model.addAttribute("errorAtributo", "El correo no es válido!");
            return "participanteAccion/add";
        }
        boolean res = miembroUjiDao.comprobarCorreo(correo);
        if(res) {
            MiembroUji miembroUji = miembroUjiDao.getMiembroUjiCorreo(correo);
            participanteAccion.setId_miembro(miembroUji.getId_miembro());
            boolean existe = accionDao.existeParticipante(miembroUji.getId_miembro(),participanteAccion.getId_accion());
            if(!existe) {
                participanteAccionDao.addParticipante(participanteAccion);
                return "redirect:/participanteAccion/list/"+participanteAccion.getId_accion();
            } else  {
                model.addAttribute("errorAtributo", "El correo no es válido!");
                return "participanteAccion/add";
            }
        }
        model.addAttribute("errorAtributo", "El correo no es válido!");
        return "participanteAccion/add";
    }

    @RequestMapping(value = "/list/{id_accion:.+}")
    public String listParticipantes(Model model, @PathVariable int id_accion) {
        model.addAttribute("nombreAccion", "PARTICIPANTES DE LA ACCION " + accionDao.getAccion(id_accion).getNombre() );
        List<ParticipanteAccion> participanteAccions = participanteAccionDao.getParticipantes(id_accion);
        model.addAttribute("id_miembrou",iniciativaDao.getIniciativa(accionDao.getAccion(id_accion).getId_iniciativa()).getId_miembro());
        model.addAttribute("id_iniciativa", id_accion);
        List<MiembroUji> miembrosAcciones = new ArrayList<>();
        for( ParticipanteAccion participanteAccion : participanteAccions) {
            miembrosAcciones.add(miembroUjiDao.getUjiMember(participanteAccion.getId_miembro()));
        }
        model.addAttribute("participantesAccion",miembrosAcciones);

        return "participanteAccion/list";
    }

    @GetMapping(value = "/delete/{id_miembro:.+}/{id_accion:.+}")
    public String deleteProcessParticipante(@PathVariable("id_miembro") int id_miembro, @PathVariable("id_accion") int id_accion) {
        participanteAccionDao.deleteParticipante(id_miembro, id_accion);
        return "redirect:/participanteAccion/list/"+ id_accion;
    }




}
