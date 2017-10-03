/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.pio.service;
//import com.projects.halkom.model.ComboValues;
import com.projects.pio.model.ClaimRacuni;
import com.projects.pio.model.PIO;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.OracleTypes;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
//import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.jdbc.core.namedparam.SqlParameterSource;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlOutParameter;
/*import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Required;*/

/*import com.projects.trade.model.User;
import com.projects.trade.model.LoginUser;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;*/
import com.projects.pio.model.JsTreeNode;
import com.projects.pio.model.ComboValues;
import com.projects.pio.model.Greske;
import com.projects.pio.model.Iznosi;
import com.projects.pio.model.StavkeKnjizenja;
import com.projects.pio.model.UplatePIO;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigDecimal;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Date;
//import oracle.sql.NUMBER;
//import java.util.ArrayList;
//import org.hibernate.jpa.criteria.predicate.IsEmptyPredicate;
//import org.springframework.util.StringUtils;

@Repository
public class DataServiceImpl implements  DataService {
    
        @Autowired
        private DataSource dataSource;
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
        Integer ret=0;
        
        List<PIO> rset=null;
        List<StavkeKnjizenja> stavke_knjizenja=null;
        List<UplatePIO> monitoring_stavke=null;

  
        BigDecimal ukupan_iznos = new BigDecimal("0000.00");
        BigDecimal iznos_filijala = new BigDecimal("0000.00");
        
        
        @Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) throws DataAccessException {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
        
      
         @Override
	public List<JsTreeNode> getTreeView()  throws RuntimeException
        {
                String PROC_NAME="SGMainDB.dbo.GetMenuGroups_Spring";
            
                SqlParameter usrid = new SqlParameter("UserID", Types.INTEGER);
                SqlOutParameter retval = new SqlOutParameter("RetVal", Types.INTEGER);
                SqlParameter[] paramArray = {usrid,retval};

                StoredProcedureCall spc=new StoredProcedureCall(dataSource, PROC_NAME, paramArray);
                Object [] param_values={2037};
                List<JsTreeNode> result = spc.get(param_values);
                
		return result;

	}
        
        @Override
        public List<PIO> FindPIObyFilijala(String p_filijalaOd)
        {
           List<PIO> result ;
           Map<String, Object> params = new HashMap<>();
           params.put("p_filijalaOd", p_filijalaOd);
//            if (p_PioType==1)
//            {
//                String sql = " SELECT FILIJALA,ID_PENZIJE,SIF_OBUSTAVE,JMBG,VRSTA_PRAVA,(TO_NUMBER(IZNOS_RATE )* 100) /10000 IZNOS_RATE,PARTIJA,IZNOS_DUGA,OSTATAK_DUGA,OSTALO,0 NOVA_RATA,0 NOVI_OSTATAK,0 PARTIJA_LOAN from knjizipio where filijala=:p_filijalaOd";
//		result = namedParameterJdbcTemplate.query(sql,params,new PIOMapper());
//             }
//            if (p_PioType==2)
//            {
                String sql = "SELECT RBR as id_knjiz,FILIJALA,ID_PENZIJE,SIF_OBUSTAVE,JMBG,0 as VRSTA_PRAVA,(TO_NUMBER(IZNOS )* 100) /10000 IZNOS_RATE,PARTIJA,IZNOS_DUGA,OSTATAK_DUGA,OPIS as OSTALO,0 NOVA_RATA,0 NOVI_OSTATAK,0 PARTIJA_LOAN,STATUS from knjizipio where filijala=:p_filijalaOd";
		result = namedParameterJdbcTemplate.query(sql,params,new PIOMapper());
//             }
		return result;
        }
        
        @Override
        public List<UplatePIO> UplatePIO()
        {
           List<UplatePIO> result ;
           Map<String, Object> params = new HashMap<>();
           Date date=new Date();
           DateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
           String datum=dateFormat.format(date);
           String dat="04may2016";
           params.put("p_datum", dat);
           String sql = "select ID,ACCOUNT racun,to_char(IDATE,'DD.MM.YYYY') datum,AMOUNT iznos,PARTYACCOUNT kontraracun,F50_1 ime,F70_3 pbo,substr(F72_1,1,30) opis,F70_1 sifra from payment@tms where idate =:p_datum and account in ('840000000000165222','840000000001065285') and F50_1 like '%RFPIO%' and F59_1 like '%SOCIETE GENERALE%'";
	   result = namedParameterJdbcTemplate.query(sql,params,new PIOUplate());
	   return result;
        }
        
        @Override
        public List<UplatePIO> UPIT_ZA_TABELU_KNJIZENJA(String p_datum,BigDecimal iznos_uplate)
        {
           List<UplatePIO> result ;
           Map<String, Object> params = new HashMap<>();
           params.put("p_datum", p_datum);
           params.put("iznos_uplate", iznos_uplate);
           String sql = "select ID,ACCOUNT racun,to_char(IDATE,'DD.MM.YYYY') datum,AMOUNT iznos,PARTYACCOUNT kontraracun,F50_1 ime,F70_3 pbo,substr(F72_1,1,30) opis,F70_1 sifra from payment@tms where idate =:p_datum and amount=:iznos_uplate and account in ('840000000000165222','840000000001065285') and F50_1 like '%RFPIO%' and F59_1 like '%SOCIETE GENERALE%'";
	   result = namedParameterJdbcTemplate.query(sql,params,new PIOUplate());
	   return result;
        }
        
        
        @Override
        public List<ComboValues> PIOFilijale()
        {
            List<ComboValues> result ;
            
            String sql = " SELECT  code id, city description from PIO_FILIJALE ";
	    result = namedParameterJdbcTemplate.query(sql,new ComboValueMapper());
           
	    return result;
        }
        
        
        @Override
        public List<Greske> AccountCheckTable()
        {
            List<Greske> result ;
            
            String sql = " SELECT rbr,racun,ime,(to_number(iznos )* 100) /10000 as iznos,jmbg,0 as stavka,(SELECT SUM((to_number(iznos )* 100) /10000) FROM tbl_greske) as suma,status,status_racuna,'' as id_penzije,'' sifra_obustave,'' as filijala FROM tbl_greske";
	    result = namedParameterJdbcTemplate.query(sql,new GreskeMapper());
           
	    return result;
        }
        
        @Override
        public List<ClaimRacuni> ClaimAccounts()
        {
            List<ClaimRacuni> result ;
            
            String sql = "select username as ime,filijala,id_penzije,jmbg,amount as iznos from tmp_stavke_knjizenja where status_racuna='CLAIM' ";
	    result = namedParameterJdbcTemplate.query(sql,new ClaimMapper());
           
	    return result;
        }
        
        @Override
        public List<PIO> allPIO(Integer tip)
        {
           List<PIO> result  =null;
            if (tip==1)
            {
                String sql = " SELECT 0 as id_knjiz,FILIJALA,ID_PENZIJE,SIF_OBUSTAVE,JMBG,VRSTA_PRAVA,(TO_NUMBER(IZNOS_RATE )* 100) /10000 IZNOS_RATE,PARTIJA,IZNOS_DUGA,OSTATAK_DUGA,OSTALO,0 NOVA_RATA,0 NOVI_OSTATAK,0 as PARTIJA_LOAN,'AKTIVAN' as STATUS from PIO_ADMIN_NOVA where rownum <101";
		result = namedParameterJdbcTemplate.query(sql,new PIOMapper());
             }
            if (tip==2)
            {
                String sql = "SELECT 0 as id_knjiz,FILIJALA,ID_PENZIJE,SIF_OBUSTAVE,JMBG,VRSTA_PRAVA,(TO_NUMBER(IZNOS_RATE )* 100) /10000 IZNOS_RATE,PARTIJA,IZNOS_DUGA,OSTATAK_DUGA,OSTALO,0 NOVA_RATA,0 NOVI_OSTATAK,0 as PARTIJA_LOAN,'AKTIVAN' as STATUS from PIO_ADMIN_NOVA_NS where rownum <101";
		result = namedParameterJdbcTemplate.query(sql,new PIOMapper());
             }
		return result;
        
        }
          
        @Override
        public BigDecimal IznosiPIO(Integer tip)
        {
            BigDecimal result;
            Iznosi res=null;
            Map<String, Object> params = new HashMap<>();
            if (tip==1)
            {
                String sql = "  select sum((to_number(IZNOS_RATE )* 100) /10000) as ukupan_iznos, 0 as iznos_filijale from PIO_ADMIN_NOVA_IMP";
		res =(Iznosi) namedParameterJdbcTemplate.queryForObject(sql,params,new IznosiMapper());
             }
            if (tip==2)
            {
                String sql = " select sum((to_number(IZNOS_RATE )* 100) /10000) as ukupan_iznos, 0 as iznos_filijale from PIO_ADMIN_NOVA_IMP_NS";
		res = (Iznosi) namedParameterJdbcTemplate.queryForObject(sql,params,new IznosiMapper());
             }
		
            
            result= new BigDecimal(res.getUkupan_iznos().toString());
            setUkupan_Iznos(result);
            return result;
        
        }
        
        @Override 
        public void SP_PIO(String p_akcija,String p_filijala,String p_id_penzije,String p_obustava,String p_partija,String p_opis,String p_jmbg, String p_iznos,Integer p_rbr,String p_status_racuna)
        {
        
                String PROC_NAME="SGS_PIO.SP_PIO";
                SqlParameter akcija = new SqlParameter("p_akcija", Types.VARCHAR);
                SqlParameter filijala = new SqlParameter("p_filijala", Types.VARCHAR);
                SqlParameter id_penzije = new SqlParameter("p_id_penzije", Types.VARCHAR);
                SqlParameter obustava = new SqlParameter("p_obustava", Types.VARCHAR);
                SqlParameter partija = new SqlParameter("p_partija", Types.VARCHAR);
                SqlParameter opis = new SqlParameter("p_opis", Types.VARCHAR);
                SqlParameter jmbg = new SqlParameter("p_jmbg", Types.VARCHAR);
                SqlParameter iznos = new SqlParameter("p_iznos", Types.VARCHAR);
                SqlParameter rbr = new SqlParameter("p_rbr", Types.VARCHAR);
                SqlParameter status_racuna = new SqlParameter("p_status_racuna", Types.VARCHAR);
                
                //SqlOutParameter retval = new SqlOutParameter("p_ret", Types.NUMERIC);
                
                
                SqlParameter[] paramArray = {akcija,filijala,id_penzije,obustava,partija,opis,jmbg,iznos,rbr,status_racuna};

                StoredProcedureCall spc=new StoredProcedureCall(dataSource, PROC_NAME, paramArray);
                Object [] param_values={p_akcija,p_filijala,p_id_penzije,p_obustava,p_partija,p_opis,p_jmbg,p_iznos,p_rbr,p_status_racuna};
                Map res=spc.executeProc(param_values);
                //setReturnValue(Integer.getInteger(res.get("p_ret").toString()));
        
        
        }
        @Override       
        public void PIO_EXPORT(String p_filijalaOd)
        {
               String PROC_NAME="SGS_PIO.PIO_EXPORT";

                SqlParameter filijala = new SqlParameter("p_filijalaOd", Types.VARCHAR);
                //SqlOutParameter suma = new SqlOutParameter("p_suma", OracleTypes.CURSOR, new PIOMapper());
                //SqlOutParameter rst = new SqlOutParameter("rs", OracleTypes.CURSOR, new PIOMapper());
                
                SqlParameter[] paramArray = {filijala};

                StoredProcedureCall spc=new StoredProcedureCall(dataSource, PROC_NAME, paramArray);
                Object [] param_values={p_filijalaOd};
                Map res=spc.executeProc(param_values);
        }
        @Override
        public BigDecimal IznosiPoFilijalama(Integer tip,String p_filijala)
        {
             BigDecimal result;
             Iznosi res=null;
             Map<String, Object> params = new HashMap<>();
             params.put("p_filijala", p_filijala);
             
            if(tip==0)
            {
            
                String sql = "  select 0 as ukupan_iznos, sum((to_number(IZNOS )* 100) /10000) as iznos_filijale from knjizipio where filijala=:p_filijala";
		res = (Iznosi)namedParameterJdbcTemplate.queryForObject(sql,params,new IznosiMapper());
            }
            if (tip==1)
            {
                String sql = "  select 0 as ukupan_iznos, sum((to_number(IZNOS_RATE )* 100) /10000) as iznos_filijale from PIO_ADMIN_NOVA_IMP where filijala=:p_filijala";
		res = (Iznosi)namedParameterJdbcTemplate.queryForObject(sql,params,new IznosiMapper());
             }
            if (tip==2)
            {
                String sql = " select 0 as ukupan_iznos, sum((to_number(IZNOS_RATE )* 100) /10000) as iznos_filijale from PIO_ADMIN_NOVA_IMP_NS where filijala=:p_filijala";
		res = (Iznosi) namedParameterJdbcTemplate.queryForObject(sql,params,new IznosiMapper());
             }
            
            result= new BigDecimal(res.getIznos_filijala().toString());
            setIznos_Filijala(result);
	    return result;
        
        }

        @Override
	public List<PIO> ParsePIOImport(Integer p_PioType, Integer pFileType)
        {

                String PROC_NAME="SGS_PIO.sp_ParsePIOImport";

                SqlParameter tip = new SqlParameter("p_PioType", Types.INTEGER);
                SqlParameter filetip = new SqlParameter("p_FileType", Types.INTEGER);
                //SqlOutParameter suma = new SqlOutParameter("p_suma", OracleTypes.CURSOR, new PIOMapper());
                SqlOutParameter rst = new SqlOutParameter("rs", OracleTypes.CURSOR, new PIOMapper());
                
                SqlParameter[] paramArray = {tip,filetip,rst};

                StoredProcedureCall spc=new StoredProcedureCall(dataSource, PROC_NAME, paramArray);
                Object [] param_values={p_PioType,pFileType};
                Map res=spc.executeProc(param_values);
                List<PIO> result = (List<PIO>) res.get("rs");
                //setUkupan_Iznos(Integer.getInteger(res.get("p_suma").toString()));
                return result;

	}

         @Override
	public void ParsePIOExport(String p_datum,Integer p_PioType)
        {

                String PROC_NAME="";
                if (p_PioType==1)
                {
                    PROC_NAME="SGS_PIO.SP_PIOBG_Izvoz";
                }
                if (p_PioType==2)
                {
                    PROC_NAME="SGS_PIO.SP_PIONS_Izvoz";
                }
                SqlParameter datum = new SqlParameter("p_datum", Types.VARCHAR);
                //SqlOutParameter retval = new SqlOutParameter("p_ret", Types.NUMERIC);
                
                
                SqlParameter[] paramArray = {datum};

                StoredProcedureCall spc=new StoredProcedureCall(dataSource, PROC_NAME, paramArray);
                Object [] param_values={p_datum};
                Map res=spc.executeProc(param_values);
                //setReturnValue(Integer.getInteger(res.get("p_ret").toString()));

	}
        
        @Override
	public List<Greske> AccountCheck(String p_akcija)
        {

                String PROC_NAME="SGS_PIO.SP_FILE";
                SqlParameter akcija = new SqlParameter("p_akcija", Types.VARCHAR);
                SqlParameter reference = new SqlParameter("p_reference", Types.VARCHAR);
                SqlOutParameter rs = new SqlOutParameter("ispravni_rs",OracleTypes.CURSOR, new PIOMapper());
                SqlOutParameter greske = new SqlOutParameter("greske_rs",OracleTypes.CURSOR, new GreskeMapper());
                
                
                SqlParameter[] paramArray = {akcija,reference,greske,rs};

                StoredProcedureCall spc=new StoredProcedureCall(dataSource, PROC_NAME, paramArray);
                Object [] param_values={p_akcija,null,null};
                Map res=spc.executeProc(param_values);
                List<Greske> result = (List<Greske>) res.get("greske_rs");
                List<PIO> ispravni=(List<PIO>) res.get("ispravni_rs");
               
                setRset(ispravni);
                
                return result;

	}
        @Override
        public List<ComboValues> PIO_FAJL_ZA_KNJIZENJE(String p_filijalaOd,Integer p_PioType)
        {
        
                String PROC_NAME="";
                if (p_PioType==1)
                {
                    PROC_NAME="SGS_PIO.PIO_FAJL_ZA_KNJIZENJE_BG";
                }
                if (p_PioType==2)
                {
                    PROC_NAME="SGS_PIO.PIO_FAJL_ZA_KNJIZENJE_NS";
                }
                SqlParameter filijala = new SqlParameter("p_filijalaOd", Types.VARCHAR);
                SqlOutParameter retval = new SqlOutParameter("odgovor_rs",OracleTypes.CURSOR, new ComboValueMapper());
                
                
                SqlParameter[] paramArray = {filijala,retval};

                StoredProcedureCall spc=new StoredProcedureCall(dataSource, PROC_NAME, paramArray);
                Object [] param_values={p_filijalaOd};
                Map res=spc.executeProc(param_values);
                List<ComboValues> result = (List<ComboValues>) res.get("odgovor_rs");
                //setReturnValue(Integer.getInteger(res.get("p_ret").toString()));
                return result;
        }

        
         @Override
	public PIO PIODetails(Integer id_knjiz)
        {

               PIO result ;
               Map<String, Object> params = new HashMap<>();
               params.put("id_knjiz", id_knjiz);

                String sql = "SELECT RBR as id_knjiz,FILIJALA,ID_PENZIJE,SIF_OBUSTAVE,JMBG,0 as VRSTA_PRAVA,IZNOS IZNOS_RATE,PARTIJA,IZNOS_DUGA,OSTATAK_DUGA,OPIS as OSTALO,0 NOVA_RATA,0 NOVI_OSTATAK,0 PARTIJA_LOAN,STATUS from knjizipio where RBR=:id_knjiz";
		result = (PIO) namedParameterJdbcTemplate.queryForObject(sql,params,new PIOMapper());
		return result;

	}
        
        @Override
	public List<Iznosi> StavkeKnjizenja(String p_akcija,Integer fil,Integer p_idk,String p_datum)
        {

               List<Iznosi> result ;
               if ("".equals(p_datum) || p_datum==null)
               {
                    Date date=new Date();
                    DateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
                    String datum=dateFormat.format(date);
                    p_datum=datum;
                }
              
                String PROC_NAME="SGS_PIO.SP_STAVKE_KNJIZENJA";

                SqlParameter akcija = new SqlParameter("p_akcija", Types.VARCHAR);
                SqlParameter filijala = new SqlParameter("p_filijala", Types.INTEGER);
                SqlParameter datum = new SqlParameter("p_datum_unosa", Types.VARCHAR);
                SqlParameter idk = new SqlParameter("p_idk", Types.INTEGER);

                SqlOutParameter rst = new SqlOutParameter("iznosi_rs", OracleTypes.CURSOR, new IznosiStavkeMapper());
                
                SqlParameter[] paramArray = {akcija,filijala,datum,idk,rst};

                StoredProcedureCall spc=new StoredProcedureCall(dataSource, PROC_NAME, paramArray);
                Object [] param_values={p_akcija,fil,p_datum,p_idk};
                Map res=spc.executeProc(param_values);
                result = (List<Iznosi>) res.get("iznosi_rs");
		return result;

	}
        
        @Override
	public List<Iznosi> Monitoring(String p_akcija,Integer fil,String p_datum,String p_idk)
        {

               List<Iznosi> result ;
               if ("".equals(p_datum) || p_datum==null)
               {
                    Date date=new Date();
                    DateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
                    String datum=dateFormat.format(date);
                    p_datum=datum;
                }
              
                String PROC_NAME="SGS_PIO.SP_MONITORING";

                SqlParameter akcija = new SqlParameter("p_akcija", Types.VARCHAR);
                SqlParameter filijala = new SqlParameter("p_filijala", Types.INTEGER);
                SqlParameter datum = new SqlParameter("p_datum_unosa", Types.VARCHAR);
                SqlParameter idk = new SqlParameter("p_idk", Types.VARCHAR);

                SqlOutParameter rst = new SqlOutParameter("monitor_rs", OracleTypes.CURSOR, new IznosiStavkeMapper());
                SqlOutParameter stavkers = new SqlOutParameter("stavke_rs",OracleTypes.CURSOR, new StavkeMapper());
                SqlOutParameter headers = new SqlOutParameter("header_rs", OracleTypes.CURSOR, new PIOUplate());
                
                SqlParameter[] paramArray = {akcija,filijala,datum,idk,rst,stavkers,headers};

                StoredProcedureCall spc=new StoredProcedureCall(dataSource, PROC_NAME, paramArray);
                Object [] param_values={p_akcija,fil,p_datum,p_idk};
                Map res=spc.executeProc(param_values);
                result = (List<Iznosi>) res.get("monitor_rs");
                setStavkeKnjizenja((List<StavkeKnjizenja>)(res.get("stavke_rs")));
                setMonitoring((List<UplatePIO>)(res.get("header_rs")));
		return result;

	}
        
        
        
        @Override
	public Greske PIOErrorDetails(Integer id_knjiz)
        {

               Greske result ;
               Map<String, Object> params = new HashMap<>();
               params.put("id_knjiz", id_knjiz);

                String sql = "select k.rbr ,k.filijala,k.id_penzije,k.sif_obustave as sifra_obustave,k.jmbg,g.iznos,g.racun ,g.ime,k.status,case when g.status_racuna='UGASEN' then 2 when g.status_racuna='NE POSTOJI' then 0 when g.status_racuna='AKTIVAN' then 1 end as status_racuna,0 as stavka,0 as suma from knjizipio k inner join tbl_greske g on k.rbr=g.rbr where g.rbr=:id_knjiz";
		result = (Greske) namedParameterJdbcTemplate.queryForObject(sql,params,new GreskeMapper());
		return result;

	}
        
        
        @Override
	public List<UplatePIO> PodacioUplatama(String p_akcija,String p_datum,String p_iznos_uplate)
        {

                String PROC_NAME="SP_PODACI_O_UPLATAMA";
                
                if ("".equals(p_datum) || p_datum==null)
                {
                    Date date=new Date();
                    DateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
                    String datum=dateFormat.format(date);
                    p_datum=datum;
                }
                SqlParameter akcija = new SqlParameter("p_akcija", Types.VARCHAR);
                SqlParameter datum = new SqlParameter("p_datum", Types.VARCHAR);
                SqlParameter iznos_uplate = new SqlParameter("p_iznos_uplate", Types.VARCHAR);
                SqlOutParameter retval = new SqlOutParameter("uplate_rs",OracleTypes.CURSOR, new PIOUplate());
                
                
                SqlParameter[] paramArray = {akcija,datum,iznos_uplate,retval};

                StoredProcedureCall spc=new StoredProcedureCall(dataSource, PROC_NAME, paramArray);
                Object [] param_values={p_akcija,p_datum,p_iznos_uplate};
                Map res=spc.executeProc(param_values);
                List<UplatePIO> result = (List<UplatePIO>) res.get("uplate_rs");
                return result;

	}
        
        @Override
	public List<ComboValues> Posalji_Na_Knjizenje(String p_akcija,Integer p_idk,Integer p_tms_id,String p_iznos_uplate,String p_datum_unosa,Integer fil)
        {

                String PROC_NAME="SGS_PIO.SP_STAVKE_SPISKA";

                SqlParameter akcija = new SqlParameter("p_akcija", Types.VARCHAR);
                SqlParameter idk = new SqlParameter("p_id_knjizenja", Types.INTEGER);
                SqlParameter  tms_id= new SqlParameter("p_niz", Types.VARCHAR);
                SqlParameter  account= new SqlParameter("p_account", Types.VARCHAR);
                SqlParameter iznos_uplate = new SqlParameter("p_amount", Types.VARCHAR);
                SqlParameter username = new SqlParameter("p_username", Types.VARCHAR);
                SqlParameter referenca = new SqlParameter("p_referenca", Types.INTEGER);
                SqlParameter stavka = new SqlParameter("p_stavka", Types.INTEGER);
                SqlParameter datum_unosa = new SqlParameter("p_datum_unosa", Types.VARCHAR);
                SqlOutParameter retval = new SqlOutParameter("odgovor_rs",OracleTypes.CURSOR, new ComboValueMapper());
                SqlOutParameter stavkers = new SqlOutParameter("stavke_rs",OracleTypes.CURSOR, new StavkeMapper());
                
                
                SqlParameter[] paramArray = {akcija,idk,tms_id,account,iznos_uplate,username,referenca,stavka,datum_unosa,retval,stavkers};

                StoredProcedureCall spc=new StoredProcedureCall(dataSource, PROC_NAME, paramArray);
                Object [] param_values={p_akcija,p_idk,p_tms_id,null,p_iznos_uplate,null,null,fil,p_datum_unosa};
                Map res=spc.executeProc(param_values);
                List<ComboValues> result = (List<ComboValues>) res.get("odgovor_rs");
                setStavkeKnjizenja((List<StavkeKnjizenja>)(res.get("stavke_rs")));
                return result;

	}
        
        @Override
        public List<ComboValues>  SendToTMS()
        {
           String PROC_NAME="SGS_PIO.SP_PROCESS";
              
           SqlOutParameter retval = new SqlOutParameter("odgovor_rs",OracleTypes.CURSOR, new ComboValueMapper());
           SqlParameter[] paramArray = {retval};
           StoredProcedureCall spc=new StoredProcedureCall(dataSource, PROC_NAME, paramArray);
           Object [] param_values={null};
           Map res=spc.executeProc(param_values);
           List<ComboValues> result = (List<ComboValues>) res.get("odgovor_rs");
           
           return result;
        
        }
        
          private static final class ComboValueMapper implements RowMapper<ComboValues> {
                @Override
		public ComboValues mapRow(ResultSet rs, int rowNum) throws SQLException {
			ComboValues cmb = new ComboValues(Integer.parseInt(rs.getString("id")),rs.getString("description"));
			return cmb;
		}
	}
          
        
          
          private static final class IznosiMapper implements RowMapper<Iznosi> {
                @Override
		public Iznosi mapRow(ResultSet rs, int rowNum) throws SQLException {
			Iznosi cmb = new Iznosi(rs.getBigDecimal("ukupan_iznos"),rs.getBigDecimal("iznos_filijale"));
			return cmb;
		}
	}
        
         private static final class IznosiStavkeMapper implements RowMapper<Iznosi> {
                @Override
		public Iznosi mapRow(ResultSet rs, int rowNum) throws SQLException {
			Iznosi cmb = new Iznosi(rs.getBigDecimal("ukupno"),rs.getBigDecimal("claim"));
                        cmb.setIznos_stavke(rs.getBigDecimal("stavke"));
                        cmb.setSum_ukupno(rs.getBigDecimal("sumaukupno"));
			return cmb;
		}
	}
       
        private static final class PIOMapper implements RowMapper<PIO> 
        {

                @Override
		public PIO mapRow(ResultSet rs, int rowNum) throws SQLException 
                {
			PIO pio = new PIO();
                        pio.setId(rs.getInt("id_knjiz"));
			pio.setFilijala(rs.getString("Filijala"));
                        pio.setId_penzije(rs.getString("ID_PENZIJE"));
                        pio.setSif_obustave(rs.getString("SIF_OBUSTAVE"));
                        pio.setJmbg(rs.getString("JMBG"));
                        pio.setVrsta_prava(rs.getString("VRSTA_PRAVA"));
                        pio.setIznos_rate(rs.getString("IZNOS_RATE"));
			pio.setPartija(rs.getString("PARTIJA"));
			pio.setIznos_duga(rs.getString("IZNOS_DUGA"));
                        pio.setOstatak_duga(rs.getString("OSTATAK_DUGA"));
                        pio.setOstalo(rs.getString("OSTALO"));
                        pio.setNova_rata(rs.getString("NOVA_RATA"));
                        pio.setNovi_ostatak(rs.getString("NOVI_OSTATAK"));
                        pio.setPartija_loan(rs.getString("PARTIJA_LOAN"));
                        pio.setStatus(rs.getString("STATUS"));
			return pio;
		}
	}
        
          private static final class PIOUplate implements RowMapper<UplatePIO> 
        {

                @Override
		public UplatePIO mapRow(ResultSet rs, int rowNum) throws SQLException 
                {
			UplatePIO pio = new UplatePIO();
                        pio.setID(rs.getInt("id"));
			pio.setDatum(rs.getString("datum"));
                        pio.setIme(rs.getString("ime"));
                        pio.setIznos(rs.getBigDecimal("iznos"));
                        pio.setKontraracun(rs.getString("kontraracun"));
                        pio.setOpis(rs.getString("opis"));
                        pio.setPbo(rs.getString("pbo"));
			pio.setRacun(rs.getString("racun"));
			pio.setSifra(rs.getString("sifra"));
                        pio.setIdk(rs.getInt("idk"));
                        
			return pio;
		}
	}
        
        
          private static final class GreskeMapper implements RowMapper<Greske> 
        {

                @Override
		public Greske mapRow(ResultSet rs, int rowNum) throws SQLException 
                {
			Greske greske = new Greske();
                        greske.setIme(rs.getString("ime"));
                        greske.setRacun(rs.getString("racun"));
			greske.setIznos(rs.getString("iznos"));
                        greske.setJmbg(rs.getString("jmbg"));
                        greske.setStavka(rs.getInt("stavka"));
                        greske.setSuma(rs.getDouble("suma"));
                        greske.setRbr(rs.getString("rbr"));
                        greske.setStatus(rs.getString("status"));
                        greske.setStatus_racuna(rs.getString("status_racuna"));
                        greske.setId_penzije(rs.getString("id_penzije"));
                        greske.setSifra_obustave(rs.getString("sifra_obustave"));
                        greske.setFilijala(rs.getString("filijala"));
			return greske;
		}
	}
          
        private static final class ClaimMapper implements RowMapper<ClaimRacuni> 
        {

                @Override
		public ClaimRacuni mapRow(ResultSet rs, int rowNum) throws SQLException 
                {
			ClaimRacuni claim = new ClaimRacuni();
                        claim.setIme(rs.getString("ime"));
			claim.setIznos(rs.getString("iznos"));
                        claim.setJmbg(rs.getString("jmbg"));
                        claim.setId_penzije(rs.getString("id_penzije"));
                        claim.setFilijala(rs.getString("filijala"));
			return claim;
		}
	}
        
           private static final class StavkeMapper implements RowMapper<StavkeKnjizenja> 
        {

                @Override
		public StavkeKnjizenja mapRow(ResultSet rs, int rowNum) throws SQLException 
                {
			StavkeKnjizenja stavke = new StavkeKnjizenja();
                        stavke.setCredit_account(rs.getString("credit_account"));
			stavke.setDatum_obrade(rs.getString("datum_obrade"));
                        stavke.setDatum_valute(rs.getString("datum_valute"));
                        stavke.setDebit_account(rs.getString("debit_account"));
                        stavke.setIdk(rs.getString("idk"));
                        stavke.setIme(rs.getString("ime"));
                        stavke.setIznos(rs.getString("iznos"));
			stavke.setStatus(rs.getString("status"));
			stavke.setTms_id(rs.getString("tms_id"));
                        
                        
			return stavke;
		}
	}
           
        
        @Override
        @DataServiceImpl.Property(key="property.key", defaultValue="default")
        public List<UplatePIO> getMonitoring()
        {
        return monitoring_stavke;
        }
        
        @Override
        @DataServiceImpl.Property(key="property.key", defaultValue="default")
        public void setMonitoring(List<UplatePIO> rset) {
            this.monitoring_stavke = rset;
        }
            
           
        @Override
        @DataServiceImpl.Property(key="property.key", defaultValue="default")
        public List<StavkeKnjizenja> getStavkeKnjizenja()
        {
        return stavke_knjizenja;
        }
        
        @Override
        @DataServiceImpl.Property(key="property.key", defaultValue="default")
        public void setStavkeKnjizenja(List<StavkeKnjizenja> rset) {
            this.stavke_knjizenja = rset;
        }
        
        @Override
        @DataServiceImpl.Property(key="property.key", defaultValue="default")
        public List<PIO> getRset()
        {
        return rset;
        }
        
        @Override
        @DataServiceImpl.Property(key="property.key", defaultValue="default")
        public void setRset(List<PIO> rset) {
            this.rset = rset;
        }
          
          
        @Override
        @DataServiceImpl.Property(key="property.key", defaultValue="default")
        public Integer getReturnValue() 
        {
            return ret;
        }
        @Override
        @DataServiceImpl.Property(key="property.key", defaultValue="default")
        public void setReturnValue(Integer lg) 
        {
           ret=lg;
        }
        
        @Override
        @DataServiceImpl.Property(key="property.key", defaultValue="default")
        public BigDecimal getUkupan_Iznos() 
        {
            return ukupan_iznos;
        }
        @Override
        @DataServiceImpl.Property(key="property.key", defaultValue="default")
        public void setUkupan_Iznos(BigDecimal lg) 
        {
           ukupan_iznos=lg;
        }
        
        
        @Override
        @DataServiceImpl.Property(key="property.key", defaultValue="default")
        public BigDecimal getIznos_Filijala() 
        {
            return iznos_filijala;
        }
        @Override
        @DataServiceImpl.Property(key="property.key", defaultValue="default")
        public void setIznos_Filijala(BigDecimal lg) 
        {
           iznos_filijala=lg;
        }
        
        @Retention(RetentionPolicy.RUNTIME) 
        @Target({ElementType.METHOD, ElementType.FIELD})
        public @interface Property {
            String key();
            String defaultValue() default "";
        }
        
}
