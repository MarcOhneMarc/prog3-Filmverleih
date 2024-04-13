package com.filmverleih.filmverleih.entity;


import java.io.Serializable;
import java.util.Objects;

/**
 *  IDClass class for Rental
 */
public class RentalId implements Serializable {
    private int movieid;

    private int customerid;

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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RentalId rentalId = (RentalId) o;
        return movieid == rentalId.movieid && customerid == rentalId.customerid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieid, customerid);
    }
}