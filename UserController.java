package com.projects.pio.controller;
import com.projects.pio.model.DatePicker;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.LinkedHashMap;
import java.util.List;
//import java.awt.List;
import java.util.Map;
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
import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.projects.pio.exceptions.DatabaseException;
import com.projects.pio.exceptions.DataAccessException;
import com.projects.pio.exceptions.CustomException;
import com.projects.pio.exceptions.GlobalException;
//import com.projects.halkom.exceptions.GlobalExceptionHandler;
import com.projects.pio.model.User;
import com.projects.pio.model.LoginUser;
import com.projects.pio.model.FormDate;
import com.projects.pio.service.UserService;
import com.projects.pio.validation.UserFormValidator;
import com.projects.pio.validation.FormDateValidator;
//import com.projects.halkom.exceptions.GlobalExceptionHandler;
import com.projects.pio.model.JsTreeNode;
import com.projects.pio.model.TData;
import com.projects.pio.model.Attributes;
import com.projects.pio.model.ComboValues;
import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
//import org.w3c.dom.NodeList;

//http://www.tikalk.com/redirectattributes-new-feature-spring-mvc-31/
//https://en.wikipedia.org/wiki/Post/Redirect/Get
//http://www.oschina.net/translate/spring-mvc-flash-attribute-example

@Controller
public class UserController {

	//private final Logger logger = LoggerFactory.getLogger(UserController.class);
        String nodetext="";
        String append="";
        String pass="";
        String usr="";

	@Autowired
	UserFormValidator userFormValidator;
        
        
        @Autowired
	FormDateValidator formDateValidator;
	
        List<JsTreeNode> tree;
        List<JsTreeNode> treeview;
        List<JsTreeNode> nodesList=new LinkedList<>();
	
        @InitBinder("user")
        protected void initUserBinder(WebDataBinder binder) {
        
            binder.setValidator(userFormValidator);
        }

        @InitBinder("formdate")
        protected void initPaymentBinder(WebDataBinder binder) {
            binder.setValidator(formDateValidator);
        }
        
	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
      
	@RequestMapping(value = {"/", "/login**"}, method = RequestMethod.GET)
	public String index(Model model) {
		//logger.debug("index()");
                LoginUser log = new LoginUser();
                log.setDisplayname("");
                log.setPassword("");
                log.setUsername("");
                userService.setlogStatus(0);
                model.addAttribute("loginForm", log);
                
		return "users/login";
	}
      
        @RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loging(@ModelAttribute("loginForm") LoginUser loguser,Model model, final RedirectAttributes redirectAttributes)
        {
	
            try
                {
                    if ("".equals(userService.DisplayNameByUserName(loguser.getUsername(),loguser.getPassword()).trim()))
                    {
                        throw new DataAccessException("User not found!" + "aa");
                    }
                    else
                    {

                        try
                        {
                           
                           String akcija="userLogged";
                           userService.executeProcUser(akcija,null, null, null, loguser.getUsername(), loguser.getPassword(), null, 1,1);
                           userService.setlogStatus(1);
                           usr=loguser.getUsername();
                           pass=loguser.getPassword();
                           if (!nodesList.isEmpty())
                           {
                               nodesList.clear();
                           }
                           tree= userService.getTreeView(userService.getidAuth());
                           treeview=GetTreeView();

                        }
                        catch (RuntimeException ex)
                        {
                            throw new DataAccessException(ex.getMessage());
                        }
                        nodetext="users";
                        //return "redirect:/users";*/
                        return "index";
                        //return "newtree";
                    }
                }
                catch (Exception ex)
                 {
                    throw new DataAccessException(ex.getMessage());
                 }
	}
        private List<JsTreeNode> GetTreeView()
        {
              
                    JsTreeNode node;
                    Attributes attr;
                    Attributes attr_child;
                    int index=1;
                    String node_num="0";

                    for (JsTreeNode jt : tree)
                    {
                        node = new JsTreeNode();
                        node.setId("rootnode" + index);
                        node.setText(jt.getText());
                        attr=jt.getData();
                        attr.setFdata(nodetext);
                        node.setData(attr);
                        node.setChildren(new LinkedList<JsTreeNode>());
                        
                       if (!jt.getId().equals(node_num))
                       {
                           for (JsTreeNode jt_node : tree)   
                           {
                               JsTreeNode cnode = new JsTreeNode();
                               TData tdat=jt_node.getMetadata();
                               attr_child=jt_node.getData();
                               cnode.setId("childnode" + tdat.getNodeid());
                               cnode.setText(tdat.getNodename());
                               attr_child.setFdata(nodetext);
                               cnode.setData(attr_child);
                               if (jt_node.getId().equals(jt.getId()))
                               {
                                   node.getChildren().add(cnode);
                               }

                            }
                       }  
                            if (!jt.getId().equals(node_num))
                            {
                                nodesList.add(node);
                            }
                            node_num=jt.getId();
                            index++;
                    }
                    return nodesList;
        }
        
        @RequestMapping(value = "/closeEvent", method = RequestMethod.POST)
	public  String closeWind(Model model) 
        {
                String akcija="userLogged";
                userService.executeProcUser(akcija,null, null, null, usr, pass, null, null,0);
                //userService.setlogStatus(0);
                return null;
         }
        
        @RequestMapping(value = "/backEvent", method = RequestMethod.GET)
	public  String backToApplication(Model model) 
        {
              
                return "index";
         }
        
        
        @RequestMapping(value = "/logOut", method = RequestMethod.POST)
	public  String logOutWind(Model model) 
        {
                String akcija="userLogged";
                userService.executeProcUser(akcija,null, null, null, usr, pass, null, null,0);
                userService.setlogStatus(0);
                return "redirect:/logOut";
         }
        @RequestMapping(value = "/logOut", method = RequestMethod.GET)
	public  String logOut(Model model) 
        {
                LoginUser log = new LoginUser();
                log.setDisplayname("");
                log.setPassword("");
                log.setUsername("");
                model.addAttribute("loginForm", log);
		return "users/login";
         }
        @RequestMapping(value="/newtree",method=RequestMethod.GET,produces="application/json")
        public @ResponseBody List<JsTreeNode> TreeView() throws RuntimeException
        {
            
            return treeview;
             
        }
        
	// list page
	@RequestMapping(value = "/usersAdmin", method = RequestMethod.GET)
	public  String showAllUsers(Model model) {

                
           if (userService.getlogStatus()==0)
            {
                throw new DataAccessException("You're not logged!!!");
                //return "redirect:/login";
            }
            model.addAttribute("users", userService.findAll());
            return "/users/list";

	}

  
        @RequestMapping(value = "/users/insert", method = RequestMethod.POST)
	public String insertUser(@ModelAttribute("userForm") @Validated  User user,BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
                
            
            if (userService.getlogStatus()==0)
            {
                throw new DataAccessException("You're not logged!!!");
                //return "redirect:/login";
            }
            String akcija="insert";
                userService.executeProcUser(akcija,null, user.getFirstname(), user.getSecondname(), user.getUsername(), user.getPassword(), user.getIdAuth(), 1,0);
                return "redirect:/usersAdmin";
            
	}
        
        @RequestMapping(value = "/passwordChange", method = RequestMethod.POST)
	public String updatePassword(@ModelAttribute("passwordForm") @Validated  User user,BindingResult result, Model model) {
                
           if (userService.getlogStatus()==0)
            {
                throw new DataAccessException("You're not logged!!!");
                //return "redirect:/login";
            }
            String akcija="passwordChange";
                userService.executeProcUser(akcija,user.getIdUser(),null, null, null, user.getPassword(),null, null,null);
                return "redirect:/usersAdmin";
            
	}

        @RequestMapping(value = "/passwordChange", method = RequestMethod.GET)
	public  String getPassword(Model model,HttpServletRequest request) {

		//logger.debug("showUpdateUserForm() : {}", id);
            if (userService.getlogStatus()==0)
            {
                throw new DataAccessException("You're not logged!!!");
                //return "redirect:/login";
            }
                User user = userService.findById(userService.getidUser());
		model.addAttribute("passwordForm", user);
		return "users/passwordform";
              
	}
	// show add user form
	@RequestMapping(value = "/newUser", method = RequestMethod.GET)
	public String showAddUserForm(Model model,HttpServletRequest request) {

		//logger.debug("showAddUserForm()");
             if (userService.getlogStatus()==0)
            {
                throw new DataAccessException("You're not logged!!!");
                //return "redirect:/login";
            }
		User user = new User();
		user.setFirstname(" ");
		user.setSecondname(" ");
		user.setUsername(" ");
                user.setPassword(" ");
		model.addAttribute("userForm", user);
		FormDate formdate = new FormDate();
		DatePicker datePicker = new DatePicker();
		datePicker.setYear(1234L);
		datePicker.setMonth(2345L);
		datePicker.setDay(3456L);
		formdate.setDatePicker(datePicker);
		request.setAttribute("formdate", formdate);
		populateDefaultModel(model);
                nodetext="users/userform";
		return nodetext;

	}

	// show update form
	@RequestMapping(value = "/users/{id}/update", method = RequestMethod.GET)
	public  String showUpdateUserForm(@PathVariable("id") int id, Model model,HttpServletRequest request) {

		
            if (userService.getlogStatus()==0)
            {
                throw new DataAccessException("You're not logged!!!");
                //return "redirect:/login";
            }
                //logger.debug("showUpdateUserForm() : {}", id);
                User user = userService.findById(id);
		model.addAttribute("userForm", user);
                request.setAttribute("authorization", userService.getAuthList());
		populateDefaultModel(model);
		return "users/userform";
              
	}
        @RequestMapping(value = "/beforeupdate", method = RequestMethod.GET,produces="application/json")
	public @ResponseBody String UserUpdateData() {
        
            return append;
        
        }
        
        @RequestMapping(value="/interauto",method=RequestMethod.GET)   //OVO JE DIREKTNO POZIVANJE JSP STRANICA
        public @ResponseBody List<ComboValues> FindComboValue() //ResponseBody kaze da je ovo kontent koji vracamo na stranicu i mi smo odgovorni za njegovo pojavljivanje
        {

             return userService.getAuthList();
        }
        
        @RequestMapping(value = "/afterupdate", method = RequestMethod.GET,produces="application/json")
	public @ResponseBody String UserAfterUpdate() {
        
            return append;
        
        }
        
        @RequestMapping(value = "/users/{upid}/update", method = RequestMethod.POST)
	public String UpdateUserForm(@PathVariable("upid") int upid, @ModelAttribute("userForm") @Validated User usr) {

            if (userService.getlogStatus()==0)
            {
                throw new DataAccessException("You're not logged!!!");
                //return "redirect:/login";
            }

                //logger.debug("UpdateUserForm() : {}", upid);
                String akcija="update";
                userService.executeProcUser(akcija,usr.getIdUser(), usr.getFirstname(), usr.getSecondname(), usr.getUsername(), usr.getPassword(), usr.getIdAuth(), 1,usr.getLogged());
                return "redirect:/users/" + usr.getIdUser();
	}
        
	// delete user
        //@ExceptionHandler(GlobalExceptionHandler.class)
	@RequestMapping(value = "/users/{delid}/delete", method = RequestMethod.POST)
	public String deleteUser(@PathVariable("delid") int delid) throws Exception
        {
               
            if (userService.getlogStatus()==0)
            {
                throw new DataAccessException("You're not logged!!!");
                //return "redirect:/login";
            }
                try
               {
                     String akcija="delete";
                     userService.executeProcUser(akcija,delid, null, null, null, null, null, null,null);
                     return "redirect:/usersAdmin";
               }
               catch(Exception exo)
               {
                   throw new CustomException(exo.getMessage());
               }

	}

	// show user
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public String showUser(@PathVariable("id") int id, Model model) 
        {
            if (userService.getlogStatus()==0)
            {
                throw new DataAccessException("You're not logged!!!");
                //return "redirect:/login";
            }
		//logger.debug("showUser() id: {}", id);
		User user = userService.findById(id);
		model.addAttribute("users", user);
	     
                return "users/show";

	}

	private void populateDefaultModel(Model model) {

		List<String> frameworksList = new ArrayList<>();
		frameworksList.add("Spring MVC");
		frameworksList.add("Struts 2");
		frameworksList.add("JSF 2");
		frameworksList.add("GWT");
		frameworksList.add("Play");
		frameworksList.add("Apache Wicket");
		model.addAttribute("frameworkList", frameworksList);

		Map<String, String> skill = new LinkedHashMap<>();
		skill.put("Hibernate", "Hibernate");
		skill.put("Spring", "Spring");
		skill.put("Struts", "Struts");
		skill.put("Groovy", "Groovy");
		skill.put("Grails", "Grails");
		model.addAttribute("javaSkillList", skill);

		List<Integer> numbers = new ArrayList<>();
		numbers.add(1);
		numbers.add(2);
		numbers.add(3);
		numbers.add(4);
		numbers.add(5);
		model.addAttribute("numberList", numbers);

		Map<String, String> country = new LinkedHashMap<>();
		country.put("US", "United Stated");
		country.put("CN", "China");
		country.put("SG", "Singapore");
		country.put("MY", "Malaysia");
		model.addAttribute("countryList", country);

	}

	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ModelAndView handleEmptyData(HttpServletRequest req, Exception ex) {

		//logger.debug("handleEmptyData()");
		//logger.error("Request: {}, error ", req.getRequestURL(), ex);

		ModelAndView model = new ModelAndView();
		model.setViewName("user/show");
		model.addObject("msg", "user not found");

		return model;

	}
        @ExceptionHandler(DatabaseException.class)
	public ModelAndView handleDatabaseException(DatabaseException ex) {

		ModelAndView model = new ModelAndView("/users/errorhandler");
		model.addObject("message", ex.getMessage());
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
        @ExceptionHandler(GlobalException.class)
	public ModelAndView handleGlobalException(GlobalException ex) {

		ModelAndView model = new ModelAndView("/loginerror");
		model.addObject("message", ex.getMessage());
		return model;

	}
}
