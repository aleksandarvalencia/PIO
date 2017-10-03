/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.pio.controller;
//import com.projects.halkom.model.DatePicker;
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.awt.List;
//import java.util.Map;
import javax.servlet.http.HttpServletRequest;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
/*import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.projects.pio.exceptions.DatabaseException;
import com.projects.pio.exceptions.DataAccessException;
import com.projects.pio.exceptions.CustomException;
import com.projects.pio.model.ClaimRacuni;
import com.projects.pio.model.ComboValues;
import com.projects.pio.model.ExcelBook;
import com.projects.pio.model.Greske;
import com.projects.pio.model.Iznosi;
import com.projects.pio.model.PIO;
import com.projects.pio.model.StavkeKnjizenja;
import com.projects.pio.model.UplatePIO;
//import com.projects.pio.model.ComboValues;

//import com.projects.halkom.model.ComboValues;
//import com.projects.halkom.model.FormDate;
import com.projects.pio.service.DataService;
import com.projects.pio.service.UserService;
import com.projects.pio.validation.UserFormValidator;
import com.projects.pio.validation.FormDateValidator;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.ArrayList;
import java.util.List;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
//import com.projects.trade.exceptions.GlobalExceptionHandler;
//import com.projects.trade.model.JsTreeNode;
//import com.projects.trade.model.TData;
//import com.projects.trade.model.Attributes;
//import java.util.ArrayList;
//import java.util.List;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SessionAttributes("header_book")
public class MenuProgramController {
    
//private final Logger logger = LoggerFactory.getLogger(MenuProgramController.class);
        String nodetext="";
        //StringBuilder sb=new StringBuilder();
        //StringBuilder append;
        String append="";
        Integer p_tipf=0;
        String f_filijala="";
        Integer idk=0;

	@Autowired
	UserFormValidator userFormValidator;
        
        @Autowired
	FormDateValidator formDateValidator;
	
       
        @InitBinder("user")
        protected void initUserBinder(WebDataBinder binder) {
            //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
            //dateFormat.setLenient(false); 
            //binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
            binder.setValidator(userFormValidator);
        }

        @InitBinder("formdate")
        protected void initPaymentBinder(WebDataBinder binder) {
            binder.setValidator(formDateValidator);
        }
        
	private DataService dataService;

	@Autowired
	public void setDataService(DataService dataService) {
		this.dataService = dataService;
	}
        
        private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

     
    
       @RequestMapping(value = "/pioExport/{datum}/{exp_type})", method = RequestMethod.POST/*,produces="application/json"*/)
	public String showAllFilesForDate(@PathVariable("datum") @DateTimeFormat(pattern="yyyy-MM-dd") Date filedate,
                @PathVariable("exp_type") Integer exp_type ,Model model) 
        {
               
             if (userService.getlogStatus()==0)
            {
                throw new DataAccessException("You're not logged!!!");
                //return "redirect:/login";
            }
                DateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
                String dat=dateFormat.format(filedate);
                dataService.ParsePIOExport(dat, exp_type);
		return "/pio/piotable";
         }
        @RequestMapping(value = "/pioImport", method = RequestMethod.GET)
	public String pioSelect(Model model,HttpServletRequest request) 
        {
            if (userService.getlogStatus()==0)
            {
                throw new DataAccessException("You're not logged!!!");
                //return "redirect:/login";
            }

                List<ComboValues> tipfajla=new ArrayList<>();
                tipfajla.add(0, new ComboValues(0,""));
                tipfajla.add(0, new ComboValues(1,"ZAPOSLENI"));
                tipfajla.add(1, new ComboValues(2,"POLJOPRIVREDNICI"));
                tipfajla.add(1, new ComboValues(3,"SAMOSTALNI"));
                tipfajla.add(1, new ComboValues(4,"VOJSKA"));
                tipfajla.add(1, new ComboValues(5,"UTUZENI"));
                model.addAttribute("pio_tip", tipfajla);

                List<PIO> pio =null;
                model.addAttribute("pioDetails", pio);
                return "/pio/piolist";
	}
        
       
        @RequestMapping(value = "/pioImport/{id}/{tipfajla}", method = RequestMethod.POST/*,produces="application/json"*/)
	public String searchPioP(@PathVariable("id") Integer id,@PathVariable("tipfajla") Integer tipfajla,Model model) 
        {

                List<PIO> pio =  dataService.ParsePIOImport(id,tipfajla);
		model.addAttribute("pioDetails", pio);
                
                List<ComboValues> tip=new ArrayList<>();
                tip.add(0, new ComboValues(0,""));
                tip.add(0, new ComboValues(1,"ZAPOSLENI"));
                tip.add(1, new ComboValues(2,"POLJOPRIVREDNICI"));
                tip.add(1, new ComboValues(3,"SAMOSTALNI"));
                tip.add(1, new ComboValues(4,"VOJSKA"));
                tip.add(1, new ComboValues(5,"UTUZENI"));
                model.addAttribute("pio_tip", tip);
                model.addAttribute("ukupaniznos",dataService.IznosiPIO(id));        
                model.addAttribute("informacija", "1");
                return "/pio/piolist";
         }
         
        @RequestMapping(value = "/searchPio/{filijala}/{tip}", method = RequestMethod.GET)
	public String searchPio(@PathVariable("filijala") String filijala,@PathVariable("tip") Integer tip ,Model model,HttpServletRequest request) 
        {

            if (userService.getlogStatus()==0)
            {
                throw new DataAccessException("You're not logged!!!");
                //return "redirect:/login";
            }
		request.setAttribute("pio", dataService.FindPIObyFilijala(filijala));
		return "/pio/piolist";

	}
	
	@RequestMapping(value = "/pioExport/{datum}/{pio_tip}", method = RequestMethod.POST)
	public String pioExport(@PathVariable("datum") @DateTimeFormat(pattern="yyyy-MM-dd") Date filedate,@PathVariable("pio_tip") Integer pio_tip ,Model model) 
        {
               
             if (userService.getlogStatus()==0)
            {
                throw new DataAccessException("You're not logged!!!");
                //return "redirect:/login";
            }
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
            String dat=dateFormat.format(filedate);
            dataService.ParsePIOExport(dat,pio_tip);
            
            model.addAttribute("pio", dataService.allPIO(pio_tip));
            model.addAttribute("informacija", "1");
            return "/pio/pioexport";
         }
        
        @RequestMapping(value = "/pioExport", method = RequestMethod.GET)
	public String pioExportNS(Model model) 
        {
               
             if (userService.getlogStatus()==0)
            {
                throw new DataAccessException("You're not logged!!!");
                //return "redirect:/login";
            }
                model.addAttribute("pio", dataService.allPIO(2));
		return "/pio/pioexport";
         }
        
        @RequestMapping(value = "/pioKnjizenje", method = RequestMethod.GET)
	public String pioKnjizenje(Model model) 
        {

		//logger.debug("UpdateUserForm() : {}", upid);
            
                if (userService.getlogStatus()==0)
               {
                   throw new DataAccessException("You're not logged!!!");
                   //return "redirect:/login";
               }

                List<PIO> pio =null;
                model.addAttribute("pio", pio);
                model.addAttribute("informacija","");
		return "/pio/piotable";

	}

        
        @RequestMapping(value="/filijale",method=RequestMethod.GET)   //OVO JE DIREKTNO POZIVANJE JSP STRANICA
        public @ResponseBody List<ComboValues> FindAllCities() //ResponseBody kaze da je ovo kontent koji vracamo na stranicu i mi smo odgovorni za njegovo pojavljivanje
        {

           return dataService.PIOFilijale();

        }
        @RequestMapping(value="/statusracuna",method=RequestMethod.GET)   
        public @ResponseBody List<ComboValues> StatusRacuna() 
        {
            List<ComboValues> statusracuna;
            HashMap referenceData = new HashMap();
            HashMap<String,String> country = new LinkedHashMap<>();
            country.put("0", "");
            country.put("1", "AKTIVAN");
            country.put("2", "UGASEN");
            statusracuna=(List<ComboValues>) referenceData;
            

            return statusracuna;

        }
        @RequestMapping(value = "/pioKnjizenje/{filijalak}", method = RequestMethod.GET)
	public String pioKnjizenjeF(@PathVariable("filijalak") String filijalak,Model model) 
        {

                if (userService.getlogStatus()==0)
               {
                   throw new DataAccessException("You're not logged!!!");
                   //return "redirect:/login";
               }

                model.addAttribute("pio", dataService.FindPIObyFilijala(filijalak));
                model.addAttribute("ukupaniznos",dataService.IznosiPoFilijalama(0,filijalak));   
                model.addAttribute("informacija", "0");
		return "/pio/piotable";

	}
        @RequestMapping(value = "/pioKnjizenje/{filijalak}/{tipk}", method = RequestMethod.POST)
	public String pioKnjizenjeP(@PathVariable("filijalak") String filijalak,@PathVariable("tipk") Integer tipk ,
                @ModelAttribute("pio") PIO pp,HttpServletRequest request,Model model) 
        {

                if (userService.getlogStatus()==0)
               {
                   throw new DataAccessException("You're not logged!!!");
                   //return "redirect:/login";
               }

                //PIO pp1=new PIO();
                dataService.PIO_EXPORT(filijalak);         
                List<UplatePIO> pio_uplate = (List<UplatePIO>) request.getSession().getAttribute("header_information");
                model.addAttribute("header_book",pio_uplate.get(0));
               // model.addAttribute("pio", pp1);
                model.addAttribute("ukupaniznos",dataService.IznosiPoFilijalama(tipk,filijalak));   
                model.addAttribute("informacija", "EXPORT U FAJL USPESNO URADJEN");
		return "/pio/piotable";

	}
         @RequestMapping(value = "/filijalaDetails/{filijalap}/{tipfilijala}", method = RequestMethod.POST)
	public String pioFilijaleDetalji(@PathVariable("filijalap") String filijalap,@PathVariable("tipfilijala") Integer tipfilijala,           
                Model model,HttpServletResponse response, HttpServletRequest request) 
        {

               if (userService.getlogStatus()==0)
               {
                   throw new DataAccessException("You're not logged!!!");
                   //return "redirect:/login";
               }
               /*@CookieValue(value="datum_uplate" , defaultValue = "01-01-2016") String datum_uplate,@CookieValue(value="iznos_uplate",defaultValue = "0") String iznos_uplate,
                @CookieValue(value="racun_uplate",defaultValue = "111-1111111111111-11") String racun_uplate,@CookieValue(value="kontraracun_uplate",defaultValue = "111-1111111111111-11") String kontraracun_uplate,
                @CookieValue(value="tmsid_uplate",defaultValue = "0") String tmsid_uplate,@CookieValue(value="idk_uplate",defaultValue = "0") String idk_uplate,
                @CookieValue(value="pbo_uplate",defaultValue = "99") String pbo_uplate,@CookieValue(value="ime_uplate",defaultValue = "WWW") String ime_uplate,*/
               
                //f_filijala=filijalap;
               
               
               /*UplatePIO up=new UplatePIO();
               
               Cookie[] cookies = request.getCookies();
                for (Cookie cookie : cookies) {
                   switch (cookie.getName()) 
                  {
                       case "uplata_datum":
                           up.setDatum(cookie.getValue());
                           break;
                       case "uplata_tmsid":
                            up.setID(Integer.parseInt(cookie.getValue()));
                           break;
                       case "uplata_idk":
                           up.setIdk(Integer.parseInt(cookie.getValue()));
                           break;
                       case "uplata_ime":
                            up.setIme(cookie.getValue());
                           break;
                        case "uplata_iznos":
                           up.setIznos(BigDecimal.valueOf(Double.valueOf(cookie.getValue())));
                           break;
                       case "uplata_kontraracun":
                            up.setKontraracun(cookie.getValue());
                           break;
                       case "uplata_pbo":
                          up.setPbo(cookie.getValue());
                           break;
                       case "uplata_racun":
                            up.setRacun(cookie.getValue());
                           
                           break;                               
                   }
                }
               
                
                List<UplatePIO> upl=new ArrayList<>();
                upl.add(0,up);*/
/*
                up.setID(Integer.parseInt(tmsid_uplate));
                up.setIdk(Integer.parseInt(idk_uplate));
                up.setIme(ime_uplate);
                up.setIznos(BigDecimal.valueOf(Double.valueOf(iznos_uplate)));
                up.setKontraracun(kontraracun_uplate);
                up.setPbo(pbo_uplate);
                up.setRacun(racun_uplate);
 */
                Integer retval=0;
                String inf="";
                request.getSession().setAttribute("filijalavalue",filijalap);
                request.getSession().setAttribute("piobuttonvalue",tipfilijala);
                List<ComboValues> result =dataService.PIO_FAJL_ZA_KNJIZENJE(filijalap, tipfilijala);
                retval=result.get(0).getValue();
                inf=result.get(0).getLabel();
                 if (retval==0)
                {
                    model.addAttribute("pio", dataService.FindPIObyFilijala(filijalap));
                    model.addAttribute("ukupaniznos",dataService.IznosiPoFilijalama(0,filijalap));   
                }
                List<UplatePIO> pio_uplate = (List<UplatePIO>) request.getSession().getAttribute("header_information");
                model.addAttribute("header_book",pio_uplate.get(0));
                model.addAttribute("informacija", inf);
                //SetCookieValue(upl, response);
		return "/pio/piotable";

	}
         @RequestMapping(value = "/pioKnjizenje/{id_knjiz}/details", method = RequestMethod.GET)
	public String pioDetalji(@PathVariable("id_knjiz") Integer id_knjiz ,Model model) 
        {

                if (userService.getlogStatus()==0)
               {
                   throw new DataAccessException("You're not logged!!!");
                   //return "redirect:/login";
               }
                List<ComboValues> statusracuna=new ArrayList<>();
                statusracuna.add(0, new ComboValues(0,""));
                statusracuna.add(1, new ComboValues(1,"AKTIVAN"));
                statusracuna.add(1, new ComboValues(2,"UGASEN"));
                statusracuna.add(1, new ComboValues(3,"NE POSTOJI"));
                Greske piodet =dataService.PIOErrorDetails(id_knjiz);
                model.addAttribute("pio",piodet);
                model.addAttribute("statusiracuna", statusracuna);
		return "/pio/piodetails";

	}
        @RequestMapping(value = "/accountCheck", method = RequestMethod.POST)
	public String racunProvera(Model model) 
        {

                if (userService.getlogStatus()==0)
               {
                   throw new DataAccessException("You're not logged!!!");
                   //return "redirect:/login";
               }
                
                String p_akcija="load";
                
                List<Greske> gr=dataService.AccountCheck(p_akcija);
                List<PIO> pp=dataService.getRset();
                
                
                
                Integer gr_len=0;
                Integer pp_len=0;
                if (gr==null)
                {
                    gr_len=0;
                }
                else
                {
                    gr_len=gr.size();
                }
                if (pp==null)
                {
                    pp_len=0;
                }
                else
                {
                    pp_len=pp.size();
                }

                
                if ( gr_len==0 && pp_len >0)
                {
                    model.addAttribute("pio", pp);
                    model.addAttribute("ukupaniznos",pp.get(0).getNova_rata());  
                    model.addAttribute("informacija", "3");
                }
                if ( gr_len>0 && pp_len==0)
                {
                    model.addAttribute("greske", gr);
                    model.addAttribute("informacija", "provera");
                }
		return "/pio/piotable";

	}
         @RequestMapping(value = "/accountCheck", method = RequestMethod.GET)
	public String racunProveraGet( Model model,HttpServletResponse response,HttpServletRequest request) 
        {

                if (userService.getlogStatus()==0)
               {
                   throw new DataAccessException("You're not logged!!!");
                   //return "redirect:/login";
               }
                
               /* UplatePIO up=new UplatePIO();
               Cookie[] cookies = request.getCookies();
                for (Cookie cookie : cookies) {
                   switch (cookie.getName()) 
                  {
                       case "uplata_datum":
                           up.setDatum(cookie.getValue());
                           break;
                       case "uplata_tmsid":
                            up.setID(Integer.parseInt(cookie.getValue()));
                           break;
                       case "uplata_idk":
                           up.setIdk(Integer.parseInt(cookie.getValue()));
                           break;
                       case "uplata_ime":
                            up.setIme(cookie.getValue());
                           break;
                        case "uplata_iznos":
                           up.setIznos(BigDecimal.valueOf(Double.valueOf(cookie.getValue())));
                           break;
                       case "uplata_kontraracun":
                            up.setKontraracun(cookie.getValue());
                           break;
                       case "uplata_pbo":
                          up.setPbo(cookie.getValue());
                           break;
                       case "uplata_racun":
                            up.setRacun(cookie.getValue());
                           break;                          
                   }
                }
                
                List<UplatePIO> upl=new ArrayList<>();
                upl.add(0,up);*/
                
 /*             up.setDatum(datum_uplate);
                up.setID(Integer.parseInt(tmsid_uplate));
                up.setIdk(Integer.parseInt(idk_uplate));
                up.setIme(ime_uplate);
                up.setIznos(BigDecimal.valueOf(Double.valueOf(iznos_uplate)));
                up.setKontraracun(kontraracun_uplate);
                up.setPbo(pbo_uplate);
                up.setRacun(racun_uplate);
 */
               
                
                //SetCookieValue(upl, response);
                model.addAttribute("greske", dataService.AccountCheckTable());
                List<UplatePIO> pio_uplate = (List<UplatePIO>) request.getSession().getAttribute("header_information");
                model.addAttribute("header_book",pio_uplate.get(0));
                model.addAttribute("informacija", "provera");
		return "/pio/piotable";

	}
       @RequestMapping(value = "/pioKnjizenje/{id_knjiz1}/details", method = RequestMethod.POST)
	public String pioDetaljiUpdate(@PathVariable("id_knjiz1") Integer id_knjiz1 ,@ModelAttribute("pio") Greske pio) 
        {

                if (userService.getlogStatus()==0)
               {
                   throw new DataAccessException("You're not logged!!!");
                   //return "redirect:/login";
               }

                String akcija="update";
                dataService.SP_PIO(akcija,pio.getFilijala(),pio.getId_penzije(),pio.getSifra_obustave(),pio.getRacun(),pio.getIme(),pio.getJmbg(),pio.getIznos().toString(),id_knjiz1,pio.getStatus_racuna());
		return "redirect:/accountCheck";

	}
        @RequestMapping(value = "/pioUplate", method = RequestMethod.GET)
	public String pioUplate(Model model) 
        {

                if (userService.getlogStatus()==0)
               {
                   throw new DataAccessException("You're not logged!!!");
                   //return "redirect:/login";
               }

                String akcija="select";
                
               
                model.addAttribute("uplate", dataService.PodacioUplatama(akcija,null, null));
		return "/pio/piouplate";

	}
         @RequestMapping(value = "/getPayment/{payment_date}", method = RequestMethod.POST)
	public String gerPayment(@PathVariable("payment_date") @DateTimeFormat(pattern="yyyy-MM-dd") Date payment_date,Model model) 
        {

                if (userService.getlogStatus()==0)
               {
                   throw new DataAccessException("You're not logged!!!");
                   //return "redirect:/login";
               }

            String akcija="select";
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
            String dat=dateFormat.format(payment_date);
               
                model.addAttribute("uplate", dataService.PodacioUplatama(akcija,dat, null));
		return "/pio/piouplate";

	}

        
        @RequestMapping(value = "/headerInformation/{idate}/{tms_iznos}", method = RequestMethod.POST)
	public String HeaderInformation(@PathVariable("idate") @DateTimeFormat(pattern="yyyy-MM-dd") Date idate,
                @PathVariable("tms_iznos") String tms_iznos ,Model model,HttpServletRequest request,SessionStatus status) 
        {
               
            if (userService.getlogStatus()==0)
            {
                throw new DataAccessException("You're not logged!!!");
                //return "redirect:/login";
            }
            
            /*@CookieValue("datum_uplate") String datum_uplate,@CookieValue("iznos_uplate") String iznos_uplate,
                @CookieValue("racun_uplate") String racun_uplate,@CookieValue("kontraracun_uplate") String kontraracun_uplate,@CookieValue("tmsid_uplate") String tmsid_uplate,
                @CookieValue("idk_uplate") String idk_uplate,@CookieValue("pbo_uplate") String pbo_uplate,@CookieValue("ime_uplate") String ime_uplate*/
            
  
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
            String dat=dateFormat.format(idate);
            String akcija="header";
            tms_iznos=tms_iznos.replace(",", ".");
            List<UplatePIO> upl=dataService.PodacioUplatama(akcija,dat,tms_iznos);
            idk=upl.get(0).getIdk();

            request.getSession().setAttribute("header_information",upl);


            //SetCookieValue(upl,response);
            
            ///KAD IZBACI NumberFormatException to znaci da  ne moze da dodje do stavke iz liste!!!
            model.addAttribute("header_book",upl.get(0));
            model.addAttribute("informacija", "0");
            return "/pio/piotable";
         }
      
        @RequestMapping(value = "/sendBooking/{id_knjizenja}/{tms_id}/{knjizenje_iznos}/{tip_fil}", method = RequestMethod.POST)
	public String Booking(@PathVariable("id_knjizenja") Integer id_knjizenja,@PathVariable("tms_id") Integer tms_id ,
                @PathVariable("knjizenje_iznos") String knjizenje_iznos ,@PathVariable("tip_fil") Integer tip_fil ,Model model) 
        {
               
             if (userService.getlogStatus()==0)
            {
                throw new DataAccessException("You're not logged!!!");
            }

            Integer retval=0;
            String inf="";
            if (tip_fil==null || tip_fil==0 ) 
            {
                retval=1;
                inf="MORATE IZABRATI PIO FOND ZA KOJI KNJIZITE STAVKE";
            }
            else
            {
                String akcija="knjizenje";
                /*if (id_knjizenja < idk)
                {
                    id_knjizenja=idk;
                }*/
                knjizenje_iznos=knjizenje_iznos.replace(",", ".");
                List<ComboValues> upl=dataService.Posalji_Na_Knjizenje(akcija, id_knjizenja, tms_id, knjizenje_iznos,null,tip_fil);
                retval=upl.get(0).getValue();
                inf=upl.get(0).getLabel();
            }
            model.addAttribute("informacija",inf);
            
            if (retval==0)
            {
                return "redirect:/bookingItems/" + tip_fil + "/" + inf + "/" + id_knjizenja;
            }
            else
                return "/pio/piotable";
         }
        @RequestMapping(value = "/bookingItems/{knjizenje_datum}/{fl}/{book_idk}", method = RequestMethod.POST)
	public String BookingItems(@PathVariable("knjizenje_datum") @DateTimeFormat(pattern="yyyy-MM-dd") Date knjizenje_datum ,
                @PathVariable("fl") Integer fl,@PathVariable("book_idk") Integer book_idk,Model model) 
        {
               
             if (userService.getlogStatus()==0)
            {
                throw new DataAccessException("You're not logged!!!");
                //return "redirect:/login";
            }
            List<ComboValues> pio_filijala=new ArrayList<>();
            pio_filijala.add(0, new ComboValues(0,""));
            pio_filijala.add(1, new ComboValues(1,"BEOGRAD"));
            pio_filijala.add(1, new ComboValues(2,"NOVI SAD"));
            
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
            String dat=dateFormat.format(knjizenje_datum);
            
            String akcija="select";
            List<ComboValues> upl=dataService.Posalji_Na_Knjizenje(akcija, null, null, null,dat,fl);
            List<StavkeKnjizenja> sk=dataService.getStavkeKnjizenja();

            model.addAttribute("informacija","");
            List<Iznosi> izn=dataService.StavkeKnjizenja(akcija, fl,null,dat);
            model.addAttribute("pio_idk", book_idk);
            model.addAttribute("pio_filijala", pio_filijala);
            if (izn!=null)
            {
             model.addAttribute("iznosi_knjizenja",izn.get(0));
            }
            else
            {
             model.addAttribute("iznosi_knjizenja",null);
            }
            //model.addAttribute("iznosi_knjizenja",izn.get(0));
            model.addAttribute("stavke", sk);
            return "/pio/piostavke";
           
         }
        @RequestMapping(value = "/bookingItems", method = RequestMethod.GET)
	public String BookingItemsFull(Model model) 
        {
               
             if (userService.getlogStatus()==0)
            {
                throw new DataAccessException("You're not logged!!!");
                //return "redirect:/login";
            }
            List<ComboValues> pio_filijala=new ArrayList<>();
            pio_filijala.add(0, new ComboValues(0,""));
            pio_filijala.add(1, new ComboValues(1,"BEOGRAD"));
            pio_filijala.add(1, new ComboValues(2,"NOVI SAD"));
            
            String akcija="select";
            List<ComboValues> upl=dataService.Posalji_Na_Knjizenje(akcija, null, null, null,null,null);
            List<StavkeKnjizenja> sk=dataService.getStavkeKnjizenja();
            List<Iznosi> izn=dataService.StavkeKnjizenja(null, null,null,null);
            
            //Integer retval=upl.get(0).getValue();
            model.addAttribute("informacija","");
            model.addAttribute("pio_idk", 0);
            model.addAttribute("pio_filijala", pio_filijala);
            if (izn!=null)
            {
             model.addAttribute("iznosi_knjizenja",izn.get(0));
            }
            else
            {
             model.addAttribute("iznosi_knjizenja",null);
            }
            model.addAttribute("stavke", sk);
            return "/pio/piostavke";
           
         }
        
        @RequestMapping(value = "/bookingItems/{fil_fil}/{info}/{b_idk}", method = RequestMethod.GET)
	public String BookingItemsForm(@PathVariable("fil_fil") Integer fil_fil,@PathVariable("info") String info,@PathVariable("b_idk") Integer b_idk,Model model) 
        {
               
            List<ComboValues> pio_filijala=new ArrayList<>();
            pio_filijala.add(0, new ComboValues(0,""));
            pio_filijala.add(1, new ComboValues(1,"BEOGRAD"));
            pio_filijala.add(1, new ComboValues(2,"NOVI SAD"));
           
            String akcija="select";
            //List<ComboValues> upl=dataService.Posalji_Na_Knjizenje(akcija, null, null, null,null,null);
            List<StavkeKnjizenja> sk=dataService.getStavkeKnjizenja();
            List<Iznosi> izn=dataService.StavkeKnjizenja(akcija,fil_fil,b_idk,null );
           
            //Integer retval=upl.get(0).getValue();
            model.addAttribute("informacija",info);
            model.addAttribute("pio_idk", b_idk);
            model.addAttribute("pio_filijala", pio_filijala);
            if (izn!=null)
            {
             model.addAttribute("iznosi_knjizenja",izn.get(0));
            }
            else
            {
             model.addAttribute("iznosi_knjizenja",null);
            }
            //model.addAttribute("iznosi_knjizenja",izn.get(0));
            model.addAttribute("stavke", sk);
            return "/pio/piostavke";
           
         }
        @RequestMapping(value = "/changeAll", method = RequestMethod.POST)
	public String changeAll() 
        {

                if (userService.getlogStatus()==0)
               {
                   throw new DataAccessException("You're not logged!!!");
                   //return "redirect:/login";
               }

                String akcija="sve";
                dataService.SP_PIO(akcija,null,null,null,null,null,null,null,null,null);
		return "redirect:/accountCheck";

	}
        
        @RequestMapping(value = "/claimAccounts", method = RequestMethod.GET)
        public String claimAccounts(Model model) {
            // create some sample data

           if (userService.getlogStatus()==0)
           {
                throw new DataAccessException("You're not logged!!!");
                   //return "redirect:/login";
            }

            List<ClaimRacuni> claimClients=dataService.ClaimAccounts();
            model.addAttribute("claimracuni", claimClients);        
            return "/pio/claimaccounts";
        }
        
        @RequestMapping(value = "/sendToProcess/{kn_datum}/{fl1}/{sp_idk}", method = RequestMethod.POST)
	public String sendToProcess(@PathVariable("kn_datum") @DateTimeFormat(pattern="yyyy-MM-dd") Date kn_datum ,
                @PathVariable("fl1") Integer fl1, @PathVariable("sp_idk") Integer sp_idk,Model model) 
        {

               if (userService.getlogStatus()==0)
               {
                   throw new DataAccessException("You're not logged!!!");
                   //return "redirect:/login";
               }

                List<ComboValues> pio_filijala=new ArrayList<>();
                pio_filijala.add(0, new ComboValues(0,""));
                pio_filijala.add(1, new ComboValues(1,"BEOGRAD"));
                pio_filijala.add(1, new ComboValues(2,"NOVI SAD"));
                
                String akcija="process";
                DateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
                String dat=dateFormat.format(kn_datum);
                List<ComboValues> upl=dataService.Posalji_Na_Knjizenje(akcija, null, null, null,dat,fl1);
                List<StavkeKnjizenja> sk=dataService.getStavkeKnjizenja();
                List<Iznosi> izn=dataService.StavkeKnjizenja("select", fl1,sp_idk,dat);
                
                model.addAttribute("informacija",upl.get(0).getLabel());
                model.addAttribute("pio_filijala", pio_filijala);
                if (izn!=null)
                {
                 model.addAttribute("iznosi_knjizenja",izn.get(0));
                }
                else
                {
                 model.addAttribute("iznosi_knjizenja",null);
                }
                //model.addAttribute("iznosi_knjizenja",izn.get(0));
                model.addAttribute("stavke", sk);
		return "/pio/piostavke";

	}
        @RequestMapping(value = "/downloadExcel", method = RequestMethod.POST)
        public ModelAndView downloadExcel() {
            // create some sample data
            List<ExcelBook> claimClients = new ArrayList<>();
            List<ClaimRacuni> claimAccounts=dataService.ClaimAccounts();
            //filijala,id_penzije,opis,jmbg,iznos
            for (ClaimRacuni claimAccount : claimAccounts)
            {
                claimClients.add(new ExcelBook(claimAccount.getFilijala(),claimAccount.getId_penzije(), claimAccount.getIme(),claimAccount.getJmbg(),claimAccount.getIznos(),"","", "","",""));

            }

            return new ModelAndView("excelView", "excelBooks", claimClients);
        }
        
        /* MONITORNIG SA IDK*/
        @RequestMapping(value = "/monitoring/{monitoring_datum}/{monitoring_fl}/{monitoring_idk}", method = RequestMethod.POST)
	public String MonitoringItems(@PathVariable("monitoring_datum") @DateTimeFormat(pattern="yyyy-MM-dd") Date monitoring_datum ,
                @PathVariable("monitoring_fl") Integer monitoring_fl,@PathVariable("monitoring_idk") String monitoring_idk,Model model) 
        {
               
            if (userService.getlogStatus()==0)
            {
                throw new DataAccessException("You're not logged!!!");
                //return "redirect:/login";
            }
            List<ComboValues> pio_filijala=new ArrayList<>();
            pio_filijala.add(0, new ComboValues(0,""));
            pio_filijala.add(1, new ComboValues(1,"BEOGRAD"));
            pio_filijala.add(1, new ComboValues(2,"NOVI SAD"));
            
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
            String dat=dateFormat.format(monitoring_datum);
            
            DateFormat dateFormat1 = new SimpleDateFormat("YYYY-MM-dd");
            String dat1=dateFormat1.format(monitoring_datum);
            
            String akcija="monitoring_items";
            

            List<Iznosi> izn=dataService.Monitoring(akcija, monitoring_fl,dat,monitoring_idk);
            List<UplatePIO> mon=dataService.getMonitoring();
            List<StavkeKnjizenja> sk=dataService.getStavkeKnjizenja();
            model.addAttribute("informacija","monitorning_full");
            model.addAttribute("pio_filijala", pio_filijala);
            if (izn!=null)
            {
             model.addAttribute("iznosi_knjizenja",izn.get(0));
            }
            else
            {
             model.addAttribute("iznosi_knjizenja",null);
            }
            //model.addAttribute("iznosi_knjizenja",izn.get(0));
            model.addAttribute("stavke", sk);
            model.addAttribute("monitoring_header", mon);
            model.addAttribute("monitoring_date", dat1);
            return "/pio/piomonitoring";
           
         }  
         /* MONITORNIG BEZ IDK*/
        @RequestMapping(value = "/monitoring/{monitoring_datum}/{monitoring_fl}", method = RequestMethod.POST)
	public String MonitoringHeaderOnly(@PathVariable("monitoring_datum") @DateTimeFormat(pattern="yyyy-MM-dd") Date monitoring_datum ,
                @PathVariable("monitoring_fl") Integer monitoring_fl,Model model,HttpServletRequest request) 
        {
               
            if (userService.getlogStatus()==0)
            {
                throw new DataAccessException("You're not logged!!!");
                //return "redirect:/login";
            }
            List<ComboValues> pio_filijala=new ArrayList<>();
            pio_filijala.add(0, new ComboValues(0,""));
            pio_filijala.add(1, new ComboValues(1,"BEOGRAD"));
            pio_filijala.add(1, new ComboValues(2,"NOVI SAD"));

            DateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
            String dat=dateFormat.format(monitoring_datum);

            DateFormat dateFormat1 = new SimpleDateFormat("YYYY-MM-dd");
            String dat1=dateFormat1.format(monitoring_datum);
            request.getSession().setAttribute("filijalamon",monitoring_fl);
            //request.getSession().setAttribute("monitoringdatum",dat);
            
            String akcija="monitoring_header";

            List<Iznosi> izn=dataService.Monitoring(akcija, monitoring_fl,dat,null);
            List<UplatePIO> sk=dataService.getMonitoring();
            model.addAttribute("informacija","header_only");
            model.addAttribute("pio_filijala", pio_filijala);
            if (izn!=null)
            {
             model.addAttribute("iznosi_knjizenja",izn.get(0));
            }
            else
            {
             model.addAttribute("iznosi_knjizenja",null);
            }
            //model.addAttribute("iznosi_knjizenja",izn.get(0));
            model.addAttribute("monitoring_header", sk);
            model.addAttribute("monitoring_date", dat1);
            return "/pio/piomonitoring";
           
         }  
         @RequestMapping(value = "/monitoring", method = RequestMethod.GET)
	public String MonitoringFull(Model model) 
        {
               
             if (userService.getlogStatus()==0)
            {
                throw new DataAccessException("You're not logged!!!");
                //return "redirect:/login";
            }
            List<ComboValues> pio_filijala=new ArrayList<>();
            pio_filijala.add(0, new ComboValues(0,""));
            pio_filijala.add(1, new ComboValues(1,"BEOGRAD"));
            pio_filijala.add(1, new ComboValues(2,"NOVI SAD"));
            
            String akcija="monitoring_header";
            List<Iznosi> izn=dataService.Monitoring(akcija, null,null,null);
            List<UplatePIO> sk=dataService.getMonitoring();
            //Integer retval=upl.get(0).getValue();
            model.addAttribute("informacija","header_only");
            model.addAttribute("pio_filijala", pio_filijala);
            if (izn!=null)
            {
             model.addAttribute("iznosi_knjizenja",izn.get(0));
            }
            else
            {
             model.addAttribute("iznosi_knjizenja",null);
            }
            model.addAttribute("monitoring_header", sk);
            return "/pio/piomonitoring";
           
         }
        private void SetCookieValue(List<UplatePIO> upl,HttpServletResponse response)
        {
            Cookie uplata_idk =new Cookie("uplata_idk", upl.get(0).getIdk().toString());
            uplata_idk.setMaxAge(1500000);
            uplata_idk.setDomain("");
            uplata_idk.setPath("");
            response.addCookie(uplata_idk);
            Cookie uplata_datum=new Cookie("uplata_datum", upl.get(0).getDatum());
            uplata_datum.setMaxAge(1500000);
            uplata_datum.setDomain("");
            uplata_datum.setPath("");
            response.addCookie(uplata_datum);
            Cookie uplata_iznos= new Cookie("uplata_iznos", upl.get(0).getIznos().toString());
            uplata_iznos.setMaxAge(1500000);
            uplata_iznos.setDomain("");
            uplata_iznos.setPath("");
            response.addCookie(uplata_iznos);
            Cookie uplata_racun=new Cookie("uplata_racun", upl.get(0).getRacun());
            uplata_racun.setMaxAge(1500000);
            uplata_racun.setDomain("");
            uplata_racun.setPath("");
            response.addCookie(uplata_racun);
            Cookie uplata_kontraracun=new Cookie("uplata_kontraracun", upl.get(0).getKontraracun());
            uplata_kontraracun.setMaxAge(1500000);
            uplata_kontraracun.setDomain("");
            uplata_kontraracun.setPath("");
            response.addCookie(uplata_kontraracun);
            Cookie uplata_tmsid=new Cookie("uplata_tmsid", upl.get(0).getID().toString());
            uplata_tmsid.setMaxAge(1500000);
            uplata_tmsid.setDomain("");
            uplata_tmsid.setPath("");
            response.addCookie(uplata_tmsid);
            Cookie uplata_pbo=new Cookie("uplata_pbo", upl.get(0).getPbo());
            uplata_pbo.setMaxAge(1500000);
            uplata_pbo.setDomain("");
            uplata_pbo.setPath("");
            response.addCookie(uplata_pbo);
            Cookie uplata_ime=new Cookie("uplata_ime", upl.get(0).getIme());
            uplata_ime.setMaxAge(1500000);
            uplata_ime.setDomain("");
            uplata_ime.setPath("");
            response.addCookie(uplata_ime);
            
        }

    //----------------------------------------------------------------------------------------//
        @ExceptionHandler(EmptyResultDataAccessException.class)
	public ModelAndView handleEmptyData(HttpServletRequest req, Exception ex) {

		//logger.debug("handleEmptyData()");
		//logger.error("Request: {}, error ", req.getRequestURL(), ex);

		ModelAndView model = new ModelAndView();
		model.setViewName("/users/errorhandler");
		model.addObject("message", ex.getMessage());

		return model;

	}
        @ExceptionHandler(DatabaseException.class)
	public ModelAndView handleDatabaseException(DatabaseException ex) {

		ModelAndView model = new ModelAndView("/users/errorhandler");
		model.addObject("message", "No Data Found");
		return model;

	}
        
        @ExceptionHandler(CustomException.class)
	public ModelAndView handleCustomException(CustomException ex) {

		ModelAndView model = new ModelAndView("/users/errorhandler");
		model.addObject("message", ex.getMessage());
		return model;

	}
        
        @ExceptionHandler(DataAccessException.class)
	public ModelAndView handleAccessException(DataAccessException ex) {

		ModelAndView model = new ModelAndView("/users/loginerror");
		model.addObject("message", ex.getMessage());
		return model;

	}
    
}
