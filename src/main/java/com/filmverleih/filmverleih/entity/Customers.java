package com.filmverleih.filmverleih.entity;

import jakarta.persistence.*;

import java.util.Objects;

/**
 *  Entitiy class for Customers
 */
@Entity
public class Customers {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "customerid", nullable = false)
    private int customerid;
    @Basic
    @Column(name = "firstname", nullable = false, length = -1)
    private String firstname;
    @Basic
    @Column(name = "lastname", nullable = false, length = -1)
    private String lastname;
    @Basic
    @Column(name = "street", nullable = false, length = -1)
    private String street;
    @Basic
    @Column(name = "postalcode", nullable = false, length = -1)
    private String postalcode;
    @Basic
    @Column(name = "city", nullable = false, length = -1)
    private String city;
    @Basic
    @Column(name = "phone", nullable = false, length = -1)
    private String phone;
    @Basic
    @Column(name = "email", nullable = false, length = -1)
    private String email;

    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customers customers = (Customers) o;
        return customerid == customers.customerid && Objects.equals(firstname, customers.firstname) && Objects.equals(lastname, customers.lastname) && Objects.equals(street, customers.street) && Objects.equals(postalcode, customers.postalcode) && Objects.equals(city, customers.city) && Objects.equals(phone, customers.phone) && Objects.equals(email, customers.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerid, firstname, lastname, street, postalcode, city, phone, email);
    }
}
