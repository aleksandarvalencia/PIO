package com.projects.pio.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.projects.pio.exceptions.DatabaseException;
import com.projects.pio.exceptions.DataAccessException;
import com.projects.pio.exceptions.CustomException;
import com.projects.pio.model.ClaimRacuni;
import com.projects.pio.model.ComboValues;
import com.projects.pio.model.ExcelBook;
import com.projects.pio.model.Iznosi;
import com.projects.pio.model.PENZIJE;
import com.projects.pio.model.PIO;
import com.projects.pio.model.StavkeKnjizenja;
import com.projects.pio.model.UGASENI;
import com.projects.pio.model.UplatePIO;
import com.projects.pio.model.VRSTEFAJLOVA;
import com.projects.pio.service.PensionService;
import com.projects.pio.service.UserService;
import com.projects.pio.validation.UserFormValidator;
import com.projects.pio.validation.FormDateValidator;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.servlet.http.HttpServletResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;


@Controller
@SessionAttributes("pension_book")
public class PensionProgramController {
    
    
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
        
	private PensionService pensionService;

	@Autowired
	public void setDataService(PensionService pensionService) {
		this.pensionService = pensionService;
	}
        
        private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}


        @RequestMapping(value = "/penzijeImport", method = RequestMethod.GET)
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
                return "/pension/pensionlist";
	}
        @RequestMapping(value = "/penzijeExport/{exp_type}", method = RequestMethod.POST/*,produces="application/json"*/)
	public String showAllFilesForDate(@PathVariable("exp_type") Integer exp_type ,HttpServletRequest request,Model model) 
        {
               
             if (userService.getlogStatus()==0)
            {
                throw new DataAccessException("You're not logged!!!");
                //return "redirect:/login";
            }
                List<ComboValues> pio_filijala=new ArrayList<>();
                pio_filijala.add(0, new ComboValues(0,""));
                pio_filijala.add(1, new ComboValues(1,"ZAPOSLENI BEOGRAD"));
                pio_filijala.add(2, new ComboValues(2,"ZAPOSLENI BEOGRAD NAKNADE"));
                pio_filijala.add(3, new ComboValues(3,"ZAPOSLENI BEOGRAD POGREBNO"));
                pio_filijala.add(4, new ComboValues(4,"ZAPOSLENI NOVI SAD"));
                pio_filijala.add(5, new ComboValues(5,"VOJSKA NOVI SAD"));
                pio_filijala.add(6, new ComboValues(6,"VOJSKA BEOGRAD"));
                pio_filijala.add(7, new ComboValues(7,"VOJSKA BEOGRAD NAKNADE"));
                pio_filijala.add(8, new ComboValues(8,"VOJSKA BEOGRAD POGREBNO"));
                pio_filijala.add(9, new ComboValues(9,"POLJOPRIVREDA BEOGRAD"));
                pio_filijala.add(10, new ComboValues(10,"POLJOPRIVREDA BEOGRAD NAKNADE"));
                pio_filijala.add(11, new ComboValues(11,"POLJOPRIVREDA BEOGRAD POGREBNO"));
                pio_filijala.add(12, new ComboValues(12,"POLJOPRIVREDA NOVI SAD"));
                pio_filijala.add(13, new ComboValues(13,"SAMOSTALCI NOVI SAD"));
                pio_filijala.add(14, new ComboValues(14,"SAMOSTALCI BEOGRAD"));
                pio_filijala.add(15, new ComboValues(15,"SAMOSTALCI BEOGRAD NAKNADE"));
                pio_filijala.add(16, new ComboValues(16,"SAMOSTALCI BEOGRAD POGREBNO")); 
                pio_filijala.add(17, new ComboValues(17,"PSOVO"));
                
                
                request.getSession().setAttribute("filijalavalue",exp_type);
                pensionService.PIO_EXPORT(exp_type);
                List<PENZIJE> pio=pensionService.getRset();
                List<UGASENI> upp=pensionService.getRsetUgaseni();
		model.addAttribute("penzije", pio);
                model.addAttribute("ugaseni", upp);
                model.addAttribute("pio_filijale", pio_filijala);
                model.addAttribute("idkg",request.getSession().getAttribute("idkg"));
                model.addAttribute("informacija","USPESNO EXPORTOVAN FAJL!");
		return "/pension/pensiontable";
         }
        
       @RequestMapping(value="/ImportRacuna",method=RequestMethod.POST)
	public String ImportRacunaPenzija(Model model) 
        {
                Integer retval=0;
                String inf="";
                List<ComboValues> pio =  pensionService.ImportRacuna();   
                retval=pio.get(0).getValue();
                inf=pio.get(0).getLabel();
                model.addAttribute("informacija", inf);
                return "/pension/pensionuplate";
         }

        @RequestMapping(value = "/penzijeKnjizenje", method = RequestMethod.GET)
	public String pioKnjizenje(Model model,HttpServletRequest request) 
        {

               if (userService.getlogStatus()==0)
               {
                   throw new DataAccessException("You're not logged!!!");
               }

                List<ComboValues> pio_filijala=new ArrayList<>();
                pio_filijala.add(0, new ComboValues(0,""));
                pio_filijala.add(1, new ComboValues(1,"ZAPOSLENI BEOGRAD"));
                pio_filijala.add(2, new ComboValues(2,"ZAPOSLENI BEOGRAD NAKNADE"));
                pio_filijala.add(3, new ComboValues(3,"ZAPOSLENI BEOGRAD POGREBNO"));
                pio_filijala.add(4, new ComboValues(4,"ZAPOSLENI NOVI SAD"));
                pio_filijala.add(5, new ComboValues(5,"VOJSKA NOVI SAD"));
                pio_filijala.add(6, new ComboValues(6,"VOJSKA BEOGRAD"));
                pio_filijala.add(7, new ComboValues(7,"VOJSKA BEOGRAD NAKNADE"));
                pio_filijala.add(8, new ComboValues(8,"VOJSKA BEOGRAD POGREBNO"));
                pio_filijala.add(9, new ComboValues(9,"POLJOPRIVREDA BEOGRAD"));
                pio_filijala.add(10, new ComboValues(10,"POLJOPRIVREDA BEOGRAD NAKNADE"));
                pio_filijala.add(11, new ComboValues(11,"POLJOPRIVREDA BEOGRAD POGREBNO"));
                pio_filijala.add(12, new ComboValues(12,"POLJOPRIVREDA NOVI SAD"));
                pio_filijala.add(13, new ComboValues(13,"SAMOSTALCI NOVI SAD"));
                pio_filijala.add(14, new ComboValues(14,"SAMOSTALCI BEOGRAD"));
                pio_filijala.add(15, new ComboValues(15,"SAMOSTALCI BEOGRAD NAKNADE"));
                pio_filijala.add(16, new ComboValues(16,"SAMOSTALCI BEOGRAD POGREBNO")); 
                pio_filijala.add(17, new ComboValues(17,"PSOVO"));
                
                List<PENZIJE> penzije =null;
                model.addAttribute("penzije", penzije);
                model.addAttribute("pio_filijale", pio_filijala);
                model.addAttribute("idkg",request.getSession().getAttribute("idkg"));
                model.addAttribute("informacija","");
		return "/pension/pensiontable";

	}

        @RequestMapping(value="/statusracunap",method=RequestMethod.GET)   
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
       @RequestMapping(value = "/penzijeFajlovi", method = RequestMethod.GET)
	public String penzijeFajlovi(HttpServletRequest request,Model model) 
        {

               if (userService.getlogStatus()==0)
               {
                   throw new DataAccessException("You're not logged!!!");
                   //return "redirect:/login";
               }
               
               List<VRSTEFAJLOVA> vf=pensionService.VrsteFajlova();
               model.addAttribute("penzije_fajlovi", vf);
	       return "/pension/tipfajlova";

	}
        @RequestMapping(value = "/penzijeKnjizenje/{tip_fond}/{uplata}", method = RequestMethod.POST)
	public String pioKnjizenjeP(@PathVariable("tip_fond") Integer tip_fond ,@PathVariable("uplata") Integer uplata,
                @ModelAttribute("pio") PIO pp,HttpServletRequest request,Model model) 
        {

                if (userService.getlogStatus()==0)
               {
                   throw new DataAccessException("You're not logged!!!");
                   //return "redirect:/login";
               }
                List<ComboValues> pio_filijala=new ArrayList<>();
                pio_filijala.add(0, new ComboValues(0,""));
                pio_filijala.add(1, new ComboValues(1,"ZAPOSLENI BEOGRAD"));
                pio_filijala.add(2, new ComboValues(2,"ZAPOSLENI BEOGRAD NAKNADE"));
                pio_filijala.add(3, new ComboValues(3,"ZAPOSLENI BEOGRAD POGREBNO"));
                pio_filijala.add(4, new ComboValues(4,"ZAPOSLENI NOVI SAD"));
                pio_filijala.add(5, new ComboValues(5,"VOJSKA NOVI SAD"));
                pio_filijala.add(6, new ComboValues(6,"VOJSKA BEOGRAD"));
                pio_filijala.add(7, new ComboValues(7,"VOJSKA BEOGRAD NAKNADE"));
                pio_filijala.add(8, new ComboValues(8,"VOJSKA BEOGRAD POGREBNO"));
                pio_filijala.add(9, new ComboValues(9,"POLJOPRIVREDA BEOGRAD"));
                pio_filijala.add(10, new ComboValues(10,"POLJOPRIVREDA BEOGRAD NAKNADE"));
                pio_filijala.add(11, new ComboValues(11,"POLJOPRIVREDA BEOGRAD POGREBNO"));
                pio_filijala.add(12, new ComboValues(12,"POLJOPRIVREDA NOVI SAD"));
                pio_filijala.add(13, new ComboValues(13,"SAMOSTALCI NOVI SAD"));
                pio_filijala.add(14, new ComboValues(14,"SAMOSTALCI BEOGRAD"));
                pio_filijala.add(15, new ComboValues(15,"SAMOSTALCI BEOGRAD NAKNADE"));
                pio_filijala.add(16, new ComboValues(16,"SAMOSTALCI BEOGRAD POGREBNO"));  
                pio_filijala.add(17, new ComboValues(17,"PSOVO"));
                
                request.getSession().setAttribute("filijalavalue",tip_fond);
                request.getSession().setAttribute("zajednickipriliv",uplata);
                List<PENZIJE> pio =  pensionService.ParsePenzijeImport(tip_fond,uplata);
                List<UGASENI> upp=pensionService.getRsetUgaseni();
		model.addAttribute("penzije", pio);
                model.addAttribute("ugaseni", upp);
                Integer ispravni=pio.get(0).getIspravno();
                List<UplatePIO> pio_uplate = (List<UplatePIO>) request.getSession().getAttribute("pension_information");
                model.addAttribute("ukupan_priliv",request.getSession().getAttribute("ukupan_priliv"));
                request.getSession().setAttribute("piobuttonvalue",tip_fond);
                model.addAttribute("idkg",request.getSession().getAttribute("idkg"));
                model.addAttribute("pension_book",pio_uplate);
                model.addAttribute("ukupaniznos",pensionService.IznosiPENZIJE(tip_fond));
                model.addAttribute("pio_filijale", pio_filijala);
                if (ispravni==1)
                {
                    model.addAttribute("informacija", "IMPORT PENZIJA USPESNO URADJEN");
                }
                if (ispravni==0)
                {
                    model.addAttribute("informacija", "provera");
                }
		return "/pension/pensiontable";

	}
        @RequestMapping(value = "/penzijeUplate", method = RequestMethod.GET)
	public String pioUplate(Model model) 
        {

                if (userService.getlogStatus()==0)
               {
                   throw new DataAccessException("You're not logged!!!");
                   //return "redirect:/login";
               }

                String akcija="select";
                
                List<UplatePIO> up=pensionService.PodacioUplatama(akcija,null, null,null);
                model.addAttribute("uplate", up);
                Integer up_len=0;
                if (up==null)
                {
                    up_len=0;
                }
                else
                {
                    up_len=up.size();
                }
                if ( up_len >0)
                {
                    model.addAttribute("chkcount",up.get(0).getCnt());
                }
                if ( up_len ==0)
                {
                    model.addAttribute("chkcount",0);

                }
                model.addAttribute("informacija", "0");
		return "/pension/pensionuplate";

	}
        @RequestMapping(value = "/getPPayment/{ppayment_date}", method = RequestMethod.POST)
	public String gerPayment(@PathVariable("ppayment_date") @DateTimeFormat(pattern="yyyy-MM-dd") Date ppayment_date,Model model) 
        {

            if (userService.getlogStatus()==0)
               {
                   throw new DataAccessException("You're not logged!!!");
                   //return "redirect:/login";
               }

            String akcija="select";
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
            String dat=dateFormat.format(ppayment_date);
            
            if (!dat.isEmpty())
            {
            
                List<UplatePIO> up=pensionService.PodacioUplatama(akcija,dat, null,null);
                model.addAttribute("uplate",up );
                Integer up_len=0;
                if (up==null)
                {
                    up_len=0;
                }
                else
                {
                    up_len=up.size();
                }
                if ( up_len >0)
                {
                    model.addAttribute("chkcount",up.get(0).getCnt());
                }
                if ( up_len ==0)
                {
                    model.addAttribute("chkcount",0);

                }
                model.addAttribute("informacija", "0");
            }
            else
            {
                model.addAttribute("chkcount",0);
                model.addAttribute("informacija", "UNESITE DATUM ZA PRETRAGU");
            }
            
	    return "/pension/pensionuplate";

	}
        /* @RequestMapping(value = "/filijalaDetails/{filijalap}/{tipfilijala}", method = RequestMethod.POST)
	public String pioFilijaleDetalji(@PathVariable("filijalap") String filijalap,@PathVariable("tipfilijala") Integer tipfilijala,           
                Model model,HttpServletResponse response, HttpServletRequest request) 
        {

               if (userService.getlogStatus()==0)
               {
                   throw new DataAccessException("You're not logged!!!");
                  
               }
              
                Integer retval=0;
                String inf="";
                request.getSession().setAttribute("filijalavalue",filijalap);
                request.getSession().setAttribute("piobuttonvalue",tipfilijala);
                List<ComboValues> result =pensionService.PIO_FAJL_ZA_KNJIZENJE(filijalap, tipfilijala);
                retval=result.get(0).getValue();
                inf=result.get(0).getLabel();
                if (retval==0)
                {
                    model.addAttribute("ukupaniznos",pensionService.IznosiPENZIJE(0));   
                }
                List<UplatePIO> pio_uplate = (List<UplatePIO>) request.getSession().getAttribute("pension_information");
                model.addAttribute("header_book",pio_uplate.get(0));
                model.addAttribute("informacija", inf);
                //SetCookieValue(upl, response);
		return "/pio/piotable";

	}*/
         @RequestMapping(value = "/penzijeKnjizenje/{idp_knjiz}/details", method = RequestMethod.GET)
	public String pioDetalji(@PathVariable("idp_knjiz") Integer idp_knjiz ,Model model) 
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
                PENZIJE piodet =pensionService.PENZIJEErrorDetails(idp_knjiz);
                model.addAttribute("penzije",piodet);
                model.addAttribute("statusiracuna", statusracuna);
		return "/pension/pensiondetails";

	}
        @RequestMapping(value = "/paccountCheck", method = RequestMethod.POST)
	public String racunProvera(Model model,HttpServletRequest request) 
        {

                if (userService.getlogStatus()==0)
               {
                   throw new DataAccessException("You're not logged!!!");
                   //return "redirect:/login";
               }
                
                List<ComboValues> pio_filijala=new ArrayList<>();
                pio_filijala.add(0, new ComboValues(0,""));
                pio_filijala.add(1, new ComboValues(1,"ZAPOSLENI BEOGRAD"));
                pio_filijala.add(2, new ComboValues(2,"ZAPOSLENI BEOGRAD NAKNADE"));
                pio_filijala.add(3, new ComboValues(3,"ZAPOSLENI BEOGRAD POGREBNO"));
                pio_filijala.add(4, new ComboValues(4,"ZAPOSLENI NOVI SAD"));
                pio_filijala.add(5, new ComboValues(5,"VOJSKA NOVI SAD"));
                pio_filijala.add(6, new ComboValues(6,"VOJSKA BEOGRAD"));
                pio_filijala.add(7, new ComboValues(7,"VOJSKA BEOGRAD NAKNADE"));
                pio_filijala.add(8, new ComboValues(8,"VOJSKA BEOGRAD POGREBNO"));
                pio_filijala.add(9, new ComboValues(9,"POLJOPRIVREDA BEOGRAD"));
                pio_filijala.add(10, new ComboValues(10,"POLJOPRIVREDA BEOGRAD NAKNADE"));
                pio_filijala.add(11, new ComboValues(11,"POLJOPRIVREDA BEOGRAD POGREBNO"));
                pio_filijala.add(12, new ComboValues(12,"POLJOPRIVREDA NOVI SAD"));
                pio_filijala.add(13, new ComboValues(13,"SAMOSTALCI NOVI SAD"));
                pio_filijala.add(14, new ComboValues(14,"SAMOSTALCI BEOGRAD"));
                pio_filijala.add(15, new ComboValues(15,"SAMOSTALCI BEOGRAD NAKNADE"));
                pio_filijala.add(16, new ComboValues(16,"SAMOSTALCI BEOGRAD POGREBNO")); 
                pio_filijala.add(17, new ComboValues(17,"PSOVO"));
                
                String p_akcija="load";
                
                List<PENZIJE> gr=pensionService.AccountCheck(p_akcija);
                List<PENZIJE> pp=pensionService.getRset();
                List<UGASENI> upp=pensionService.getRsetUgaseni();
                
                
                
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
                    model.addAttribute("penzije", pp);
                    model.addAttribute("ugaseni", upp);
                    model.addAttribute("idkg",request.getSession().getAttribute("idkg"));
                    model.addAttribute("ukupaniznos",pp.get(0).getIznos_rate());  
                    model.addAttribute("ukupan_priliv",request.getSession().getAttribute("ukupan_priliv"));
                    model.addAttribute("pio_filijale", pio_filijala);
                    model.addAttribute("informacija", "3");
                }
                if ( gr_len>0 && pp_len==0)
                {
                    model.addAttribute("penzije", gr);
                    model.addAttribute("informacija", "provera");
                }
		return "/pension/pensiontable";

	}
         @RequestMapping(value = "/paccountCheck", method = RequestMethod.GET)
	public String racunProveraGet( Model model,HttpServletResponse response,HttpServletRequest request) 
        {

                if (userService.getlogStatus()==0)
               {
                   throw new DataAccessException("You're not logged!!!");
                   //return "redirect:/login";
               }
                
                List<ComboValues> pio_filijala=new ArrayList<>();
                pio_filijala.add(0, new ComboValues(0,""));
                pio_filijala.add(1, new ComboValues(1,"ZAPOSLENI BEOGRAD"));
                pio_filijala.add(2, new ComboValues(2,"ZAPOSLENI BEOGRAD NAKNADE"));
                pio_filijala.add(3, new ComboValues(3,"ZAPOSLENI BEOGRAD POGREBNO"));
                pio_filijala.add(4, new ComboValues(4,"ZAPOSLENI NOVI SAD"));
                pio_filijala.add(5, new ComboValues(5,"VOJSKA NOVI SAD"));
                pio_filijala.add(6, new ComboValues(6,"VOJSKA BEOGRAD"));
                pio_filijala.add(7, new ComboValues(7,"VOJSKA BEOGRAD NAKNADE"));
                pio_filijala.add(8, new ComboValues(8,"VOJSKA BEOGRAD POGREBNO"));
                pio_filijala.add(9, new ComboValues(9,"POLJOPRIVREDA BEOGRAD"));
                pio_filijala.add(10, new ComboValues(10,"POLJOPRIVREDA BEOGRAD NAKNADE"));
                pio_filijala.add(11, new ComboValues(11,"POLJOPRIVREDA BEOGRAD POGREBNO"));
                pio_filijala.add(12, new ComboValues(12,"POLJOPRIVREDA NOVI SAD"));
                pio_filijala.add(13, new ComboValues(13,"SAMOSTALCI NOVI SAD"));
                pio_filijala.add(14, new ComboValues(14,"SAMOSTALCI BEOGRAD"));
                pio_filijala.add(15, new ComboValues(15,"SAMOSTALCI BEOGRAD NAKNADE"));
                pio_filijala.add(16, new ComboValues(16,"SAMOSTALCI BEOGRAD POGREBNO")); 
                pio_filijala.add(17, new ComboValues(17,"PSOVO"));
                
                model.addAttribute("penzije", pensionService.AccountCheckTable());
                List<UplatePIO> pio_uplate = (List<UplatePIO>) request.getSession().getAttribute("pension_information");
                model.addAttribute("ukupan_priliv",request.getSession().getAttribute("ukupan_priliv"));
                model.addAttribute("pension_book",pio_uplate);
                model.addAttribute("pio_filijale", pio_filijala);
                model.addAttribute("informacija", "provera");
		return "/pension/pensiontable";

	}
       @RequestMapping(value = "/penzijeKnjizenje/{id_knjiz1}/details", method = RequestMethod.POST)
	public String pioDetaljiUpdate(@PathVariable("id_knjiz1") Integer id_knjiz1 ,@ModelAttribute("penzije") PENZIJE penzije) 
        {

                if (userService.getlogStatus()==0)
               {
                   throw new DataAccessException("You're not logged!!!");
                   //return "redirect:/login";
               }

                String akcija="update";
                pensionService.SP_PENZIJE(akcija,penzije.getFilijala(),penzije.getPartija(),penzije.getIme(),penzije.getJmbg(),penzije.getIznos_rate().toString(),id_knjiz1,penzije.getStatus_racuna());
		
                return "redirect:/paccountCheck";

	}

        @RequestMapping(value = "/headerPInformation/{idate}/{tms_iznos}", method = RequestMethod.POST)
	public String HeaderInformation(@PathVariable("idate") @DateTimeFormat(pattern="yyyy-MM-dd") Date idate,
                @PathVariable("tms_iznos") String tms_iznos ,Model model,HttpServletRequest request,SessionStatus status) 
        {
               
            if (userService.getlogStatus()==0)
            {
                throw new DataAccessException("You're not logged!!!");
                //return "redirect:/login";
            }
            
                List<ComboValues> pio_filijala=new ArrayList<>();
                pio_filijala.add(0, new ComboValues(0,""));
                pio_filijala.add(1, new ComboValues(1,"ZAPOSLENI BEOGRAD"));
                pio_filijala.add(2, new ComboValues(2,"ZAPOSLENI BEOGRAD NAKNADE"));
                pio_filijala.add(3, new ComboValues(3,"ZAPOSLENI BEOGRAD POGREBNO"));
                pio_filijala.add(4, new ComboValues(4,"ZAPOSLENI NOVI SAD"));
                pio_filijala.add(5, new ComboValues(5,"VOJSKA NOVI SAD"));
                pio_filijala.add(6, new ComboValues(6,"VOJSKA BEOGRAD"));
                pio_filijala.add(7, new ComboValues(7,"VOJSKA BEOGRAD NAKNADE"));
                pio_filijala.add(8, new ComboValues(8,"VOJSKA BEOGRAD POGREBNO"));
                pio_filijala.add(9, new ComboValues(9,"POLJOPRIVREDA BEOGRAD"));
                pio_filijala.add(10, new ComboValues(10,"POLJOPRIVREDA BEOGRAD NAKNADE"));
                pio_filijala.add(11, new ComboValues(11,"POLJOPRIVREDA BEOGRAD POGREBNO"));
                pio_filijala.add(12, new ComboValues(12,"POLJOPRIVREDA NOVI SAD"));
                pio_filijala.add(13, new ComboValues(13,"SAMOSTALCI NOVI SAD"));
                pio_filijala.add(14, new ComboValues(14,"SAMOSTALCI BEOGRAD"));
                pio_filijala.add(15, new ComboValues(15,"SAMOSTALCI BEOGRAD NAKNADE"));
                pio_filijala.add(16, new ComboValues(16,"SAMOSTALCI BEOGRAD POGREBNO")); 
                pio_filijala.add(17, new ComboValues(17,"PSOVO"));
                
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
            String dat=dateFormat.format(idate);
            String akcija="header";
            tms_iznos=tms_iznos.replace(",", ".");
            List<UplatePIO> upl=pensionService.PodacioUplatama(akcija,dat,tms_iznos,null);
            idk=upl.get(0).getIdkg();

            request.getSession().setAttribute("pension_information",upl);


            //SetCookieValue(upl,response);
            
            ///KAD IZBACI NumberFormatException to znaci da  ne moze da dodje do stavke iz liste!!!
            model.addAttribute("pension_book",upl);
            model.addAttribute("pio_filijale", pio_filijala);
            model.addAttribute("informacija", "0");
            return "/pension/pensiontable";
         }
        @RequestMapping(value = "/getPPaymentTable/{lista}", method = RequestMethod.POST)
	public String PaymentTable(@PathVariable("lista") String lista ,Model model,HttpServletRequest request,SessionStatus status) 
        {
               
            if (userService.getlogStatus()==0)
            {
                throw new DataAccessException("You're not logged!!!");
            }
            
                List<ComboValues> pio_filijala=new ArrayList<>();
                pio_filijala.add(0, new ComboValues(0,""));
                pio_filijala.add(1, new ComboValues(1,"ZAPOSLENI BEOGRAD"));
                pio_filijala.add(2, new ComboValues(2,"ZAPOSLENI BEOGRAD NAKNADE"));
                pio_filijala.add(3, new ComboValues(3,"ZAPOSLENI BEOGRAD POGREBNO"));
                pio_filijala.add(4, new ComboValues(4,"ZAPOSLENI NOVI SAD"));
                pio_filijala.add(5, new ComboValues(5,"VOJSKA NOVI SAD"));
                pio_filijala.add(6, new ComboValues(6,"VOJSKA BEOGRAD"));
                pio_filijala.add(7, new ComboValues(7,"VOJSKA BEOGRAD NAKNADE"));
                pio_filijala.add(8, new ComboValues(8,"VOJSKA BEOGRAD POGREBNO"));
                pio_filijala.add(9, new ComboValues(9,"POLJOPRIVREDA BEOGRAD"));
                pio_filijala.add(10, new ComboValues(10,"POLJOPRIVREDA BEOGRAD NAKNADE"));
                pio_filijala.add(11, new ComboValues(11,"POLJOPRIVREDA BEOGRAD POGREBNO"));
                pio_filijala.add(12, new ComboValues(12,"POLJOPRIVREDA NOVI SAD"));
                pio_filijala.add(13, new ComboValues(13,"SAMOSTALCI NOVI SAD"));
                pio_filijala.add(14, new ComboValues(14,"SAMOSTALCI BEOGRAD"));
                pio_filijala.add(15, new ComboValues(15,"SAMOSTALCI BEOGRAD NAKNADE"));
                pio_filijala.add(16, new ComboValues(16,"SAMOSTALCI BEOGRAD POGREBNO"));  
                pio_filijala.add(17, new ComboValues(17,"PSOVO"));
            //DateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
            //String dat=dateFormat.format(idate);
            String akcija="header";

            if(!lista.isEmpty())
            {
                List<UplatePIO> upl=pensionService.PodacioUplatama(akcija,null,"0",lista);
                idk=upl.get(0).getIdkg();
                BigDecimal ukupan_iznos=upl.get(0).getUkupan_iznos();

                request.getSession().setAttribute("pension_information",upl);
                request.getSession().setAttribute("idkg",idk);
                request.getSession().setAttribute("ukupan_priliv",ukupan_iznos);

                //SetCookieValue(upl,response);

                ///KAD IZBACI NumberFormatException to znaci da  ne moze da dodje do stavke iz liste!!!
                model.addAttribute("pension_book",upl);
                model.addAttribute("informacija", "0");
                model.addAttribute("idkg", upl.get(0).getIdkg());
                model.addAttribute("ukupan_priliv",ukupan_iznos);
                model.addAttribute("pio_filijale", pio_filijala); 
                return "/pension/pensiontable";
            }
            else
            {

                model.addAttribute("informacija", "NIJEDNU UPLATU NISTE IZABRALI IZ LISTE!!!");
                return "/pension/pensionuplate";
            }
            
         }
      
       /* @RequestMapping(value = "/sendPBooking/{id_knjizenja}/{tms_id}/{knjizenje_iznos}/{tip_fil}", method = RequestMethod.POST)
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

                knjizenje_iznos=knjizenje_iznos.replace(",", ".");
                List<ComboValues> upl=pensionService.Posalji_Na_Knjizenje(akcija, id_knjizenja, tms_id, knjizenje_iznos,null,tip_fil);
                retval=upl.get(0).getValue();
                inf=upl.get(0).getLabel();
            }
            model.addAttribute("informacija",inf);
            
            if (retval==0)
            {
                return "redirect:/bookingPItems/" + tip_fil + "/" + inf + "/" + id_knjizenja;
            }
            else
                return "/pension/pensiontable";
         }*/
        @RequestMapping(value = "/sendPBooking/{id_knjizenja}/{tip_fil}", method = RequestMethod.POST)
	public String Booking(@PathVariable("id_knjizenja") Integer id_knjizenja,@PathVariable("tip_fil") Integer tip_fil ,Model model) 
        {
               
             if (userService.getlogStatus()==0)
            {
                throw new DataAccessException("You're not logged!!!");
            }

                List<ComboValues> pio_filijala=new ArrayList<>();
                pio_filijala.add(0, new ComboValues(0,""));
                pio_filijala.add(1, new ComboValues(1,"ZAPOSLENI BEOGRAD"));
                pio_filijala.add(2, new ComboValues(2,"ZAPOSLENI BEOGRAD NAKNADE"));
                pio_filijala.add(3, new ComboValues(3,"ZAPOSLENI BEOGRAD POGREBNO"));
                pio_filijala.add(4, new ComboValues(4,"ZAPOSLENI NOVI SAD"));
                pio_filijala.add(5, new ComboValues(5,"VOJSKA NOVI SAD"));
                pio_filijala.add(6, new ComboValues(6,"VOJSKA BEOGRAD"));
                pio_filijala.add(7, new ComboValues(7,"VOJSKA BEOGRAD NAKNADE"));
                pio_filijala.add(8, new ComboValues(8,"VOJSKA BEOGRAD POGREBNO"));
                pio_filijala.add(9, new ComboValues(9,"POLJOPRIVREDA BEOGRAD"));
                pio_filijala.add(10, new ComboValues(10,"POLJOPRIVREDA BEOGRAD NAKNADE"));
                pio_filijala.add(11, new ComboValues(11,"POLJOPRIVREDA BEOGRAD POGREBNO"));
                pio_filijala.add(12, new ComboValues(12,"POLJOPRIVREDA NOVI SAD"));
                pio_filijala.add(13, new ComboValues(13,"SAMOSTALCI NOVI SAD"));
                pio_filijala.add(14, new ComboValues(14,"SAMOSTALCI BEOGRAD"));
                pio_filijala.add(15, new ComboValues(15,"SAMOSTALCI BEOGRAD NAKNADE"));
                pio_filijala.add(16, new ComboValues(16,"SAMOSTALCI BEOGRAD POGREBNO")); 
                pio_filijala.add(17, new ComboValues(17,"PSOVO"));
                
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
                //knjizenje_iznos=knjizenje_iznos.replace(",", ".");
                List<ComboValues> upl=pensionService.Posalji_Na_Knjizenje(akcija, id_knjizenja, null, null,null,tip_fil);
                retval=upl.get(0).getValue();
                inf=upl.get(0).getLabel();
            }
            model.addAttribute("informacija",inf);
            model.addAttribute("pio_filijale", pio_filijala);
            
            if (retval==0)
            {
                return "redirect:/bookingPItems/" + tip_fil + "/" + inf + "/" + id_knjizenja;
            }
            else
                return "/pension/pensiontable";
         }
        
        @RequestMapping(value = "/bookingPItems/{knjizenje_datum}/{fl}/{book_idk}", method = RequestMethod.POST)
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
                pio_filijala.add(1, new ComboValues(1,"ZAPOSLENI BEOGRAD"));
                pio_filijala.add(2, new ComboValues(2,"ZAPOSLENI BEOGRAD NAKNADE"));
                pio_filijala.add(3, new ComboValues(3,"ZAPOSLENI BEOGRAD POGREBNO"));
                pio_filijala.add(4, new ComboValues(4,"ZAPOSLENI NOVI SAD"));
                pio_filijala.add(5, new ComboValues(5,"VOJSKA NOVI SAD"));
                pio_filijala.add(6, new ComboValues(6,"VOJSKA BEOGRAD"));
                pio_filijala.add(7, new ComboValues(7,"VOJSKA BEOGRAD NAKNADE"));
                pio_filijala.add(8, new ComboValues(8,"VOJSKA BEOGRAD POGREBNO"));
                pio_filijala.add(9, new ComboValues(9,"POLJOPRIVREDA BEOGRAD"));
                pio_filijala.add(10, new ComboValues(10,"POLJOPRIVREDA BEOGRAD NAKNADE"));
                pio_filijala.add(11, new ComboValues(11,"POLJOPRIVREDA BEOGRAD POGREBNO"));
                pio_filijala.add(12, new ComboValues(12,"POLJOPRIVREDA NOVI SAD"));
                pio_filijala.add(13, new ComboValues(13,"SAMOSTALCI NOVI SAD"));
                pio_filijala.add(14, new ComboValues(14,"SAMOSTALCI BEOGRAD"));
                pio_filijala.add(15, new ComboValues(15,"SAMOSTALCI BEOGRAD NAKNADE"));
                pio_filijala.add(16, new ComboValues(16,"SAMOSTALCI BEOGRAD POGREBNO"));  
                pio_filijala.add(17, new ComboValues(17,"PSOVO"));
            
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
            String dat=dateFormat.format(knjizenje_datum);
            
            String akcija="select";
            List<ComboValues> upl=pensionService.Posalji_Na_Knjizenje(akcija, null, null, null,dat,fl);
            List<StavkeKnjizenja> sk=pensionService.getStavkeKnjizenja();

            model.addAttribute("informacija","");
            List<Iznosi> izn=pensionService.StavkeKnjizenja(akcija, fl,null,dat);
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
            return "/pension/pensionstavke";
           
         }
        @RequestMapping(value = "/bookingPItems", method = RequestMethod.GET)
	public String BookingItemsFull(Model model) 
        {
               
             if (userService.getlogStatus()==0)
            {
                throw new DataAccessException("You're not logged!!!");
                //return "redirect:/login";
            }
                List<ComboValues> pio_filijala=new ArrayList<>();
                pio_filijala.add(0, new ComboValues(0,""));
                pio_filijala.add(1, new ComboValues(1,"ZAPOSLENI BEOGRAD"));
                pio_filijala.add(2, new ComboValues(2,"ZAPOSLENI BEOGRAD NAKNADE"));
                pio_filijala.add(3, new ComboValues(3,"ZAPOSLENI BEOGRAD POGREBNO"));
                pio_filijala.add(4, new ComboValues(4,"ZAPOSLENI NOVI SAD"));
                pio_filijala.add(5, new ComboValues(5,"VOJSKA NOVI SAD"));
                pio_filijala.add(6, new ComboValues(6,"VOJSKA BEOGRAD"));
                pio_filijala.add(7, new ComboValues(7,"VOJSKA BEOGRAD NAKNADE"));
                pio_filijala.add(8, new ComboValues(8,"VOJSKA BEOGRAD POGREBNO"));
                pio_filijala.add(9, new ComboValues(9,"POLJOPRIVREDA BEOGRAD"));
                pio_filijala.add(10, new ComboValues(10,"POLJOPRIVREDA BEOGRAD NAKNADE"));
                pio_filijala.add(11, new ComboValues(11,"POLJOPRIVREDA BEOGRAD POGREBNO"));
                pio_filijala.add(12, new ComboValues(12,"POLJOPRIVREDA NOVI SAD"));
                pio_filijala.add(13, new ComboValues(13,"SAMOSTALCI NOVI SAD"));
                pio_filijala.add(14, new ComboValues(14,"SAMOSTALCI BEOGRAD"));
                pio_filijala.add(15, new ComboValues(15,"SAMOSTALCI BEOGRAD NAKNADE"));
                pio_filijala.add(16, new ComboValues(16,"SAMOSTALCI BEOGRAD POGREBNO"));  
                pio_filijala.add(17, new ComboValues(17,"PSOVO"));
            
            String akcija="select";
            List<ComboValues> upl=pensionService.Posalji_Na_Knjizenje(akcija, null, null, null,null,null);
            List<StavkeKnjizenja> sk=pensionService.getStavkeKnjizenja();
            List<Iznosi> izn=pensionService.StavkeKnjizenja(null, null,null,null);
            
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
            return "/pension/pensionstavke";
           
         }
        
        @RequestMapping(value = "/bookingPItems/{fil_fil}/{info}/{b_idk}", method = RequestMethod.GET)
	public String BookingItemsForm(@PathVariable("fil_fil") Integer fil_fil,@PathVariable("info") String info,@PathVariable("b_idk") Integer b_idk,Model model) 
        {
               
                List<ComboValues> pio_filijala=new ArrayList<>();
                pio_filijala.add(0, new ComboValues(0,""));
                pio_filijala.add(1, new ComboValues(1,"ZAPOSLENI BEOGRAD"));
                pio_filijala.add(2, new ComboValues(2,"ZAPOSLENI BEOGRAD NAKNADE"));
                pio_filijala.add(3, new ComboValues(3,"ZAPOSLENI BEOGRAD POGREBNO"));
                pio_filijala.add(4, new ComboValues(4,"ZAPOSLENI NOVI SAD"));
                pio_filijala.add(5, new ComboValues(5,"VOJSKA NOVI SAD"));
                pio_filijala.add(6, new ComboValues(6,"VOJSKA BEOGRAD"));
                pio_filijala.add(7, new ComboValues(7,"VOJSKA BEOGRAD NAKNADE"));
                pio_filijala.add(8, new ComboValues(8,"VOJSKA BEOGRAD POGREBNO"));
                pio_filijala.add(9, new ComboValues(9,"POLJOPRIVREDA BEOGRAD"));
                pio_filijala.add(10, new ComboValues(10,"POLJOPRIVREDA BEOGRAD NAKNADE"));
                pio_filijala.add(11, new ComboValues(11,"POLJOPRIVREDA BEOGRAD POGREBNO"));
                pio_filijala.add(12, new ComboValues(12,"POLJOPRIVREDA NOVI SAD"));
                pio_filijala.add(13, new ComboValues(13,"SAMOSTALCI NOVI SAD"));
                pio_filijala.add(14, new ComboValues(14,"SAMOSTALCI BEOGRAD"));
                pio_filijala.add(15, new ComboValues(15,"SAMOSTALCI BEOGRAD NAKNADE"));
                pio_filijala.add(16, new ComboValues(16,"SAMOSTALCI BEOGRAD POGREBNO"));  
                pio_filijala.add(17, new ComboValues(17,"PSOVO"));
           
            String akcija="select";
            //List<ComboValues> upl=pensionService.Posalji_Na_Knjizenje(akcija, null, null, null,null,null);
            List<StavkeKnjizenja> sk=pensionService.getStavkeKnjizenja();
            List<Iznosi> izn=pensionService.StavkeKnjizenja(akcija,fil_fil,b_idk,null );
           
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
            return "/pension/pensionstavke";
           
         }
        @RequestMapping(value = "/changePAll", method = RequestMethod.POST)
	public String changeAll() 
        {

                if (userService.getlogStatus()==0)
               {
                   throw new DataAccessException("You're not logged!!!");
                   //return "redirect:/login";
               }

                String akcija="sve";
                pensionService.SP_PENZIJE(akcija,null,null,null,null,null,null,null);
		return "redirect:/paccountCheck";

	}
        
        @RequestMapping(value = "/pclaimAccounts", method = RequestMethod.GET)
        public String claimAccounts(Model model) {
            // create some sample data

           if (userService.getlogStatus()==0)
           {
                throw new DataAccessException("You're not logged!!!");
                   //return "redirect:/login";
            }

            List<ClaimRacuni> claimClients=pensionService.ClaimAccounts();
            model.addAttribute("claimracuni", claimClients);        
            return "/pension/claimaccounts";
        }
        
        @RequestMapping(value = "/psendToProcess/{kn_datum}/{fl1}/{sp_idk}", method = RequestMethod.POST)
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
                pio_filijala.add(1, new ComboValues(1,"ZAPOSLENI BEOGRAD"));
                pio_filijala.add(2, new ComboValues(2,"ZAPOSLENI BEOGRAD NAKNADE"));
                pio_filijala.add(3, new ComboValues(3,"ZAPOSLENI BEOGRAD POGREBNO"));
                pio_filijala.add(4, new ComboValues(4,"ZAPOSLENI NOVI SAD"));
                pio_filijala.add(5, new ComboValues(5,"VOJSKA NOVI SAD"));
                pio_filijala.add(6, new ComboValues(6,"VOJSKA BEOGRAD"));
                pio_filijala.add(7, new ComboValues(7,"VOJSKA BEOGRAD NAKNADE"));
                pio_filijala.add(8, new ComboValues(8,"VOJSKA BEOGRAD POGREBNO"));
                pio_filijala.add(9, new ComboValues(9,"POLJOPRIVREDA BEOGRAD"));
                pio_filijala.add(10, new ComboValues(10,"POLJOPRIVREDA BEOGRAD NAKNADE"));
                pio_filijala.add(11, new ComboValues(11,"POLJOPRIVREDA BEOGRAD POGREBNO"));
                pio_filijala.add(12, new ComboValues(12,"POLJOPRIVREDA NOVI SAD"));
                pio_filijala.add(13, new ComboValues(13,"SAMOSTALCI NOVI SAD"));
                pio_filijala.add(14, new ComboValues(14,"SAMOSTALCI BEOGRAD"));
                pio_filijala.add(15, new ComboValues(15,"SAMOSTALCI BEOGRAD NAKNADE"));
                pio_filijala.add(16, new ComboValues(16,"SAMOSTALCI BEOGRAD POGREBNO"));
                pio_filijala.add(17, new ComboValues(17,"PSOVO"));
                
                
                String akcija="process";
                DateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
                String dat=dateFormat.format(kn_datum);
                List<ComboValues> upl=pensionService.Posalji_Na_Knjizenje(akcija, null, null, null,dat,fl1);
                List<StavkeKnjizenja> sk=pensionService.getStavkeKnjizenja();
                List<Iznosi> izn=pensionService.StavkeKnjizenja("select", fl1,sp_idk,dat);
                
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
		return "/pension/pensionstavke";

	}
        @RequestMapping(value = "/downloadPExcel", method = RequestMethod.POST)
        public ModelAndView downloadExcel() {
            // create some sample data
            List<ExcelBook> claimClients = new ArrayList<>();
            List<ClaimRacuni> claimAccounts=pensionService.ClaimAccounts();
            //filijala,id_penzije,opis,jmbg,iznos
            for (ClaimRacuni claimAccount : claimAccounts)
            {
                claimClients.add(new ExcelBook(claimAccount.getFilijala(),claimAccount.getId_penzije(), claimAccount.getIme(),claimAccount.getJmbg(),claimAccount.getIznos(),claimAccount.getPartija(),"", "","",""));

            }

            return new ModelAndView("vUgaseniRacuni", "excelPenzije", claimClients);
        }
        
        /* MONITORNIG SA IDK*/
        @RequestMapping(value = "/pmonitoring/{monitoring_datum}/{monitoring_fl}/{monitoring_idk}", method = RequestMethod.POST)
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
                pio_filijala.add(1, new ComboValues(1,"ZAPOSLENI BEOGRAD"));
                pio_filijala.add(2, new ComboValues(2,"ZAPOSLENI BEOGRAD NAKNADE"));
                pio_filijala.add(3, new ComboValues(3,"ZAPOSLENI BEOGRAD POGREBNO"));
                pio_filijala.add(4, new ComboValues(4,"ZAPOSLENI NOVI SAD"));
                pio_filijala.add(5, new ComboValues(5,"VOJSKA NOVI SAD"));
                pio_filijala.add(6, new ComboValues(6,"VOJSKA BEOGRAD"));
                pio_filijala.add(7, new ComboValues(7,"VOJSKA BEOGRAD NAKNADE"));
                pio_filijala.add(8, new ComboValues(8,"VOJSKA BEOGRAD POGREBNO"));
                pio_filijala.add(9, new ComboValues(9,"POLJOPRIVREDA BEOGRAD"));
                pio_filijala.add(10, new ComboValues(10,"POLJOPRIVREDA BEOGRAD NAKNADE"));
                pio_filijala.add(11, new ComboValues(11,"POLJOPRIVREDA BEOGRAD POGREBNO"));
                pio_filijala.add(12, new ComboValues(12,"POLJOPRIVREDA NOVI SAD"));
                pio_filijala.add(13, new ComboValues(13,"SAMOSTALCI NOVI SAD"));
                pio_filijala.add(14, new ComboValues(14,"SAMOSTALCI BEOGRAD"));
                pio_filijala.add(15, new ComboValues(15,"SAMOSTALCI BEOGRAD NAKNADE"));
                pio_filijala.add(16, new ComboValues(16,"SAMOSTALCI BEOGRAD POGREBNO")); 
                pio_filijala.add(17, new ComboValues(17,"PSOVO"));
            
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
            String dat=dateFormat.format(monitoring_datum);
            
            DateFormat dateFormat1 = new SimpleDateFormat("YYYY-MM-dd");
            String dat1=dateFormat1.format(monitoring_datum);
            
            String akcija="monitoring_items";
            

            List<Iznosi> izn=pensionService.Monitoring(akcija, monitoring_fl,dat,monitoring_idk);
            List<UplatePIO> mon=pensionService.getMonitoring();
            List<StavkeKnjizenja> sk=pensionService.getStavkeKnjizenja();
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
            return "/pension/pensionmonitoring";
           
         }  
         /* MONITORNIG BEZ IDK*/
        @RequestMapping(value = "/pmonitoring/{monitoring_datum}/{monitoring_fl}", method = RequestMethod.POST)
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
                pio_filijala.add(1, new ComboValues(1,"ZAPOSLENI BEOGRAD"));
                pio_filijala.add(2, new ComboValues(2,"ZAPOSLENI BEOGRAD NAKNADE"));
                pio_filijala.add(3, new ComboValues(3,"ZAPOSLENI BEOGRAD POGREBNO"));
                pio_filijala.add(4, new ComboValues(4,"ZAPOSLENI NOVI SAD"));
                pio_filijala.add(5, new ComboValues(5,"VOJSKA NOVI SAD"));
                pio_filijala.add(6, new ComboValues(6,"VOJSKA BEOGRAD"));
                pio_filijala.add(7, new ComboValues(7,"VOJSKA BEOGRAD NAKNADE"));
                pio_filijala.add(8, new ComboValues(8,"VOJSKA BEOGRAD POGREBNO"));
                pio_filijala.add(9, new ComboValues(9,"POLJOPRIVREDA BEOGRAD"));
                pio_filijala.add(10, new ComboValues(10,"POLJOPRIVREDA BEOGRAD NAKNADE"));
                pio_filijala.add(11, new ComboValues(11,"POLJOPRIVREDA BEOGRAD POGREBNO"));
                pio_filijala.add(12, new ComboValues(12,"POLJOPRIVREDA NOVI SAD"));
                pio_filijala.add(13, new ComboValues(13,"SAMOSTALCI NOVI SAD"));
                pio_filijala.add(14, new ComboValues(14,"SAMOSTALCI BEOGRAD"));
                pio_filijala.add(15, new ComboValues(15,"SAMOSTALCI BEOGRAD NAKNADE"));
                pio_filijala.add(16, new ComboValues(16,"SAMOSTALCI BEOGRAD POGREBNO")); 
                pio_filijala.add(17, new ComboValues(17,"PSOVO"));

            DateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
            String dat=dateFormat.format(monitoring_datum);

            DateFormat dateFormat1 = new SimpleDateFormat("YYYY-MM-dd");
            String dat1=dateFormat1.format(monitoring_datum); 
            request.getSession().setAttribute("filijalamon",monitoring_fl);
            //request.getSession().setAttribute("monitoringdatum",dat);
            
            String akcija="monitoring_header";

            List<Iznosi> izn=pensionService.Monitoring(akcija, monitoring_fl,dat,null);
            List<UplatePIO> sk=pensionService.getMonitoring();
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
            return "/pension/pensionmonitoring";
           
         }  
         @RequestMapping(value = "/pmonitoring", method = RequestMethod.GET)
	public String MonitoringFull(Model model) 
        {
               
             if (userService.getlogStatus()==0)
            {
                throw new DataAccessException("You're not logged!!!");
                //return "redirect:/login";
            }
               List<ComboValues> pio_filijala=new ArrayList<>();
                pio_filijala.add(0, new ComboValues(0,""));
                pio_filijala.add(1, new ComboValues(1,"ZAPOSLENI BEOGRAD"));
                pio_filijala.add(2, new ComboValues(2,"ZAPOSLENI BEOGRAD NAKNADE"));
                pio_filijala.add(3, new ComboValues(3,"ZAPOSLENI BEOGRAD POGREBNO"));
                pio_filijala.add(4, new ComboValues(4,"ZAPOSLENI NOVI SAD"));
                pio_filijala.add(5, new ComboValues(5,"VOJSKA NOVI SAD"));
                pio_filijala.add(6, new ComboValues(6,"VOJSKA BEOGRAD"));
                pio_filijala.add(7, new ComboValues(7,"VOJSKA BEOGRAD NAKNADE"));
                pio_filijala.add(8, new ComboValues(8,"VOJSKA BEOGRAD POGREBNO"));
                pio_filijala.add(9, new ComboValues(9,"POLJOPRIVREDA BEOGRAD"));
                pio_filijala.add(10, new ComboValues(10,"POLJOPRIVREDA BEOGRAD NAKNADE"));
                pio_filijala.add(11, new ComboValues(11,"POLJOPRIVREDA BEOGRAD POGREBNO"));
                pio_filijala.add(12, new ComboValues(12,"POLJOPRIVREDA NOVI SAD"));
                pio_filijala.add(13, new ComboValues(13,"SAMOSTALCI NOVI SAD"));
                pio_filijala.add(14, new ComboValues(14,"SAMOSTALCI BEOGRAD"));
                pio_filijala.add(15, new ComboValues(15,"SAMOSTALCI BEOGRAD NAKNADE"));
                pio_filijala.add(16, new ComboValues(16,"SAMOSTALCI BEOGRAD POGREBNO")); 
                pio_filijala.add(17, new ComboValues(17,"PSOVO"));
            
            String akcija="monitoring_header";
            List<Iznosi> izn=pensionService.Monitoring(akcija, null,null,null);
            List<UplatePIO> sk=pensionService.getMonitoring();
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
            return "/pension/pensionmonitoring";
           
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
