package com.filmverleih.filmverleih.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.util.Objects;

@Entity
public class Rentals {

    @Id
    @Column(name = "movieid", nullable = false)
    private int movieid;
    @Basic
    @Column(name = "customerid", nullable = false)
    private int customerid;
    @Basic
    @Column(name = "startdate", nullable = true, length = 10)
    private String startdate;
    @Basic
    @Column(name = "enddate", nullable = true, length = 10)
    private String enddate;

    public int getMovieid() {
        return movieid;
    }

    public void setMovieid(int movieid) {
        this.movieid = movieid;
    }

    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rentals rentals = (Rentals) o;
        return movieid == rentals.movieid && customerid == rentals.customerid && Objects.equals(startdate, rentals.startdate) && Objects.equals(enddate, rentals.enddate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieid, customerid, startdate, enddate);
    }
}
