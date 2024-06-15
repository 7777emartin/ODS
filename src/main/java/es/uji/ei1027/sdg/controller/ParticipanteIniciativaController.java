package es.uji.ei1027.sdg.controller;

import es.uji.ei1027.sdg.dao.AccionDao;
import es.uji.ei1027.sdg.dao.IniciativaDao;
import es.uji.ei1027.sdg.dao.MiembroUjiDao;
import es.uji.ei1027.sdg.dao.ParticipanteIniciativaDao;
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
@RequestMapping("/participanteIniciativa")
public class ParticipanteIniciativaController {
    private ParticipanteIniciativaDao participanteIniciativaDao;
    private MiembroUjiDao miembroUjiDao;
    private IniciativaDao iniciativaDao;
    private AccionDao accionDao;

    @Autowired
    public void setParticipanteIniciativaDao(ParticipanteIniciativaDao participanteIniciativaDao) {
        this.participanteIniciativaDao = participanteIniciativaDao;
    }
    @Autowired
    public void setAccionDao(AccionDao accionDao) {this.accionDao = accionDao;}

    @Autowired
    public void setMiembroUjiDao(MiembroUjiDao miembroUjiDao) {
        this.miembroUjiDao = miembroUjiDao;
    }

    @Autowired
    public void setIniciativaDao(IniciativaDao iniciativaDao) { this.iniciativaDao = iniciativaDao; }








    //Add zone
    @RequestMapping(value = "/add/{id_iniciativa}")
    public String addParticipanteIniciativa(Model model,@PathVariable int id_iniciativa) {
        ParticipanteIniciativa participanteIniciativa = new ParticipanteIniciativa();
        participanteIniciativa.setId_iniciativa(id_iniciativa);
        model.addAttribute("participanteIniciativa",participanteIniciativa);
        return "participanteIniciativa/add";
    }

    @RequestMapping(value = "/add")
    public String processAddParticipanteSubmit(@ModelAttribute("participanteIniciativa") ParticipanteIniciativa participanteIniciativa, @RequestParam String correo, BindingResult bindingResult, Model model) {
        if (correo.equals("")){
            model.addAttribute("errorAtributo", "El correo no es válido!");
            return "participanteIniciativa/add";
        }
        boolean res = miembroUjiDao.comprobarCorreo(correo);
        if(res) {
            MiembroUji miembroUji = miembroUjiDao.getMiembroUjiCorreo(correo);
            participanteIniciativa.setId_miembro(miembroUji.getId_miembro());
            boolean existe = iniciativaDao.existeParticipante(miembroUji.getId_miembro(),participanteIniciativa.getId_iniciativa());
            if(!existe) {
                participanteIniciativaDao.addParticipante(participanteIniciativa);
                return "redirect:/participanteIniciativa/list" + "/" + participanteIniciativa.getId_iniciativa();
            } else  {
                model.addAttribute("errorAtributo", "El correo no es válido!");
                return "participanteIniciativa/add";
            }
        }
        model.addAttribute("errorAtributo", "El correo no es válido!");
        return "participanteIniciativa/add";
    }





    @RequestMapping(value = "/addPrimera/{id_iniciativa}")
    public String addParticipanteIniciativaFirstTime(Model model,@PathVariable int id_iniciativa) {
        ParticipanteIniciativa participanteIniciativa = new ParticipanteIniciativa();
        participanteIniciativa.setId_iniciativa(id_iniciativa);
        model.addAttribute("participanteIniciativa",participanteIniciativa);
        model.addAttribute("mensaje", "La Acción se ha creado correctamente.");
        return "participanteIniciativa/addPrimera";
    }

    @RequestMapping(value = "/addPrimera")
    public String processAddParticipanteFirstTime(@ModelAttribute("participanteIniciativa") ParticipanteIniciativa participanteIniciativa, @RequestParam String correo, BindingResult bindingResult, Model model) {
        if (correo.equals("")){
            model.addAttribute("errorAtributo", "El correo no es válido!");
            return "participanteIniciativa/addPrimera";
        }
        boolean res = miembroUjiDao.comprobarCorreo(correo);
        if(res) {
            MiembroUji miembroUji = miembroUjiDao.getMiembroUjiCorreo(correo);
            participanteIniciativa.setId_miembro(miembroUji.getId_miembro());
            boolean existe = iniciativaDao.existeParticipante(miembroUji.getId_miembro(),participanteIniciativa.getId_iniciativa());
            if(!existe) {
                participanteIniciativaDao.addParticipante(participanteIniciativa);
                return "redirect:/iniciativa/filtroPersonal?id_ods=-1&state=Todos&orden=nuevo";
            } else  {
                model.addAttribute("errorAtributo", "El correo no es válido!");
                return "participanteIniciativa/addPrimera";
            }
        }
        model.addAttribute("errorAtributo", "El correo no es válido!");
        return "participanteIniciativa/addPrimera";
    }




    @GetMapping(value = "/delete/{id_miembro:.+}/{id_iniciativa:.+}")
    public String deleteProcessParticipante(@PathVariable("id_miembro") int id_miembro, @PathVariable("id_iniciativa") int id_iniciativa) {
        participanteIniciativaDao.deleteParticipante(id_miembro, id_iniciativa);
        return "redirect:/participanteIniciativa/list" + "/" + id_iniciativa;
    }

    @RequestMapping(value = "/list/{id_iniciativa:.+}")
    public String listParticipantes(Model model, @PathVariable int id_iniciativa) {
        model.addAttribute("id_iniciativa",id_iniciativa);
        model.addAttribute("nombreIniciativa", "PARTICIPANTES DE LA INICIATIVA " + iniciativaDao.getIniciativa(id_iniciativa).getNombre() );
        model.addAttribute("id_miembrou",iniciativaDao.getIniciativa(id_iniciativa).getId_miembro());
        List<ParticipanteIniciativa> participantesIniciativas = participanteIniciativaDao.getParticipantes(id_iniciativa);
        List<MiembroUji> miembrosIniciativas = new ArrayList<>();
        for( ParticipanteIniciativa participanteIniciativa : participantesIniciativas) {
            miembrosIniciativas.add(miembroUjiDao.getUjiMember(participanteIniciativa.getId_miembro()));
        }
        model.addAttribute("participantesIniciativa",miembrosIniciativas);

        return "participanteIniciativa/list";
    }


}
