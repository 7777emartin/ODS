package es.uji.ei1027.sdg.controller;


import es.uji.ei1027.sdg.dao.ObjetivoDao;
import es.uji.ei1027.sdg.dao.OdsDao;
import es.uji.ei1027.sdg.dao.SuscriptorDao;
import es.uji.ei1027.sdg.model.Iniciativa;
import es.uji.ei1027.sdg.model.MiembroUji;
import es.uji.ei1027.sdg.model.Ods;
import es.uji.ei1027.sdg.model.Suscriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
@RequestMapping("/suscriptor")
public class SuscriptorController {
    private SuscriptorDao suscriptorDao;
    private ObjetivoDao objetivoDao;

    private OdsDao odsDao;
    @Autowired
    public void setSuscriptorDao(SuscriptorDao suscriptorDao) {
        this.suscriptorDao = suscriptorDao;
    }

    @Autowired
    public void setObjetivoDao(ObjetivoDao objetivoDao) {
        this.objetivoDao = objetivoDao;
    }

    @Autowired
    public void setOdsDao(OdsDao odsDao) {
        this.odsDao = odsDao;
    }

    @RequestMapping("/list")
    public String listSuscripciones(Model model, HttpSession session) {
        MiembroUji user = (MiembroUji) session.getAttribute("user");

        List<Suscriptor> suscriptors = suscriptorDao.getSuscriptoresMiembro(user.getId_miembro());
        List<Ods> misSuscripciones = new ArrayList<>();

        for (Suscriptor suscriptor:suscriptors){
            misSuscripciones.add(odsDao.getOds(suscriptor.getId_ods()));
        }

        model.addAttribute("tamResult", misSuscripciones.size());
        model.addAttribute("suscripciones", misSuscripciones);



        return "suscritptor/listSuscriPersonal";

    }

    @RequestMapping("/suscribirse/{id_ods:.+}")
    public String suscribirse(Model model, @PathVariable int id_ods, HttpSession session) {
        MiembroUji user = (MiembroUji) session.getAttribute("user");
        suscriptorDao.addSuscriptor(user.getId_miembro(), id_ods);

        List<Suscriptor> suscriptors = suscriptorDao.getSuscriptoresMiembro(user.getId_miembro());

        List<Ods> misSuscripciones = new ArrayList<>();

        for (Suscriptor suscriptor:suscriptors){
            misSuscripciones.add(odsDao.getOds(suscriptor.getId_ods()));
        }

        model.addAttribute("suscripciones", misSuscripciones);



        return "suscritptor/listSuscriPersonal";

    }


    @RequestMapping("/desuscribirmse/{id_ods:.+}")
    public String desuscribirmse(Model model, @PathVariable int id_ods, HttpSession session) {
        MiembroUji user = (MiembroUji) session.getAttribute("user");
        suscriptorDao.deleteSuscriptor(user.getId_miembro(), id_ods);

        List<Suscriptor> suscriptors = suscriptorDao.getSuscriptoresMiembro(user.getId_miembro());

        List<Ods> misSuscripciones = new ArrayList<>();

        for (Suscriptor suscriptor:suscriptors){
            misSuscripciones.add(odsDao.getOds(suscriptor.getId_ods()));
        }
        model.addAttribute("tamResult", misSuscripciones.size());
        model.addAttribute("suscripciones", misSuscripciones);



        return "suscritptor/listSuscriPersonal";

    }


}
