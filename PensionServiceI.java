/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.pio.service;

import com.projects.pio.model.ClaimRacuni;
import com.projects.pio.model.PENZIJE;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.OracleTypes;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import com.projects.pio.model.ComboValues;
import com.projects.pio.model.Iznosi;
import com.projects.pio.model.StavkeKnjizenja;
import com.projects.pio.model.UGASENI;
import com.projects.pio.model.UplatePIO;
import com.projects.pio.model.VRSTEFAJLOVA;
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


@Repository
public class PensionServiceI implements PensionService {
    
       @Autowired
        private DataSource dataSource;
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
        Integer ret=0;
        
        List<PENZIJE> rset=null;
        List<UGASENI> rsetugaseni=null;
        List<StavkeKnjizenja> stavke_knjizenja=null;
        List<UplatePIO> monitoring_stavke=null;

  
        BigDecimal ukupan_iznos = new BigDecimal("0000.00");
        BigDecimal iznos_filijala = new BigDecimal("0000.00");
        
        
        @Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) throws DataAccessException {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
        
        @Override
	public List<PENZIJE> AccountCheck(String p_akcija)
        {

                String PROC_NAME="SGS_PIO.SP_File_Penzije_Provera";
                SqlParameter akcija = new SqlParameter("p_akcija", Types.VARCHAR);
                SqlOutParameter rs = new SqlOutParameter("ispravni_rs",OracleTypes.CURSOR, new PENZIJEMapper());
                SqlOutParameter greske = new SqlOutParameter("greske_rs",OracleTypes.CURSOR, new PENZIJEMapper());
                SqlOutParameter ugasenirs = new SqlOutParameter("ugaseni_rs",OracleTypes.CURSOR, new PENZIJEMapper());
                
                SqlParameter[] paramArray = {akcija,rs,greske,ugasenirs};

                StoredProcedureCall spc=new StoredProcedureCall(dataSource, PROC_NAME, paramArray);
                Object [] param_values={p_akcija};
                Map res=spc.executeProc(param_values);
                List<PENZIJE> result = (List<PENZIJE>) res.get("greske_rs");
                List<PENZIJE> ispravni=(List<PENZIJE>) res.get("ispravni_rs");
                List<UGASENI> ugaseni=(List<UGASENI>) res.get("ugaseni_rs");
               
                setRset(ispravni);
                setRsetUgaseni(ugaseni);
                return result;

	}
       
        @Override
        public List<PENZIJE> AccountCheckTable()
        {
            List<PENZIJE> result ;
            
            String sql = " select tp.RBR as id_knjiz,tp.FILIJALA,tp.JMBG,(to_number(neto )* 100) /10000 IZNOS_RATE,tp.TR as RACUN,tp.IME,tpi.STATUS_RACUNA,tpi.STATUS,0 as ISPRAVNO  from tbl_penzije tp inner join tbl_penzije_ispravni tpi on tpi.rbr=tp.rbr where tpi.status='NEISPRAVAN' ";
	    result = namedParameterJdbcTemplate.query(sql,new GreskeMapper());
           
	    return result;
        }
        
        @Override
        public List<ClaimRacuni> ClaimAccounts()
        {
            List<ClaimRacuni> result ;
            
            String sql = "select IME,Filijala,mbrb as ID_Penzije,JMBG,(to_number(NETO )* 100) /10000 IZNOS, tr as PARTIJA from TBL_PENZIJE where status_racuna='UGASEN' ";
	    result = namedParameterJdbcTemplate.query(sql,new ClaimMapper());
           
	    return result;
        }
        
       
          
        @Override
        public BigDecimal IznosiPENZIJE(Integer tip)
        {
            BigDecimal result;
            Iznosi res=null;
            Map<String, Object> params = new HashMap<>();

            String sql = "  select sum((to_number(NETO )* 100) /10000) as ukupan_iznos, 0 as iznos_filijale from TBL_PENZIJE";
	    res =(Iznosi) namedParameterJdbcTemplate.queryForObject(sql,params,new IznosiMapper());

            result= new BigDecimal(res.getUkupan_iznos().toString());
            setUkupan_Iznos(result);
            return result;
        
        }
        
        @Override 
        public List<PENZIJE> SP_PENZIJE(String p_akcija,String p_filijala,String p_racun,String p_ime,String p_jmbg, String p_iznos,Integer p_rbr,String p_status_racuna)
        {
        
                String PROC_NAME="SGS_PIO.SP_PENZIJE";
                SqlParameter akcija = new SqlParameter("p_akcija", Types.VARCHAR);
                SqlParameter filijala = new SqlParameter("p_filijala", Types.VARCHAR);
                SqlParameter racun = new SqlParameter("p_racun", Types.VARCHAR);
                SqlParameter opis = new SqlParameter("p_ime", Types.VARCHAR);
                SqlParameter jmbg = new SqlParameter("p_jmbg", Types.VARCHAR);
                SqlParameter iznos = new SqlParameter("p_iznos", Types.VARCHAR);
                SqlParameter rbr = new SqlParameter("p_rbr", Types.VARCHAR);
                SqlParameter status_racuna = new SqlParameter("p_status_racuna", Types.VARCHAR);
                SqlOutParameter retval = new SqlOutParameter("rs_penzije", OracleTypes.CURSOR);
                
                
                SqlParameter[] paramArray = {akcija,filijala,racun,opis,jmbg,iznos,rbr,status_racuna,retval};

                StoredProcedureCall spc=new StoredProcedureCall(dataSource, PROC_NAME, paramArray);
                Object [] param_values={p_akcija,p_filijala,p_racun,p_ime,p_jmbg,p_iznos,p_rbr,p_status_racuna};
                Map res=spc.executeProc(param_values);
                List<PENZIJE> result = (List<PENZIJE>) res.get("rs_penzije");
                return result;
        
        
        }
        @Override
        public List<UplatePIO> PodacioUplatama(String p_akcija,String p_datum,String p_iznos_uplate,String p_lista)
        {

                String PROC_NAME="SP_PODACI_O_UPLATAMA_PENZIJA";
                
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
                SqlParameter lista = new SqlParameter("p_lista", Types.VARCHAR);
                SqlOutParameter retval = new SqlOutParameter("uplate_rs",OracleTypes.CURSOR, new PIOUplate());
                
                
                SqlParameter[] paramArray = {akcija,datum,iznos_uplate,lista,retval};

                StoredProcedureCall spc=new StoredProcedureCall(dataSource, PROC_NAME, paramArray);
                Object [] param_values={p_akcija,p_datum,p_iznos_uplate,p_lista};
                Map res=spc.executeProc(param_values);
                List<UplatePIO> result = (List<UplatePIO>) res.get("uplate_rs");
                return result;

	}
        

        @Override
	public List<PENZIJE> ParsePenzijeImport(Integer p_PioType, Integer p_uplata)
        {

                String PROC_NAME="SGS_PIO.sp_ParsePenzijeImport";

                SqlParameter tip = new SqlParameter("p_PioType", Types.INTEGER);
                SqlParameter uplata = new SqlParameter("p_Uplata", Types.INTEGER);
                SqlOutParameter rst = new SqlOutParameter("rs", OracleTypes.CURSOR, new PENZIJEMapper());
                SqlOutParameter rsUgaseni = new SqlOutParameter("rsUgaseni", OracleTypes.CURSOR, new PENZIJEMapper());
                
                SqlParameter[] paramArray = {tip,uplata,rst,rsUgaseni};

                StoredProcedureCall spc=new StoredProcedureCall(dataSource, PROC_NAME, paramArray);
                Object [] param_values={p_PioType,p_uplata};
                Map res=spc.executeProc(param_values);
                List<PENZIJE> result = (List<PENZIJE>) res.get("rs");
                List<UGASENI> ugaseni=(List<UGASENI>) res.get("rsUgaseni");
                setRsetUgaseni(ugaseni);
                
                return result;

	}
        
         @Override
	public List<VRSTEFAJLOVA> VrsteFajlova()
        {

                String PROC_NAME="SGS_PIO.SP_TIPOVI_FAJLOVA";
                SqlOutParameter rsTip = new SqlOutParameter("rsTipovi", OracleTypes.CURSOR, new FajloviMapper());
                
                SqlParameter[] paramArray = {rsTip};

                StoredProcedureCall spc=new StoredProcedureCall(dataSource, PROC_NAME, paramArray);
                Object [] param_values={null};
                Map res=spc.executeProc(param_values);
                List<VRSTEFAJLOVA> result = (List<VRSTEFAJLOVA>) res.get("rsTipovi");

                return result;

	}


         @Override
	public PENZIJE PENZIJEDetails(Integer id_knjiz)
        {

               PENZIJE result ;
               Map<String, Object> params = new HashMap<>();
               params.put("id_knjiz", id_knjiz);

                String sql = "select tp.RBR as id_knjiz,tp.FILIJALA,tp.JMBG,(to_number(neto )* 100) /10000 IZNOS_RATE,tp.TR as RACUN,tp.IME,tpi.STATUS_RACUNA,tpi.STATUS,0 as ISPRAVNO  from tbl_penzije tp inner join tbl_penzije_ispravni tpi on tpi.rbr=tp.rbr where tp.RBR=:id_knjiz";
		result = (PENZIJE) namedParameterJdbcTemplate.queryForObject(sql,params,new PENZIJEMapper());
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
              
                String PROC_NAME="SGS_PIO.SP_PENZIJE_STAVKE_KNJIZENJA";

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
              
                String PROC_NAME="SGS_PIO.SP_PENZIJE_MONITORING";

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
	public PENZIJE PENZIJEErrorDetails(Integer id_knjiz)
        {

               PENZIJE result ;
               Map<String, Object> params = new HashMap<>();
               params.put("id_knjiz", id_knjiz);

                String sql = "select tp.RBR as id_knjiz,tp.FILIJALA,tp.JMBG,(to_number(neto )* 100) /10000 IZNOS_RATE,tp.TR as RACUN,tp.IME ,tp.STATUS_RACUNA,tp.STATUS,0 as ISPRAVNO  from tbl_penzije tp  where rbr=:id_knjiz";
		result = (PENZIJE) namedParameterJdbcTemplate.queryForObject(sql,params,new GreskeMapper());
		return result;

	}
        
        
       
        
        @Override
	public List<ComboValues> Posalji_Na_Knjizenje(String p_akcija,Integer p_idk,Integer p_tms_id,String p_iznos_uplate,String p_datum_unosa,Integer fil)
        {

                String PROC_NAME="SGS_PIO.SP_PENZIJE_STAVKE_SPISKA";

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
           String PROC_NAME="SGS_PIO.SP_PENZIJE_PROCESS";
           
             
           SqlOutParameter retval = new SqlOutParameter("odgovor_rs",OracleTypes.CURSOR, new ComboValueMapper());
           SqlParameter[] paramArray = {retval};
           StoredProcedureCall spc=new StoredProcedureCall(dataSource, PROC_NAME, paramArray);
           Object [] param_values={null};
           Map res=spc.executeProc(param_values);
           List<ComboValues> result = (List<ComboValues>) res.get("odgovor_rs");
           
           return result;
        
        }
        @Override
        public List<ComboValues>  ImportRacuna()
        {
           String PROC_NAME="SGS_PIO.SPIMPORTRACUNA";
           
             
           SqlOutParameter retval = new SqlOutParameter("odgovor_rs",OracleTypes.CURSOR, new ComboValueMapper());
           SqlParameter[] paramArray = {retval};
           StoredProcedureCall spc=new StoredProcedureCall(dataSource, PROC_NAME, paramArray);
           Object [] param_values={null};
           Map res=spc.executeProc(param_values);
           List<ComboValues> result = (List<ComboValues>) res.get("odgovor_rs");
           
           return result;
        
        }
        @Override       
        public void PIO_EXPORT(Integer p_tip)
        {
               String PROC_NAME="SGS_PIO.PENZIJE_EXPORT";

                SqlParameter filijala = new SqlParameter("p_tip", Types.VARCHAR);
                SqlOutParameter suma = new SqlOutParameter("rs", OracleTypes.CURSOR, new PENZIJEMapper());
                SqlOutParameter rst = new SqlOutParameter("rsUgaseni", OracleTypes.CURSOR, new PENZIJEMapper());
                
                SqlParameter[] paramArray = {filijala,suma,rst};

                StoredProcedureCall spc=new StoredProcedureCall(dataSource, PROC_NAME, paramArray);
                Object [] param_values={p_tip};
                Map res=spc.executeProc(param_values);
                List<PENZIJE> ispravni=(List<PENZIJE>) res.get("rs");
                List<UGASENI> ugaseni=(List<UGASENI>) res.get("rsUgaseni");
                setRset(ispravni);
                setRsetUgaseni(ugaseni);
                
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
       
        private static final class PENZIJEMapper implements RowMapper<PENZIJE> 
        {

                @Override
		public PENZIJE mapRow(ResultSet rs, int rowNum) throws SQLException 
                {
			PENZIJE pio = new PENZIJE();
                        pio.setRbr(rs.getInt("id_knjiz"));
			pio.setFilijala(rs.getString("FILIJALA"));
                        pio.setJmbg(rs.getString("JMBG"));
                        pio.setIznos_rate(rs.getString("IZNOS_RATE"));
			pio.setPartija(rs.getString("RACUN"));
                        pio.setIme(rs.getString("IME"));
                        pio.setStatus_racuna(rs.getString("STATUS_RACUNA"));
                        pio.setStatus(rs.getString("STATUS"));
                        pio.setIspravno(rs.getInt("ISPRAVNO"));
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
                        pio.setIdkg(rs.getInt("idkg"));
                        pio.setCnt(rs.getInt("cnt"));
                        pio.setPosition(rs.getInt("position"));
                        pio.setUkupan_iznos(rs.getBigDecimal("ukupan_iznos"));
			return pio;
		}
	}
        
          private static final class GreskeMapper implements RowMapper<PENZIJE> 
        {

                @Override
		public PENZIJE mapRow(ResultSet rs, int rowNum) throws SQLException 
                {
			PENZIJE pio = new PENZIJE();
                        pio.setRbr(rs.getInt("id_knjiz"));
			pio.setFilijala(rs.getString("FILIJALA"));
                        pio.setJmbg(rs.getString("JMBG"));
                        pio.setIznos_rate(rs.getString("IZNOS_RATE"));
			pio.setPartija(rs.getString("RACUN"));
                        pio.setIme(rs.getString("IME"));
                        pio.setStatus_racuna(rs.getString("STATUS_RACUNA"));
                        pio.setStatus(rs.getString("STATUS"));
                        pio.setIspravno(rs.getInt("ISPRAVNO"));
			return pio;
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
                        claim.setPartija(rs.getString("partija"));
			return claim;
		}
	}
        
        private static final class FajloviMapper implements RowMapper<VRSTEFAJLOVA> 
        {

                @Override
		public VRSTEFAJLOVA mapRow(ResultSet rs, int rowNum) throws SQLException 
                {
			VRSTEFAJLOVA claim = new VRSTEFAJLOVA();
                        claim.setTip_fajla(rs.getString("tip_fajla"));
			claim.setNaziv_fajla(rs.getString("naziv_fajla"));
 
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
        public List<PENZIJE> getRset()
        {
        return rset;
        }
        
        @Override
        @DataServiceImpl.Property(key="property.key", defaultValue="default")
        public void setRset(List<PENZIJE> rset) {
            this.rset = rset;
        }
        
        @Override
        @DataServiceImpl.Property(key="property.key", defaultValue="default")
        public List<UGASENI> getRsetUgaseni()
        {
        return rsetugaseni;
        }
        
        @Override
        @DataServiceImpl.Property(key="property.key", defaultValue="default")
        public void setRsetUgaseni(List<UGASENI> rsetugaseni) {
            this.rsetugaseni = rsetugaseni;
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
        
        
       
        
        @Retention(RetentionPolicy.RUNTIME) 
        @Target({ElementType.METHOD, ElementType.FIELD})
        public @interface Property {
            String key();
            String defaultValue() default "";
        }
        
    
}
