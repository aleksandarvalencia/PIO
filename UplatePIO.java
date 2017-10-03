/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.pio.model;

import java.math.BigDecimal;

/**
 *
 * @author bd101009
 */
public class UplatePIO 
{
    Integer ID;
    String racun;
    String datum;
    BigDecimal iznos;
    String kontraracun;
    String ime;
    String pbo;
    String opis;
    String sifra;
    Integer idk;
    Integer cnt;
    Integer position;
    Integer idkg;
    BigDecimal ukupan_iznos;

    public Integer getIdk() {
        return idk;
    }

    public void setIdk(Integer idk) {
        this.idk = idk;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getRacun() {
        return racun;
    }

    public void setRacun(String racun) {
        this.racun = racun;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public BigDecimal getIznos() {
        return iznos;
    }

    public void setIznos(BigDecimal iznos) {
        this.iznos = iznos;
    }

    public String getKontraracun() {
        return kontraracun;
    }

    public void setKontraracun(String kontraracun) {
        this.kontraracun = kontraracun;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPbo() {
        return pbo;
    }

    public void setPbo(String pbo) {
        this.pbo = pbo;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getIdkg() {
        return idkg;
    }

    public void setIdkg(Integer idkg) {
        this.idkg = idkg;
    }

    public BigDecimal getUkupan_iznos() {
        return ukupan_iznos;
    }

    public void setUkupan_iznos(BigDecimal ukupan_iznos) {
        this.ukupan_iznos = ukupan_iznos;
    }
    
    
}
