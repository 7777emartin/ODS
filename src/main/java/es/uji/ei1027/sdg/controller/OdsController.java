package es.uji.ei1027.sdg.controller;

import es.uji.ei1027.sdg.dao.IniciativaDao;
import es.uji.ei1027.sdg.dao.ObjetivoDao;
import es.uji.ei1027.sdg.dao.OdsDao;
import es.uji.ei1027.sdg.model.Ods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/ods")
public class OdsController {
    private OdsDao odsDao;
    private IniciativaDao iniciativaDao;
    private ObjetivoDao objetivoDao;

    @Autowired
    public void setOdsDao(OdsDao odsDao) {
        this.odsDao = odsDao;
    }
    @Autowired
    public void setObjetivoDao(ObjetivoDao objetivoDao) {
        this.objetivoDao = objetivoDao;
    }
    @Autowired
    public void setIniciativaDao(IniciativaDao iniciativaDao) {
        this.iniciativaDao = iniciativaDao;
    }

    //List zone
    @RequestMapping("/list")
    public String listOds(Model model) {
        model.addAttribute("odsList", odsDao.getListOds());
        return "ods/list";

    }


    //Add zone
    @RequestMapping(value = "/add")
    public String addOds(Model model, HttpSession session) {
        if (!session.getAttribute("rol").equals("PDI") || !session.getAttribute("rol").equals("PAS"))
            return "/error";
        List<Ods> lista = odsDao.getListOds();
        model.addAttribute("ultimoNumero", lista.size() + 1);
        model.addAttribute("ods", new Ods());
        return "ods/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("ods") Ods ods, Model model, BindingResult bindingResult) {

        List<Ods> lista = odsDao.getListOds();
        ods.setNumero(lista.size()+1);
        OdsValidator odsValidator = new OdsValidator();
        odsValidator.validate(ods, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("ultimoNumero", lista.size() + 1);
            return "ods/add";
        }
        odsDao.addOds(ods);
        return "redirect:/ods/list";
    }

    //Delete zone
    @RequestMapping(value = "/delete/{id_ods:.+}")
    public String processDelete(@PathVariable int id_ods) {
        if(iniciativaDao.getIniciativaOds(id_ods) == null || objetivoDao.getObjetivosOds(id_ods).size() != 0 ){
            return "redirect:/ods/list";
        }
        odsDao.deleteOds(id_ods);
        return "redirect:/ods/list";
    }

    @RequestMapping(value = "/update/{id_ods:.+}", method = RequestMethod.GET)
    public String editOds(Model model, @PathVariable int id_ods) {
        model.addAttribute("ods", odsDao.getOds(id_ods));

        return "ods/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("ods") Ods ods,
                                      BindingResult bindingResult) {
        OdsValidator odsValidator = new OdsValidator();

        odsValidator.validate(ods, bindingResult);
        if (bindingResult.hasErrors())
            return "ods/update";
        odsDao.updateOds(ods);

        return "redirect:/ods/list";
    }
}
