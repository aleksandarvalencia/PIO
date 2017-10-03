/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projects.pio.model;



public class FormDate {
    private DatePicker datePicker;

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public void setDatePicker(final DatePicker datePicker) {
        this.datePicker = datePicker;
    }

    @Override
    public String toString() {
        return "FormDate [datePicker=" + datePicker + "]";
    }

}

