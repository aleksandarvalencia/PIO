/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.pio.service;

import com.projects.pio.model.ClaimRacuni;
import com.projects.pio.model.PENZIJE;
import com.projects.pio.model.ComboValues;
import com.projects.pio.model.Greske;
import com.projects.pio.model.Iznosi;
import com.projects.pio.model.StavkeKnjizenja;
import com.projects.pio.model.UGASENI;
import com.projects.pio.model.UplatePIO;
import com.projects.pio.model.VRSTEFAJLOVA;
import java.math.BigDecimal;

import java.util.List;


/**
 *
 * @author bd101009
 */
public interface PensionService 
{
    
        PENZIJE PENZIJEDetails(Integer id_knjiz);
        BigDecimal IznosiPENZIJE(Integer tip);
        List<PENZIJE> ParsePenzijeImport(Integer p_PioType, Integer p_uplata);
        Integer getReturnValue() ;
        void setReturnValue(Integer log);
        BigDecimal getUkupan_Iznos();
        void setUkupan_Iznos(BigDecimal lg);
        void setRset(List<PENZIJE> rset);
        void setRsetUgaseni(List<UGASENI> rsetugaseni);
        List<PENZIJE> getRset();
        List<UGASENI> getRsetUgaseni();
        List<PENZIJE> SP_PENZIJE (String p_akcija,String p_filijala,String p_racun,String p_ime,String p_jmbg, String p_iznos,Integer p_rbr,String p_status_racuna);
        List<PENZIJE> AccountCheck(String p_akcija);
        List<PENZIJE> AccountCheckTable();
        List<ClaimRacuni> ClaimAccounts();
        List<ComboValues> Posalji_Na_Knjizenje(String p_akcija,Integer idk,Integer tms_id,String p_iznos_uplate,String p_datum_unosa,Integer fil);
        void setStavkeKnjizenja(List<StavkeKnjizenja> rset);
        List<StavkeKnjizenja> getStavkeKnjizenja();
        PENZIJE PENZIJEErrorDetails(Integer id_knjiz);
        List<Iznosi> StavkeKnjizenja(String p_akcija,Integer fil,Integer p_idk,String p_datum);
        List<Iznosi> Monitoring(String p_akcija,Integer fil,String p_datum,String p_idk);void setMonitoring(List<UplatePIO> rset);
        List<UplatePIO> getMonitoring();
        List<ComboValues>  SendToTMS(); 
        List<UplatePIO> PodacioUplatama(String p_akcija,String p_datum,String p_iznos_uplate,String p_lista);
        List<ComboValues>  ImportRacuna(); 
        List<VRSTEFAJLOVA> VrsteFajlova();
        void PIO_EXPORT(Integer p_tip);
}
