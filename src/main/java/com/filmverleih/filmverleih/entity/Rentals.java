package com.filmverleih.filmverleih.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 *  Entitiy class for Customers
 */
@Entity
@IdClass(RentalId.class)
public class Rentals {

    @Id
    @Column(name = "movieid")
    private int movieid;
    @Id
    @Column(name = "customerid")
    private int customerid;
    @Basic
    @Column(name = "startdate", nullable = true, length = 10)
    private String startdate;
    @Basic
    @Column(name = "enddate", nullable = true, length = 10)
    private String enddate;
    @ManyToOne
    @JoinColumn(name = "movieid", referencedColumnName = "movieid", nullable = false, insertable=false, updatable=false)
    private Movies movie;
    @ManyToOne(optional = false)
    @JoinColumn(name = "customerid", referencedColumnName = "customerid", nullable = false, insertable=false, updatable=false)
    private Customers customer;


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

    public Movies getMovie() {
        return movie;
    }

    public void setMovie(Movies movie) {
        this.movie = movie;
    }

    public Customers getCustomer() {
        return customer;
    }

    public void setCustomer(Customers customer) {
        this.customer = customer;
    }
}
