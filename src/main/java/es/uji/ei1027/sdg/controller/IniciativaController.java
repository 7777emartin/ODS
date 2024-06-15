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
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/iniciativa")
public class IniciativaController {
    private IniciativaDao iniciativaDao;
    private OdsDao odsDao;
    private AccionDao accionDao;
    private MiembroUjiDao miembroUjiDao;
    private ParticipanteIniciativaDao participanteIniciativaDao;
    private ParticipanteAccionDao participanteAccionDao;
    private ObjetivoDao objetivoDao;

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

    @RequestMapping("/masInfo/{id_iniciativa:.+}")
    public String listAccionIniciativa(Model model, @PathVariable int id_iniciativa) {
        model.addAttribute("accionList", accionDao.getAccionesIniciativa(id_iniciativa));
        model.addAttribute("iniciativa", iniciativaDao.getIniciativa(id_iniciativa));
        model.addAttribute("ods",iniciativaDao.getIniciativa(id_iniciativa).getId_ods() +"- " + odsDao.getOds(iniciativaDao.getIniciativa(id_iniciativa).getId_ods()).getNombre());
        model.addAttribute("NumAcciones", "Número de Acciones: " + accionDao.getAccionesIniciativa(id_iniciativa).size());
        model.addAttribute("NumParticipantes","Número de Participantes: " + participanteIniciativaDao.getParticipantes(id_iniciativa).size());
        model.addAttribute("NombreCreador", miembroUjiDao.getUjiMember(iniciativaDao.getIniciativa(id_iniciativa).getId_miembro()).getNombre());
        return "iniciativa/masInfo";
    }


    @RequestMapping("/gestionar/{id_iniciativa:.+}")
    public String listaMasInfo(Model model, @PathVariable int id_iniciativa, HttpSession session) {
        if (session.getAttribute("rol") == null)
            return "/error";
        model.addAttribute("iniciativa", iniciativaDao.getIniciativa(id_iniciativa));
        model.addAttribute("id_iniciativa", id_iniciativa);

        return "iniciativa/update";

    }


    private int pageLength = 8;

    @RequestMapping("/pagedlist")
    public String listaIniciativaPaged(Model model, @RequestParam("page") Optional<Integer> page, HttpSession session) {
        model.addAttribute("odslist", odsDao.getListOds());
        List<Iniciativa> iniciativas = iniciativaDao.getIniciativasPublico();
        Collections.reverse(iniciativas);
        ArrayList<ArrayList<Iniciativa>> iniciaPaged = new ArrayList<ArrayList<Iniciativa>>();
        int ini = 0;
        int fin = pageLength - 1;
        while (fin < iniciativas.size()) {
            iniciaPaged.add(new ArrayList<Iniciativa>(iniciativas.subList(ini, fin)));
            ini += pageLength;
            fin += pageLength;
        }
        iniciaPaged.add(new ArrayList<Iniciativa>(iniciativas.subList(ini, iniciativas.size())));
        model.addAttribute("iniciativasPaged", iniciaPaged);

        int totalPages = iniciaPaged.size();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(0, totalPages - 1)// el -2 no se si está bien de momento no da ningún error
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        int currentPage = page.orElse(0);
        model.addAttribute("selectedPage", currentPage);
        return "iniciativa/list";
    }

    @GetMapping("/filtro")
    public String listIniciativa(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam(value = "state", required = false) String estado,
                                 @RequestParam(value = "orden", required = false) String orden, @RequestParam(value = "id_ods", required = false) Integer id_ods) {
        List<Iniciativa> iniciativas;
        getListODS(model, estado, orden, id_ods);

        if (id_ods == -1) {
            if (estado.equals("Todos")) {
                if (orden.equals("antiguo")) {
                    // Queremos todas las iniciativas sin filtrar con orden sin reverse
                    iniciativas = iniciativaDao.getIniciativasPublico();
                } else {
                    // Queremos todas las iniciativas sin filtrar con orden con reverse
                    iniciativas = iniciativaDao.getIniciativasPublico();
                    Collections.reverse(iniciativas);
                }
            } else {
                if (orden.equals("antiguo")) {
                    // Queremos todas las iniciativas con un estado específico y orden sin reverse
                    iniciativas = iniciativaDao.getIniciativaList_NoODS_SiEstado(estado);
                } else {
                    // Queremos todas las iniciativas con un estado específico y orden con reverse
                    iniciativas = iniciativaDao.getIniciativaList_NoODS_SiEstado(estado);
                    Collections.reverse(iniciativas);
                }
            }
        } else {
            if (orden.equals("antiguo")) {
                if (estado.equals("Todos")) {
                    // Queremos las iniciativas con un ODS específico sin filtrar por estado y con orden sin reverse
                    iniciativas = iniciativaDao.getIniciativaList_SiODS_NoEstado(id_ods);
                } else {
                    // Queremos las iniciativas con un ODS específico y un estado específico con orden sin reverse
                    iniciativas = iniciativaDao.getIniciativaListODSEstado(id_ods, estado);
                }
            } else {
                if(estado.equals("Todos")) {
                    // Queremos las iniciativas con un ODS específico sin filtrar por estado y con orden sin reverse
                    iniciativas = iniciativaDao.getIniciativaList_SiODS_NoEstado(id_ods);
                } else {
                    // Queremos las iniciativas con un ODS específico y un estado específico con orden con reverse
                    iniciativas = iniciativaDao.getIniciativaListODSEstado(id_ods, estado);
                    Collections.reverse(iniciativas);
                }
            }
        }


        model.addAttribute("tamResult", iniciativas.size());
        ArrayList<ArrayList<Iniciativa>> iniciaPaged = new ArrayList<ArrayList<Iniciativa>>();
        int ini = 0;
        int fin = pageLength - 1;
        while (fin < iniciativas.size()) {
            iniciaPaged.add(new ArrayList<Iniciativa>(iniciativas.subList(ini, fin)));
            ini += pageLength;
            fin += pageLength;
        }
        iniciaPaged.add(new ArrayList<Iniciativa>(iniciativas.subList(ini, iniciativas.size())));
        model.addAttribute("iniciativasPaged", iniciaPaged);

        // Paso 2: Crear la lista de numeros de página
        int totalPages = iniciaPaged.size();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(0, totalPages -1)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        // Paso 3: selectedPage: usar parametro opcional page, o en su defecto, 1
        int currentPage = page.orElse(0);
        model.addAttribute("selectedPage", currentPage);
        return "iniciativa/listFiltro";


    }


//Esto no va bien, tiene que ser sólo los ods, de ese usuario.

    @GetMapping("/filtroPersonal")
    public String listIniciativaUsuarioFiltrado(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam(value = "state", required = false) String estado,
                                                @RequestParam(value = "orden", required = false) String orden, @RequestParam(value = "id_ods", required = false) Integer id_ods, HttpSession session) {
        if (session.getAttribute("rol") == null)
            return "/error";

        MiembroUji user = (MiembroUji) session.getAttribute("user");

        getListODS(model, estado, orden, id_ods);
        List<Iniciativa> iniciativas;


        if (id_ods == -1) {
            if (estado.equals("Todos")) {
                if (orden.equals("nuevo")) {
                    // Queremos todas las iniciativas sin filtrar con orden sin reverse
                    iniciativas = iniciativaDao.getIniciativasPublicoPersonal(user.getId_miembro());
                } else {
                    // Queremos todas las iniciativas sin filtrar con orden con reverse
                    iniciativas = iniciativaDao.getIniciativasPublicoPersonal(user.getId_miembro());
                    Collections.reverse(iniciativas);
                }
            } else { //estado específico
                if (orden.equals("nuevo")) {
                    // Queremos todas las iniciativas con un estado específico y orden sin reverse
                    iniciativas = iniciativaDao.getIniciativaList_NoODS_SiEstadoPersonal(user.getId_miembro(),estado);
                } else {
                    // Queremos todas las iniciativas con un estado específico y orden con reverse
                    iniciativas = iniciativaDao.getIniciativaList_NoODS_SiEstadoPersonal(user.getId_miembro(),estado);
                    Collections.reverse(iniciativas);
                }
            }
        } else {
            if (orden.equals("nuevo")) {
                if (estado.equals("Todos")) {
                    // Queremos las iniciativas con un ODS específico sin filtrar por estado y con orden sin reverse
                    iniciativas = iniciativaDao.getIniciativaList_SiODS_NoEstadoPersonal(user.getId_miembro(),id_ods);
                } else {
                    // Queremos las iniciativas con un ODS específico y un estado específico con orden sin reverse
                    iniciativas = iniciativaDao.getIniciativaListODSEstadoPersonal(user.getId_miembro(),id_ods, estado);
                }
            } else {
                if(estado.equals("Todos")) {
                    // Queremos las iniciativas con un ODS específico sin filtrar por estado y con orden sin reverse
                    iniciativas = iniciativaDao.getIniciativaList_SiODS_NoEstadoPersonal(user.getId_miembro(),id_ods);
                } else {
                    // Queremos las iniciativas con un ODS específico y un estado específico con orden con reverse
                    iniciativas = iniciativaDao.getIniciativaListODSEstadoPersonal(user.getId_miembro(),id_ods, estado);
                    Collections.reverse(iniciativas);
                }
            }
        }


        model.addAttribute("tamResult", iniciativas.size());
        ArrayList<ArrayList<Iniciativa>> iniciaPaged = new ArrayList<ArrayList<Iniciativa>>();
        int ini = 0;
        int fin = pageLength - 1;
        while (fin < iniciativas.size()) {
            iniciaPaged.add(new ArrayList<Iniciativa>(iniciativas.subList(ini, fin)));
            ini += pageLength;
            fin += pageLength;
        }
        iniciaPaged.add(new ArrayList<Iniciativa>(iniciativas.subList(ini, iniciativas.size())));
        model.addAttribute("iniciativasPaged", iniciaPaged);

        // Paso 2: Crear la lista de numeros de página
        int totalPages = iniciaPaged.size();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(0, totalPages-1)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        // Paso 3: selectedPage: usar parametro opcional page, o en su defecto, 1
        int currentPage = page.orElse(0);
        model.addAttribute("selectedPage", currentPage);
        return "iniciativa/listaPersonal";


    }

    private void getListODS(Model model, @RequestParam(value = "state", required = false) String estado, @RequestParam(value = "orden", required = false) String orden, @RequestParam(value = "id_ods", required = false) Integer id_ods) {
        model.addAttribute("odslist", odsDao.getListOds());
        model.addAttribute("orden", orden);
        model.addAttribute("state", estado);
        model.addAttribute("estado_filtro", estado);
        model.addAttribute("estado_filtro_value", estado);
        model.addAttribute("id_ods", id_ods);
        model.addAttribute("ods_nombreFiltro", id_ods + "- " + ((id_ods != -1) ? odsDao.getOds(id_ods).getNombre() : ""));
        if(id_ods == -1 )
            model.addAttribute("ods_nombreFiltro","Todos");

        model.addAttribute("ods_nombreFiltro_value", id_ods);
        model.addAttribute("orden_filtro_value", orden);
        model.addAttribute("orden_filtro", orden.equals("nuevo") ? "Más nuevos primero" : "Más antiguos primero");

        List<Iniciativa> iniciativas;
    }


    //Gestion de propuestas
    @RequestMapping("/listPropuestas")
    public String listPropuestas(Model model, @RequestParam(value = "orden", required = false) String orden, @RequestParam(value = "id_ods", required = false) Integer id_ods,HttpSession session) {
        if (session.getAttribute("rol") == null)
        {
            return "/error";
        } else if (session.getAttribute("rol").equals("PDI") || session.getAttribute("rol").equals("PAS") ) {


            model.addAttribute("odslist", odsDao.getListOds());
            model.addAttribute("odslist", odsDao.getListOds());

            if (id_ods == null)
                id_ods = -1;
            if(orden == null)
                orden = "antiguo";


            model.addAttribute("orden", orden);
            model.addAttribute("id_ods", id_ods);
            model.addAttribute("ods_nombreFiltro", id_ods + "- " + ((id_ods != -1) ? odsDao.getOds(id_ods).getNombre() : ""));
            if (id_ods == -1)
                model.addAttribute("ods_nombreFiltro", "Todos");

            model.addAttribute("ods_nombreFiltro_value", id_ods);
            model.addAttribute("orden_filtro_value", orden);
            model.addAttribute("orden_filtro", orden.equals("nuevo") ? "Más nuevos primero" : "Más antiguos primero");


            List<Iniciativa> iniciativas = new ArrayList<>();

            if (id_ods == -1) {
                iniciativas = iniciativaDao.getPropuestasPendientes();
                if (!orden.equals("antiguo")) {
                    Collections.reverse(iniciativas);
                }
            } else {
                iniciativas = iniciativaDao.getPropuestasPendientesByODS(id_ods);
                if (!orden.equals("antiguo")) {
                    Collections.reverse(iniciativas);
                }
            }

            model.addAttribute("iniciativaList", iniciativas);
            model.addAttribute("tamResult", iniciativas.size());
            return "iniciativa/listPropuestas";
        } else {
            return "/error";
        }
    }

    @RequestMapping("/acepta/{id_iniciativa:.+}")
    public String aceptaPropuesta(HttpSession session, Model model, @PathVariable int id_iniciativa, @RequestParam Integer usuario) {
        if (session.getAttribute("rol") == null)
            return "/error";
        Iniciativa iniciativa = iniciativaDao.getIniciativa(id_iniciativa);
        iniciativa.setEstado("En progreso");
        iniciativaDao.updateIniciativa(iniciativa);
        int numero = iniciativaDao.getPropuestasPendientes().size();
        session.setAttribute("numeroPropuestas", numero);
        enviaMail(miembroUjiDao.getUjiMember(usuario).getNombre(),"ACEPTANDO");
        return "redirect:/iniciativa/listPropuestas";

    }

    @RequestMapping("/rechaza/{id_iniciativa:.+}")
    public String rechazaPropuesta(HttpSession session, @PathVariable int id_iniciativa, @RequestParam Integer usuario) {
        if (session.getAttribute("rol") == null)
            return "/error";
        Iniciativa iniciativa = iniciativaDao.getIniciativa(id_iniciativa);
        iniciativa.setEstado("Rechazado");
        iniciativaDao.updateIniciativa(iniciativa);
        int numero = iniciativaDao.getPropuestasPendientes().size();
        session.setAttribute("numeroPropuestas", numero);
        enviaMail(miembroUjiDao.getUjiMember(usuario).getNombre(),"RECHAZANDO");
        return "redirect:/iniciativa/listPropuestas";

    }

    //Add zone
    @RequestMapping(value = "/add")
    public String addIniciativa(Model model, HttpSession session) {
        if (session.getAttribute("rol") == null)
            return "/error";
        MiembroUji user = (MiembroUji) session.getAttribute("user");
        Iniciativa iniciativa = new Iniciativa();
        iniciativa.setId_miembro(user.getId_miembro());
        model.addAttribute("odslist", odsDao.getListOds());
        model.addAttribute("iniciativa", iniciativa);
        model.addAttribute("diaHoy", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        return "iniciativa/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("iniciativa") Iniciativa iniciativa, BindingResult bindingResult, HttpSession session, Model model) {
        if (session.getAttribute("rol") == null)
            return "/error";
        IniciativaValidator iniciativaValidator = new IniciativaValidator();
        iniciativaValidator.validate(iniciativa, bindingResult);

        if(bindingResult.hasErrors()) {
            MiembroUji user = (MiembroUji) session.getAttribute("user");
            iniciativa.setId_miembro(user.getId_miembro());
            model.addAttribute("odslist", odsDao.getListOds());
            model.addAttribute("iniciativa", iniciativa);
            model.addAttribute("diaHoy", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            return "iniciativa/add";
        }
        MiembroUji user = (MiembroUji) session.getAttribute("user");
        iniciativa.setId_miembro(user.getId_miembro());
        iniciativaDao.addIniciativa(iniciativa);
        Iniciativa iniciativa1 = iniciativaDao.getUltimaIniciativa(user.getId_miembro());
        return "redirect:/accion/addPrimera/" + iniciativa1.getId_iniciativa();
    }

    @RequestMapping("/rellenarDatos/{id_iniciativa:.+}")
    public String rellenarDatos(HttpSession session, Model model, @PathVariable int id_iniciativa) {
        if (session.getAttribute("rol") == null)
            return "/error";
        Accion accion = new Accion();
        accion.setId_iniciativa(id_iniciativa);
        model.addAttribute("objetivos", objetivoDao.getObjetivosOds(iniciativaDao.getIniciativa(id_iniciativa).getId_ods()));
        model.addAttribute("accion", accion);

        return "iniciativa/primeraAccion";
    }



    //Delete zone
    @RequestMapping(value = "/delete/{id_iniciativa:.+}")
    public String processDelete(@PathVariable int id_iniciativa, HttpSession session) {
        if (session.getAttribute("rol") == null)
            return "/error";
        iniciativaDao.deleteIniciativa(id_iniciativa);
        return "redirect:/iniciativa/pagedlist";
    }

    @RequestMapping(value = "/finalizar/{id_iniciativa:.+}")
    public String processFinalize(@PathVariable int id_iniciativa,HttpSession session) {
        if (session.getAttribute("rol") == null)
            return "/error";
        iniciativaDao.finalizarPropuesta(id_iniciativa);
        iniciativaDao.updateFechaFinIniciativa(id_iniciativa);
        MiembroUji user = (MiembroUji) session.getAttribute("user");
        if(user.getTipo().equals("Estudiante"))
            return "redirect:/iniciativa/filtroPersonal?id_ods=-1&state=Todos&orden=nuevo";
        return "redirect:/iniciativa/pagedlist";
    }


    @RequestMapping(value = "/update/{id_iniciativa:.+}", method = RequestMethod.GET)
    public String editIniciativa(Model model, @PathVariable int id_iniciativa, HttpSession session) {
        if (session.getAttribute("rol") == null)
            return "/error";
        model.addAttribute("iniciativa", iniciativaDao.getIniciativa(id_iniciativa));
        return "iniciativa/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("iniciativa") Iniciativa iniciativa,HttpSession session,
                                      BindingResult bindingResult) {
        if (session.getAttribute("rol") == null)
            return "/error";

        IniciativaValidator iniciativaValidator = new IniciativaValidator();
        iniciativaValidator.validate(iniciativa, bindingResult);
        if (bindingResult.hasErrors())
            return "iniciativa/update";
        iniciativaDao.updateIniciativa(iniciativa);
        return "redirect:/iniciativa/filtroPersonal?id_ods=-1&state=Todos&orden=nuevo";
    }

    @RequestMapping(value = "/updateAdmin/{id_iniciativa:.+}", method = RequestMethod.GET)
    public String editIniciativaAdmin(Model model, @PathVariable int id_iniciativa, HttpSession session) {
        if (session.getAttribute("rol") == null)
            return "/error";
        model.addAttribute("iniciativa", iniciativaDao.getIniciativa(id_iniciativa));
        return "iniciativa/updateAdmin";
    }
    @RequestMapping(value = "/updateAdmin", method = RequestMethod.POST)
    public String processUpdateSubmitAdmin(@ModelAttribute("iniciativa") Iniciativa iniciativa, HttpSession session,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "iniciativa/update";
        iniciativaDao.updateIniciativa(iniciativa);
        return "redirect:/iniciativa/pagedlist";
    }



    //Esto simula un programa que envía los correos.
    private boolean enviaMail (String usuario, String Resultado ) {
        System.out.println("Enviando correo al usuario "+ usuario+ " " + Resultado +" su Iniciativa");
        return true;
    }

}
