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
public class Iznosi 
{
    
    private BigDecimal ukupan_iznos;
    private BigDecimal iznos_filijala;
    private BigDecimal iznos_stavke;
    private BigDecimal sum_ukupno;

    public BigDecimal getSum_ukupno() {
        return sum_ukupno;
    }

    public void setSum_ukupno(BigDecimal sum_ukupno) {
        this.sum_ukupno = sum_ukupno;
    }

    public BigDecimal getIznos_stavke() {
        return iznos_stavke;
    }

    public void setIznos_stavke(BigDecimal iznos_stavke) {
        this.iznos_stavke = iznos_stavke;
    }
    
     public Iznosi(BigDecimal value,BigDecimal label)
    {
        this.ukupan_iznos=value;
       this.iznos_filijala=label;
    }

    public BigDecimal getUkupan_iznos() {
        return ukupan_iznos;
    }

    public void setUkupan_iznos(BigDecimal ukupan_iznos) {
        this.ukupan_iznos = ukupan_iznos;
    }

    public BigDecimal getIznos_filijala() {
        return iznos_filijala;
    }

    public void setIznos_filijala(BigDecimal iznos_filijala) {
        this.iznos_filijala = iznos_filijala;
    }
    
    
}
