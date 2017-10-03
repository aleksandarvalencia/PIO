package com.projects.pio.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import com.projects.pio.exceptions.DataAccessException;
import com.projects.pio.model.Attributes;
import com.projects.pio.model.ComboValues;
//import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.jdbc.core.namedparam.SqlParameterSource;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Required;

import com.projects.pio.model.User;
import com.projects.pio.model.LoginUser;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import com.projects.pio.model.JsTreeNode;
import com.projects.pio.model.TData;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//import com.projects.halkom.model.Attributes;
//import com.projects.halkom.model.TData;
import java.sql.Types;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
//import org.springframework.jdbc.object.StoredProcedure;

@Repository
public class UserDaoImpl implements UserDao {

        @Autowired
        private DataSource dataSource;
        private Integer idAuth;
        private Integer logStatus=0;
        private Integer idUser;
        SimpleJdbcCall SimpleCall;
        SimpleJdbcCall SimpleCallInsert;
        SimpleJdbcCall SimpleCallDelete;
        
        @Autowired  
        private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
        
        
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate)
        {   this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;  } 
        
        /*@Autowired
	public void setSimpleJdbcCall(SimpleJdbcCall simpleJdbcCall) throws DataAccessException {
		this.SimpleCall = simpleJdbcCall;
                this.SimpleCallInsert = simpleJdbcCall;
	}*/
        @Autowired
        @Required
        public void setDataSource(DataSource dataSource) {
            SimpleCallInsert=new SimpleJdbcCall(dataSource);
            SimpleCall=new SimpleJdbcCall(dataSource);
            SimpleCallDelete=new SimpleJdbcCall(dataSource);
        }

	@Override
	public User findById(Integer id) {

		Map<String, Object> params = new HashMap<>();
		params.put("id", id);

		String sql = " select idUser,firstname,secondname,username,to_char(Password) Password,idAuth,status,logged from tbl_Users where idUser=:id";

		User result = null;
		try {
			result = namedParameterJdbcTemplate.queryForObject(sql, params, new UserMapper());
		} catch (EmptyResultDataAccessException e) {
			// do nothing, return null
		}

		return result;

	}
 
	@Override
	public List<User> findAll() {

		//String sql = "select UserID,UserName,DisplayName,DeltaUser,convert(varchar(10),PasswordChanged,120) PasswordChanged,convert(varchar(10),LogOnAttempt ,120) LogOnAttempt,convert(nvarchar(1000),[Password]) Password from SGMainDB.dbo.Users";
                String sql = " select idUser,firstname,secondname,username,to_char(Password) Password,idAuth,status,logged from tbl_Users order by idUser";
		List<User> result = namedParameterJdbcTemplate.query(sql, new UserMapper());

		return result;

	}
        
        @Override
	public List<JsTreeNode> getTreeView(Integer idAuth)  throws RuntimeException
        {
               String PROC_NAME="SGS_PIO.spTreeView";

                SqlParameter idauth = new SqlParameter("pidAuth", Types.INTEGER);
                SqlOutParameter rst = new SqlOutParameter("rs", OracleTypes.CURSOR, new TreeViewMapper());
                SqlParameter[] paramArray = {idauth,rst};

                StoredProcedureCall spc=new StoredProcedureCall(dataSource, PROC_NAME, paramArray);
                Object [] param_values={idAuth};
                Map res=spc.executeProc(param_values);
                List<JsTreeNode> result = (List<JsTreeNode>) res.get("rs");
                
                return result;

	}

        @Override
	public List<User> executeProcUser(String p_akcija, Integer p_idUser, String p_firstname,String p_secondname,
                String p_username,String p_password,Integer p_idAuth, Integer p_status,Integer p_logged)
        {

                String PROC_NAME="SGS_PIO.SP_USERS";
                ShaPasswordEncoder passwordEncoder = new ShaPasswordEncoder(512);
                String encodedPassword = passwordEncoder.encodePassword(p_password,null);
                
                SqlParameter akcija = new SqlParameter("p_akcija", Types.VARCHAR);
                SqlParameter iduser = new SqlParameter("p_idUser", Types.INTEGER);
                SqlParameter firstname = new SqlParameter("p_firstname", Types.VARCHAR);
                SqlParameter secondname = new SqlParameter("p_secondname", Types.VARCHAR);
                SqlParameter username = new SqlParameter("p_username", Types.VARCHAR);
                SqlParameter password = new SqlParameter("p_password", Types.CHAR);
                SqlParameter idAut = new SqlParameter("p_idAuth", Types.INTEGER);
                SqlParameter status = new SqlParameter("p_status", Types.INTEGER);
                SqlParameter logged = new SqlParameter("p_logged", Types.INTEGER);
                SqlOutParameter rst = new SqlOutParameter("p_table", OracleTypes.CURSOR, new UserMapper());
                
                SqlParameter[] paramArray = {akcija,iduser,firstname,secondname,username,password,idAut,status,logged,rst};

                StoredProcedureCall spc=new StoredProcedureCall(dataSource, PROC_NAME, paramArray);
                Object [] param_values={p_akcija,p_idUser,p_firstname,p_secondname,p_username,encodedPassword.toUpperCase(),p_idAuth,p_status,p_logged};
                Map res=spc.executeProc(param_values);
                List<User> result = (List<User>) res.get("p_table");
                
                return result;
              
	}
        @Override
	public String DisplayNameByUserName(String username,String password) {


                ShaPasswordEncoder passwordEncoder = new ShaPasswordEncoder(512);
                String encodedPassword = passwordEncoder.encodePassword(password,null);

                Map<String, Object> params = new HashMap<>();
		params.put("username", username);
                params.put("password", encodedPassword.toUpperCase());
                
		String sql = "select idUser userid,username,firstname as displayname,idAuth,logged from tbl_Users where username=:username and password=:password";

		LoginUser result = null;
		try {
			 result = (LoginUser) namedParameterJdbcTemplate.queryForObject(sql, params, new LoginMapper());
		} catch (Exception e) 
                {
			throw new DataAccessException("Wrong user or password!!!");
                        
		}
                setidAuth(result.getIdAuth());
                setidUser(result.getId());
                return result.getDisplayname();
	}
        
        @Override
	public LoginUser UserLogin(String username, String password ) {

		Map<String, Object> params = new HashMap<>();
		params.put("username", username);
                //params.put("password", password);

		String sql = "select idUser userid,username,firstname as displayname,idAuth,logged from tbl_Users where username=:username";

		LoginUser result = null;
		try {
			result = namedParameterJdbcTemplate.queryForObject(sql, params, new LoginMapper());
		} catch (EmptyResultDataAccessException e) {
			// do nothing, return null
		}

		return result;

	}
        
        @Override
	public Integer DisplayLogStatusByUserName(String username,String password) {


                ShaPasswordEncoder passwordEncoder = new ShaPasswordEncoder(512);
                String encodedPassword = passwordEncoder.encodePassword(password,null);

                Map<String, Object> params = new HashMap<>();
		params.put("username", username);
                params.put("password", encodedPassword.toUpperCase());
                
		String sql = "select idUser userid,username,firstname as displayname,idAuth,logged from tbl_Users where username=:username and password=:password";

		LoginUser result = null;
		try {
			 result = (LoginUser) namedParameterJdbcTemplate.queryForObject(sql, params, new LoginMapper());
		} catch (Exception e) 
                {
			throw new DataAccessException("Wrong user or password!!!");
                        
		}
                //setidAuth(result.getIdAuth());
                setlogStatus(result.getLogged());
                return result.getLogged();
	}
        
        @Override
        public List<ComboValues>  getAuthList()  throws RuntimeException
        {
             String PROC_NAME="SGS_PIO.sp_Auth";
             String p_akcija="select";
             
                SqlParameter akcija = new SqlParameter("p_akcija", Types.VARCHAR);
                SqlOutParameter rst = new SqlOutParameter("rs", OracleTypes.CURSOR, new ComboValueMapper());
                
                SqlParameter[] paramArray = {akcija,rst};

                StoredProcedureCall spc=new StoredProcedureCall(dataSource, PROC_NAME, paramArray);
                Object [] param_values={p_akcija};
                Map res=spc.executeProc(param_values);
                List<ComboValues> result = (List<ComboValues>) res.get("rs");
                
                return result;
            
        
        }

           private static final class ComboValueMapper implements RowMapper<ComboValues> {
                @Override
		public ComboValues mapRow(ResultSet rs, int rowNum) throws SQLException {
			ComboValues cmb = new ComboValues(Integer.parseInt(rs.getString("id")),rs.getString("description"));
			return cmb;
		}
	}
        
          private static final class TreeViewMapper implements RowMapper<JsTreeNode> {
                @Override
		public JsTreeNode mapRow(ResultSet rs, int rowNum) throws SQLException {
			JsTreeNode jst = new JsTreeNode();
                        Attributes att = new Attributes();
                        TData tdata=new TData();
                        att.setText(rs.getString("ACTION_DATA"));
                        jst.setId(rs.getString("ITEMLEVEL"));
                        jst.setText(rs.getString("ITEMTITLE"));
                        jst.setIdAuth(rs.getString("ITEMLEVEL"));
                        jst.setIdParent(rs.getString("ITEMLEVEL"));
                        jst.setData(att);
                        tdata.setNodeid(rs.getString("TV_DATA"));
                        tdata.setNodename(rs.getString("TITLE_DATA"));
                        tdata.setHref(rs.getString("ACTION_DATA"));
                        jst.setMetadata(tdata);
			return jst;
		}
	}
	private static final class UserMapper implements RowMapper<User> {

                @Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setIdUser(rs.getInt("idUser"));
                        user.setId_Userold(rs.getInt("idUser"));
			user.setUsername(rs.getString("username"));
                        user.setFirstname(rs.getString("firstname"));
                        user.setSecondname(rs.getString("secondname"));
                        user.setIdAuth(rs.getInt("idAuth"));
                        user.setStatus(rs.getInt("status"));
                        user.setLogged(rs.getInt("logged"));
			//user.setPasswordchanged((java.sql.Date)rs.getDate(5));
			user.setPassword(rs.getString("password"));
			//user.setLogonattempt((java.sql.Date)rs.getDate("logonattempt"));

			return user;
		}
	}
        private static final class LoginMapper implements RowMapper<LoginUser> {

                @Override
		public LoginUser mapRow(ResultSet rs, int rowNum) throws SQLException {
			LoginUser log = new LoginUser();
                        log.setId(rs.getInt("userid"));
                        log.setIdAuth(rs.getInt("idAuth"));
			log.setUsername(rs.getString("username"));
                        log.setDisplayname(rs.getString("displayname"));
                        log.setLogged(rs.getInt("logged"));
			return log;
		}
	}
       
	private static List<String> convertDelimitedStringToList(String delimitedString) {

		List<String> result = new ArrayList<>();

		if (!StringUtils.isEmpty(delimitedString)) {
			result = Arrays.asList(StringUtils.delimitedListToStringArray(delimitedString, ","));
		}
		return result;

	}

	private String convertListToDelimitedString(List<String> list) {

		String result = "";
		if (list != null) {
			result = StringUtils.arrayToCommaDelimitedString(list.toArray());
		}
		return result;

	}
        @Override
        @Property(key="property.key", defaultValue="default")
        public void setidUser(Integer idUser) {
            this.idUser = idUser;
        }
        @Override
        @Property(key="property.key", defaultValue="default")
        public Integer getidUser() 
        {
            return idUser ;
        }
        
        @Override
        @Property(key="property.key", defaultValue="default")
        public void setidAuth(Integer idAuth) {
            this.idAuth = idAuth;
        }
        
        @Override
        @Property(key="property.key", defaultValue="default")
        public Integer getidAuth() 
        {
            return idAuth ;
        }
        
       
        @Override
        @Property(key="property.key", defaultValue="default")
        public void setlogStatus(Integer log) {
            this.logStatus = log;
        }
        
        @Override
        @Property(key="property.key", defaultValue="default")
        public Integer getlogStatus() 
        {
            return logStatus ;
        }
        
        @Retention(RetentionPolicy.RUNTIME) 
        @Target({ElementType.METHOD, ElementType.FIELD})
        public @interface Property {
            String key();
            String defaultValue() default "";
        }
        
        
	/*@Override
	public void save(User user) {

		//KeyHolder keyHolder = new GeneratedKeyHolder();
		//SimpleCall.withProcedureName("SP_ADD_USER");
                
		String sql = "INSERT INTO SGMainDB.dbo.Users(USERNAME, DISPLAYNAME, DELTAUSER,PASSWORDCHANGED,LOGONATTEMPT ) "
				+ "VALUES ( :username, :displayname, :deltauser,:passwordchanged,:logonattempt )";

		namedParameterJdbcTemplate.update(sql, getSqlParameterByModel(user));
		//user.setId(keyHolder.getKey().intValue());
		
	}
        @Override
	public void insert(User user) {


            Integer userid;
            userid=user.getId();
	    if (userid==null || userid==0)
            {
              
                SimpleCallInsert.withCatalogName("SGMainDB");
                SimpleCallInsert.withSchemaName("dbo");
                SimpleCallInsert.withProcedureName("SP_ADD_User");
                MapSqlParameterSource inparams = new MapSqlParameterSource();
                inparams.addValue("FirstName", user.getDisplayname());
                inparams.addValue("LastName", user.getDisplayname());
                inparams.addValue("Status", 9);
                inparams.addValue("UserName", user.getUsername());
                inparams.addValue("Password", user.getPassword());
                inparams.addValue("DisplayName", user.getDisplayname());
                inparams.addValue("Priority", 5);
                inparams.addValue("DeltaUser", user.getDeltauser());
                inparams.addValue("UserID", 2037);       
                Map<String, Object> inout = SimpleCallInsert.execute(inparams);
                //user.setId(Integer.parseInt(String.valueOf(inout.get("IDUser"))));
                inout.clear();

                
                
            }
            else
            {
                update(user);
            }

            //user.setId((Integer)out.get("IDUser"));

	}*/
        

	/*@Override
	public void update(User user) {

		//String sql = "UPDATE SGMainDB.dbo.USERS SET DISPLAYNAME=:displayname, USERNAME=:username, DELTAUSER=:deltauser, PASSWORD=:password, PASSWORDCHANGED=:passwordchanged, LOGONATTEMPT=:logonattempt WHERE UserID=:id";

		//namedParameterJdbcTemplate.update(sql, getSqlParameterByModel(user));
            Integer userid;
            userid=user.getId();
            if (userid==null || userid==0)
            {
                insert(user);
            }
            else
            {
                SimpleCall.withCatalogName("SGMainDB");
                SimpleCall.withSchemaName("dbo");
                SimpleCall.withProcedureName("SP_Update_Users");
                MapSqlParameterSource upparams = new MapSqlParameterSource();
                upparams.addValue("idUser", user.getId());
                upparams.addValue("FirstName", user.getDisplayname());
                upparams.addValue("LastName", user.getDisplayname());
                upparams.addValue("Status", 9);
                upparams.addValue("UserName", user.getUsername());
                upparams.addValue("Password", user.getPassword());
                upparams.addValue("DisplayName", user.getDisplayname());
                upparams.addValue("Priority", 5);
                upparams.addValue("Suspended", 0);
                upparams.addValue("DeltaUser", user.getDeltauser());
                upparams.addValue("UserID", 2037);
                Map<String, Object> upout = SimpleCall.execute(upparams);
                upout.clear();
               
            }
            
	}*/

	/*@Override
	public void delete(Integer id) {

                SimpleCallDelete.withCatalogName("SGMainDB");
                SimpleCallDelete.withSchemaName("dbo");
                SimpleCallDelete.withProcedureName("SP_Delete_Users");
                MapSqlParameterSource delparams = new MapSqlParameterSource();
                //delparams.addValue("idUser",Double.parseDouble(String.valueOf(id)));
                delparams.addValue("idUser",id);
                delparams.addValue("UserID", 2037);
                Map<String, Object> delout = SimpleCallDelete.execute(delparams);
                delout.clear();

	}*/
        
       /* @Override
	public void delete(User user) {


                String sql = "DELETE FROM SGMainDb.dbo.USERS WHERE UserID=:id";
		namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
            
                SimpleCallDelete.withCatalogName("SGMainDB");
                SimpleCallDelete.withSchemaName("dbo");
                SimpleCallDelete.withProcedureName("SP_Delete_Users");
                MapSqlParameterSource delparams = new MapSqlParameterSource();
                //delparams.addValue("idUser",Double.parseDouble(String.valueOf(id)));
                delparams.addValue("idUser",user.getId());
                delparams.addValue("UserID", 2037);
                Map<String, Object> delout = SimpleCallDelete.execute(delparams);
                delout.clear();

	}
        private SqlParameterSource getSqlParameterByModel(User user) {

		// Unable to handle List<String> or Array
		// BeanPropertySqlParameterSource

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		//paramSource.addValue("id", user.getId());
		paramSource.addValue("username", user.getUsername());
		paramSource.addValue("displayname", user.getDisplayname());
		paramSource.addValue("deltauser", user.getDeltauser());
		//paramSource.addValue("password", user.getPassword());
		paramSource.addValue("passwordchanged", user.getPasswordchanged());
		paramSource.addValue("logonattempt", user.getLogonattempt());


		return paramSource;
	}*/

}