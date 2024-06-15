package es.uji.ei1027.sdg.controller;

import es.uji.ei1027.sdg.dao.IniciativaDao;
import es.uji.ei1027.sdg.dao.MiembroUjiDao;
import es.uji.ei1027.sdg.model.Iniciativa;
import es.uji.ei1027.sdg.model.MiembroUji;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    private MiembroUjiDao miembroUjiDao;
    @Autowired
    private IniciativaDao iniciativaDao;

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new MiembroUji());
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String checkLogin(@ModelAttribute("user") MiembroUji user,
                             BindingResult bindingResult, HttpSession session) {
        UserValidator userValidator = new UserValidator();
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "login";
        }
        user = miembroUjiDao.loadUserByUsername(user.getCorreo(), user.getPassword());
        if (user == null) {
            bindingResult.rejectValue("password", "badpw", "Contraseña incorrecta");
            return "login";
        }

        session.setAttribute("rol", user.getTipo());
        session.setAttribute("user", user);
        session.setAttribute("numeroPropuestas", iniciativaDao.getPropuestasPendientes().size());
        session.setAttribute("id_miembro", user.getId_miembro());
        if (session.getAttribute("nextUrl") != null) {
            return (String) session.getAttribute("nextUrl");
        }

        // Torna a la pàgina principal
        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}

