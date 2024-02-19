package com.filmverleih.filmverleih.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Entitiy class for Users
 */
@Entity
@Table(name = "users", schema = "public", catalog = "filmverleih")
public class Users implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "userid", nullable = false)
    private int userid;
    @Basic
    @Column(name = "name", nullable = false, length = -1)
    private String name;
    @Basic
    @Column(name = "password", nullable = false, length = -1)
    private String password;
    @Basic
    @Column(name = "isadmin", nullable = true)
    private Boolean isadmin;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsadmin() {
        return isadmin;
    }

    public void setIsadmin(Boolean isadmin) {
        this.isadmin = isadmin;
    }

    /**
     * Compares this instance with the specified object and indicates if they are equal.
     *
     * @param o Object to be compared for equality
     * @return true if the specified object is equal to this object; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return userid == users.userid && Objects.equals(name, users.name) && Objects.equals(password, users.password) && Objects.equals(isadmin, users.isadmin);
    }

/*    @Override
    public int hashCode() {
        return Objects.hash(userid, name, password, isadmin);
    }*/
}
