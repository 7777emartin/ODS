package es.uji.ei1027.sdg.controller;


import es.uji.ei1027.sdg.dao.MiembroUjiDao;
import es.uji.ei1027.sdg.model.MiembroUji;
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
@RequestMapping("/miembrouji")
public class MiembroUjiController {
    private MiembroUjiDao miembroUjiDao;
    @Autowired
    public void setMiembroUjiDao(MiembroUjiDao miembroUjiDao) {
        this.miembroUjiDao = miembroUjiDao;
    }
    //List zone
    @RequestMapping("/list")
    public String listMiembroUji (HttpSession session, Model model) {
        if (session.getAttribute("miembroUji")==null){
            model.addAttribute("miembroUjiList",new MiembroUji());
            return "login";
        }
        model.addAttribute("miembroUjiList", miembroUjiDao.getUjiMemberList());
        return "miembroUji/list";

    }
    //Add zone
    @RequestMapping(value = "/add")
        public String addUjiMember(Model model) {
        model.addAttribute("miembrouji", new MiembroUji());
        return "miembroUji/add";
    }
    @RequestMapping(value ="/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("miembrouji") MiembroUji miembroUji, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "miembroUji/list";
        miembroUjiDao.addUjiMember(miembroUji);
        return "redirect:/";
    }

    //Delete zone
    @RequestMapping(value = "/delete/{id_miembro:.+}")
    public String processDelete(@PathVariable int id_miembro) {
        miembroUjiDao.deleteMember(id_miembro);
        return "redirect:/miembrouji/list";
    }

    @RequestMapping(value = "/update/{id_miembro}", method = RequestMethod.GET)
    public String editUjiMember(Model model, @PathVariable int id_miembro) {
        model.addAttribute("miembrouji", miembroUjiDao.getUjiMember(id_miembro));
        return "miembroUji/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("miembrouji") MiembroUji miembroUji,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "miembroUji/update";
        miembroUjiDao.updateUjiMember(miembroUji);
        return "redirect:list";
    }
}
