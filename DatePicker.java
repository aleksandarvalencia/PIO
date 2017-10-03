
package com.projects.pio.model;


public class DatePicker {
    private Long year;
    private Long month;
    private Long day;

    public Long getYear() {
        return year;
    }

    public void setYear(final Long year) {
        this.year = year;
    }

    public Long getMonth() {
        return month;
    }

    public void setMonth(final Long month) {
        this.month = month;
    }

    public Long getDay() {
        return day;
    }

    public void setDay(final Long day) {
        this.day = day;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "DatePicker [year=" + year + ", month=" + month + ", day=" + day + "]";
    }
}

