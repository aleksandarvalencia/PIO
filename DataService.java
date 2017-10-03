/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.pio.service;

import com.projects.pio.model.ClaimRacuni;
import com.projects.pio.model.PIO;
import com.projects.pio.model.ComboValues;
import com.projects.pio.model.Greske;
import com.projects.pio.model.Iznosi;
import com.projects.pio.model.JsTreeNode;
import com.projects.pio.model.StavkeKnjizenja;
import com.projects.pio.model.UplatePIO;
import java.math.BigDecimal;

import java.util.List;


/**
 *
 * @author bd101009
 */
public interface DataService 
{
        List<JsTreeNode> getTreeView();
        List<PIO> allPIO(Integer tip);
        PIO PIODetails(Integer id_knjiz);
        BigDecimal IznosiPIO(Integer tip);
        BigDecimal IznosiPoFilijalama(Integer tip,String p_filijala);
        List<ComboValues> PIOFilijale();   
        List<PIO> FindPIObyFilijala(String p_filijalaOd);
        List<PIO> ParsePIOImport(Integer p_PioType, Integer pFileType);
        void ParsePIOExport(String p_datum,Integer p_PioType);
        List<ComboValues> PIO_FAJL_ZA_KNJIZENJE(String p_filijalaOd,Integer p_PioType);
        Integer getReturnValue() ;
        void setReturnValue(Integer log);
        BigDecimal getUkupan_Iznos();
        void setUkupan_Iznos(BigDecimal lg);
        BigDecimal getIznos_Filijala();
        void setRset(List<PIO> rset);
        List<PIO> getRset();
        void setIznos_Filijala(BigDecimal lg); 
        void SP_PIO (String p_akcija,String p_filijala,String p_id_penzije,String p_obustava,String p_partija,String p_opis,String p_jmbg, String p_iznos,Integer p_rbr,String status_racuna);
        void PIO_EXPORT(String p_filijalaOd);
        List<Greske> AccountCheck(String p_akcija);
        List<Greske> AccountCheckTable();
        List<UplatePIO> UplatePIO();
        List<ClaimRacuni> ClaimAccounts();
        List<UplatePIO> UPIT_ZA_TABELU_KNJIZENJA(String p_datum,BigDecimal iznos_uplate);
        List<UplatePIO> PodacioUplatama(String p_akcija,String p_datum,String p_iznos_uplate);
        List<ComboValues> Posalji_Na_Knjizenje(String p_akcija,Integer idk,Integer tms_id,String p_iznos_uplate,String p_datum_unosa,Integer fil);
        void setStavkeKnjizenja(List<StavkeKnjizenja> rset);
        List<StavkeKnjizenja> getStavkeKnjizenja();
        Greske PIOErrorDetails(Integer id_knjiz);
        List<Iznosi> StavkeKnjizenja(String p_akcija,Integer fil,Integer p_idk,String p_datum);
        List<Iznosi> Monitoring(String p_akcija,Integer fil,String p_datum,String p_idk);void setMonitoring(List<UplatePIO> rset);
        List<UplatePIO> getMonitoring();
        List<ComboValues>  SendToTMS();

}
