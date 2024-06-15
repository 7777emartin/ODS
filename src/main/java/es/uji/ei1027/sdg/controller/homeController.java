package es.uji.ei1027.sdg.controller;


import es.uji.ei1027.sdg.dao.*;
import es.uji.ei1027.sdg.model.Accion;
import es.uji.ei1027.sdg.model.Iniciativa;
import es.uji.ei1027.sdg.model.MiembroUji;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class homeController {

    private IniciativaDao iniciativaDao;
    private OdsDao odsDao;
    private AccionDao accionDao;
    private MiembroUjiDao miembroUjiDao;
    private ParticipanteIniciativaDao participanteIniciativaDao;
    private ParticipanteAccionDao participanteAccionDao;
    private ObjetivoDao objetivoDao;
    private  HomeDao homeDao;

    @Autowired
    public void setHomeDao(HomeDao homeDao) {
        this.homeDao = homeDao;
    }

    @Autowired
    public void setIniciativaDao(IniciativaDao iniciativaDao) {
        this.iniciativaDao = iniciativaDao;
    }

    @Autowired
    public void setParticipanteAccionDao(ParticipanteAccionDao participanteAccionDao) {
        this.participanteIniciativaDao = participanteIniciativaDao;
    }

    @Autowired
    public void setParticipanteIniciativaDao(ParticipanteIniciativaDao participanteIniciativaDao) {
        this.participanteIniciativaDao = participanteIniciativaDao;
    }

    @Autowired
    public void setMiembroUjiDao(MiembroUjiDao miembroUjiDao) {
        this.miembroUjiDao = miembroUjiDao;
    }

    @Autowired
    public void setObjetivoDao(ObjetivoDao objetivoDao) {
        this.objetivoDao = objetivoDao;
    }

    @Autowired
    public void setAccionDao(AccionDao accionDao) {
        this.accionDao = accionDao;
    }

    @Autowired
    public void setOdsDao(OdsDao odsDao) {
        this.odsDao = odsDao;
    }
    @RequestMapping("/")
    public String home(Model model, HttpSession session) {
        List<Accion> lista;
        MiembroUji user = (MiembroUji) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("num_iniciativa_progreso",homeDao.getNumeroIniciativasEnProgreso());
        model.addAttribute("num_iniciativa_total", homeDao.getNumeroIniciativasTotal());
        model.addAttribute("ultimaIniciativa", homeDao.getUltimaIniciativa().getNombre());
        model.addAttribute("usuarioMasIniciativa", miembroUjiDao.getUjiMember(homeDao.obtenerUsuarioMasIniciativas()).getNombre());
        int id_ods = homeDao.obtenerODSConMasIniciativas();
        model.addAttribute("odsMasIniciativas",id_ods +"- "+odsDao.getOds(id_ods).getNombre());
        model.addAttribute("iniciativasSinOds", homeDao.obtenerIniciativasSinAcciones());
        LocalDate fechaHoy = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaFormateada = fechaHoy.format(formatter);
        model.addAttribute("fechaHoy", fechaFormateada);
        int id_iniciativa = homeDao.getUltimaIniciativa().getId_iniciativa();
        lista = accionDao.getAccionesIniciativa(id_iniciativa);
        if(lista.size() > 0) {
            model.addAttribute("ultimaAccion", lista.get(0).getNombre());

        } else {
            model.addAttribute("ultimaAccion", "La última Iniciativa no ha añadido ninguna acción");
        }

        return "home2";
    }


}
