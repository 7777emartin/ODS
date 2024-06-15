package es.uji.ei1027.sdg.controller;

import es.uji.ei1027.sdg.dao.ObjetivoDao;
import es.uji.ei1027.sdg.dao.OdsDao;
import es.uji.ei1027.sdg.dao.SuscriptorDao;
import es.uji.ei1027.sdg.model.MiembroUji;
import es.uji.ei1027.sdg.model.Objetivo;
import es.uji.ei1027.sdg.model.Suscriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/objetivo")
public class ObjetivoController {
    private ObjetivoDao objetivoDao;

    private SuscriptorDao suscriptorDao;

    private OdsDao odsDao;

    @Autowired
    public void setObjetivoDao(ObjetivoDao objetivoDao) {
        this.objetivoDao = objetivoDao;
    }
    @Autowired
    public void setSuscriptorDao(SuscriptorDao suscriptorDao) {
        this.suscriptorDao = suscriptorDao;
    }
    @Autowired
    public void setOdsDao(OdsDao odsDao) { this.odsDao = odsDao; }

    //List zone
    @RequestMapping("/list/{id_ods:.+}")
    public String listObjetivo (Model model, @PathVariable int id_ods, HttpSession session) {
        MiembroUji user = (MiembroUji) session.getAttribute("user");

        model.addAttribute("objetivoList", objetivoDao.getObjetivosOds(id_ods));
        model.addAttribute("id_ods",id_ods);
        model.addAttribute("nombreOds", "LISTA DE OBJETIVOS DE " + odsDao.getOds(id_ods).getNombre());
        Suscriptor suscriptor = suscriptorDao.getOdsSuscriptorMiembro(user.getId_miembro(), id_ods);
        model.addAttribute("suscrito", suscriptor);

        return "objetivo/list";

    }
    @RequestMapping("/listODS/{id_ods:.+}")
    public String listObjetivoODS (Model model, @PathVariable int id_ods) {
        model.addAttribute("objetivoList", objetivoDao.getObjetivosOds(id_ods));
        return "objetivo/list";

    }
    //Add zone
    @RequestMapping(value = "/add/{id_ods:.+}")
    public String addObjetivo(Model model, @PathVariable int id_ods, HttpSession session) {
        if (session.getAttribute("rol") == null)
            return "/error";
        Objetivo objetivo = new Objetivo();
        objetivo.setId_ods(id_ods);
        model.addAttribute("objetivo", objetivo);
        return "objetivo/add";
    }
    @RequestMapping(value ="/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("objetivo") Objetivo objetivo, BindingResult bindingResult, HttpSession session) {
        if (session.getAttribute("rol") == null)
            return "/error";
        ObjetivoValidator objetivoValidator = new ObjetivoValidator();
        objetivoValidator.validate(objetivo, bindingResult);
        if (bindingResult.hasErrors())
            return "objetivo/add";
        objetivoDao.addObjetivo(objetivo);

        return "redirect:list/" + objetivo.getId_ods();
    }

    //Delete zone
    @RequestMapping(value = "/delete/{id_objetivo:.+}/{id_ods:.+}")
    public String processDelete(@PathVariable int id_objetivo,@PathVariable int id_ods, HttpSession session) {
        if (session.getAttribute("rol") == null)
            return "/error";
        objetivoDao.deleteObjetivo(id_objetivo);
        return "redirect:/objetivo/list/"+id_ods;
    }

    @RequestMapping(value = "/update/{id_objetivo:.+}", method = RequestMethod.GET)
    public String editUjiMember(Model model, @PathVariable int id_objetivo, HttpSession session) {
        if (session.getAttribute("rol") == null)
            return "/error";
        model.addAttribute("objetivo", objetivoDao.getObjetivo(id_objetivo));
        return "objetivo/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("objetivo") Objetivo objetivo,
                                      BindingResult bindingResult, HttpSession session) {
        if (session.getAttribute("rol") == null)
            return "/error";

        if (bindingResult.hasErrors())
            return "objetivo/update";
        objetivoDao.updateObjetivo(objetivo);

        return "redirect:list/" + objetivo.getId_ods();
    }
}
