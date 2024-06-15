package es.uji.ei1027.sdg.controller;

import es.uji.ei1027.sdg.dao.*;
import es.uji.ei1027.sdg.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/accion")
public class AccionController {
    private AccionDao accionDao;
    private IniciativaDao iniciativaDao;
    private ObjetivoDao objetivoDao;
    private ParticipanteAccionDao participanteAccionDao;
    private List<Accion> accionsActuales = new LinkedList<>();
    @Autowired
    public void setAccionDao(AccionDao accionDao) {
        this.accionDao = accionDao;
    }
    @Autowired
    public void setIniciativaDao(IniciativaDao iniciativaDao) {
        this.iniciativaDao = iniciativaDao;
    }
    @Autowired
    public void setObjetivoDao(ObjetivoDao objetivoDao) { this.objetivoDao = objetivoDao;
    }
    @Autowired
    public void setParticipanteIniciativaDao(ParticipanteAccionDao participanteAccionDao) {
        this.participanteAccionDao = participanteAccionDao;
    }
    @RequestMapping("/list")
    public String listAccion(Model model) {
        model.addAttribute("acciones", accionDao.getAcciones());

        return "accion/list";
    }

    @RequestMapping("/list/{id_iniciativa:.+}")
    public String listAccionIniciativa(Model model, @PathVariable int id_iniciativa, HttpSession session) {
        model.addAttribute("accionList", accionDao.getAccionesIniciativa(id_iniciativa));
        model.addAttribute("iniciativa", iniciativaDao.getIniciativa(id_iniciativa) );
        model.addAttribute("nombreIniciativa", "ACCIONES DE "  +  iniciativaDao.getIniciativa(id_iniciativa).getNombre());
        model.addAttribute("fechaHoy", LocalDate.now());
        return "accion/list";
    }
    //Consultar  info acción
    @RequestMapping("/infoAccion/{id_accion:.+}")
    public String infoAccion(Model model, @PathVariable int id_accion) {
        model.addAttribute("accion", accionDao.getAccion(id_accion));
        model.addAttribute("NumParticipantes","Número de Participantes: "+ participanteAccionDao.getParticipantes(id_accion).size());
        Accion accion = accionDao.getAccion(id_accion);
        int id_objetivo = accion.getId_objetivo();
        model.addAttribute("objetivo",objetivoDao.getObjetivo(id_objetivo));
        return "accion/infoAccion";
    }

    //Add zone
    @RequestMapping(value = "/add/{id_iniciativa:.+}")
    public String addAccion(Model model, @PathVariable int id_iniciativa, HttpSession session) {
        if (session.getAttribute("rol") == null)
            return "/error";
        model.addAttribute("accion", new Accion());
        model.addAttribute("id_iniciativa", id_iniciativa);
        model.addAttribute("objetivos", objetivoDao.getObjetivosOds(iniciativaDao.getIniciativa(id_iniciativa).getId_ods()));
        return "accion/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addAction(@ModelAttribute("accion") Accion accion, @RequestParam int id_iniciativa, BindingResult bindingResult, HttpSession session, Model model) {
        if (session.getAttribute("rol") == null)
            return "/error";
        AccionValidator accionValidator = new AccionValidator();
        accionValidator.validate(accion, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("id_iniciativa", id_iniciativa);
            model.addAttribute("objetivos", objetivoDao.getObjetivosOds(iniciativaDao.getIniciativa(id_iniciativa).getId_ods()));
            return "accion/add";
        }

        accionDao.addAccion(accion);
        return "redirect:/accion/list/" + id_iniciativa;
    }

    //Acción creado con iniciativa
    @RequestMapping(value = "/addPrimera/{id_iniciativa:.+}")
    public String addAccionFirstTime(Model model, @PathVariable int id_iniciativa,HttpSession session) {
        if (session.getAttribute("rol") == null)
            return "/error";
        model.addAttribute("accion", new Accion());
        model.addAttribute("id_iniciativa", id_iniciativa);
        model.addAttribute("mensaje", "La iniciativa \" "+ iniciativaDao.getIniciativa(id_iniciativa).getNombre()+"\" se ha creado correctamente.");
        model.addAttribute("objetivos", objetivoDao.getObjetivosOds(iniciativaDao.getIniciativa(id_iniciativa).getId_ods()));
        return "accion/addPrimera";
    }

    @RequestMapping(value = "/addPrimera", method = RequestMethod.POST)
    public String addActionFirstTime(@ModelAttribute("accion") Accion accion, @RequestParam int id_iniciativa, BindingResult bindingResult, HttpSession session, Model model) {
        if (session.getAttribute("rol") == null)
            return "/error";
        AccionValidator accionValidator = new AccionValidator();
        accionValidator.validate(accion, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("id_iniciativa", id_iniciativa);
            model.addAttribute("objetivos", objetivoDao.getObjetivosOds(iniciativaDao.getIniciativa(id_iniciativa).getId_ods()));
            return "accion/addPrimera";
        }

        accionDao.addAccion(accion);
        return "redirect:/participanteIniciativa/addPrimera/" + id_iniciativa;
    }

    //zona de borrado
    @RequestMapping(value = "/delete/{id_accion}")
    public String processDelete(@PathVariable int id_accion, HttpSession session) {
        if (session.getAttribute("rol") == null)
            return "/error";
        accionDao.deleteAccion(id_accion);
        return "redirect:/accion/list";
    }

    @RequestMapping(value = "update/{id_accion}", method = RequestMethod.GET)
    public String editAccion(Model model,@PathVariable int id_accion, HttpSession session) {

        if (session.getAttribute("rol") == null)
            return "/error";
        model.addAttribute("accion", accionDao.getAccion(id_accion));
        model.addAttribute("id_accion",id_accion);
        return "accion/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("accion") Accion accion, BindingResult bindingResult, HttpSession session) {
        if (session.getAttribute("rol") == null)
            return "/error";
        AccionValidator accionValidator = new AccionValidator();
        accionValidator.validate(accion, bindingResult);
        if (bindingResult.hasErrors())
            return "accion/update";

        accionDao.updateAccion(accion);
        return "redirect:/accion/list/" + accion.getId_iniciativa();
    }


    @RequestMapping(value = "/finalizar/{id_accion:.+}")
    public String processFinalize(@PathVariable int id_accion ,HttpSession session) {
        if (session.getAttribute("rol") == null)
            return "/error";

        if(accionDao.finalizarAccion(id_accion))
            return "redirect:/accion/list/" + accionDao.getAccion(id_accion).getId_iniciativa();
        return "redirect:";
    }


}
