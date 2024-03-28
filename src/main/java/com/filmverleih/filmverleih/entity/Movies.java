package com.filmverleih.filmverleih.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;


/**
 *  Entitiy class for Movies
 */
@Entity
public class Movies {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "movieid", nullable = false)
    private int movieid;
    @Basic
    @Column(name = "name", nullable = false, length = -1)
    private String name;
    @Basic
    @Column(name = "year", nullable = false)
    private int year;
    @Basic
    @Column(name = "genre", nullable = false, length = -1)
    private String genre;
    @Basic
    @Column(name = "length", nullable = false)
    private int length;
    @Basic
    @Column(name = "rating", nullable = false, precision = 1)
    private BigDecimal rating;
    @Basic
    @Column(name = "count", nullable = false)
    private int count;
    @Basic
    @Column(name = "type", nullable = false, length = -1)
    private String type;
    @Basic
    @Column(name = "cover", nullable = false, length = -1)
    private String cover;
    @Basic
    @Column(name = "comment", nullable = false, length = -1)
    private String comment;
    @Basic
    @Column(name = "directors", nullable = true, length = -1)
    private String directors;
    @Basic
    @Column(name = "studio", nullable = true, length = -1)
    private String studio;
    @Basic
    @Column(name = "actors", nullable = true, length = -1)
    private String actors;
    @Basic
    @Column(name = "fsk", nullable = false)
    private int fsk;


    public int getMovieid() {
        return movieid;
    }

    public void setMovieid(int movieid) {
        this.movieid = movieid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCover() { return cover; }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String getStudio() { return studio; }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public int getFsk() {
        return fsk;
    }

    public void setFsk(int fsk) {
        this.fsk = fsk;
    }

    /**
     * Checks if this Movies object is equal to another object.
     *
     * @param  o    the object to compare to
     * @return      true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movies movies = (Movies) o;

        if (movieid != movies.movieid) return false;
        if (year != movies.year) return false;
        if (length != movies.length) return false;
        if (count != movies.count) return false;
        if (fsk != movies.fsk) return false;
        if (name != null ? !name.equals(movies.name) : movies.name != null) return false;
        if (genre != null ? !genre.equals(movies.genre) : movies.genre != null) return false;
        if (rating != null ? !rating.equals(movies.rating) : movies.rating != null) return false;
        if (type != null ? !type.equals(movies.type) : movies.type != null) return false;
        if (cover != null ? !cover.equals(movies.cover) : movies.cover != null) return false;
        if (comment != null ? !comment.equals(movies.comment) : movies.comment != null) return false;
        if (directors != null ? !directors.equals(movies.directors) : movies.directors != null) return false;
        if (studio != null ? !studio.equals(movies.studio) : movies.studio != null) return false;
        if (actors != null ? !actors.equals(movies.actors) : movies.actors != null) return false;

        return true;
    }

    /**
     * Classic toString Method to return the Movies object.
     * @return String with all the values
     */
    public String toSting() {
        return "Movie { " +
                "movieid=" + movieid +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", genre='" + genre + '\'' +
                ", length=" + length +
                ", rating=" + rating +
                ", count=" + count +
                ", type='" + type + '\'' +
                ", cover='" + cover + '\'' +
                ", comment='" + comment + '\'' +
                ", directors='" + directors + '\'' +
                ", studio='" + studio + '\'' +
                ", actors='" + actors + '\'' +
                ", fsk=" + fsk +
                '}';
    }

/*    @Override
    public int hashCode() {
        int result = movieid;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + year;
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        result = 31 * result + length;
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + count;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (cover != null ? cover.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (directors != null ? directors.hashCode() : 0);
        result = 31 * result + (studio != null ? studio.hashCode() : 0);
        result = 31 * result + (actors != null ? actors.hashCode() : 0);
        result = 31 * result + fsk;
        return result;
    }*/
}
