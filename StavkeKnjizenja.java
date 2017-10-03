/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.pio.model;

/**
 *
 * @author bd101009
 */
public class StavkeKnjizenja 
{
    
    String idk;
    String ime;
    String datum_valute ;
    String datum_obrade;
    String debit_account;
    String credit_account;
    String iznos;
    String tms_id;
    String status;

    public String getIdk() {
        return idk;
    }

    public void setIdk(String idk) {
        this.idk = idk;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getDatum_valute() {
        return datum_valute;
    }

    public void setDatum_valute(String datum_valute) {
        this.datum_valute = datum_valute;
    }

    public String getDatum_obrade() {
        return datum_obrade;
    }

    public void setDatum_obrade(String datum_obrade) {
        this.datum_obrade = datum_obrade;
    }

    public String getDebit_account() {
        return debit_account;
    }

    public void setDebit_account(String debit_account) {
        this.debit_account = debit_account;
    }

    public String getCredit_account() {
        return credit_account;
    }

    public void setCredit_account(String credit_account) {
        this.credit_account = credit_account;
    }

    public String getIznos() {
        return iznos;
    }

    public void setIznos(String iznos) {
        this.iznos = iznos;
    }

    public String getTms_id() {
        return tms_id;
    }

    public void setTms_id(String tms_id) {
        this.tms_id = tms_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
