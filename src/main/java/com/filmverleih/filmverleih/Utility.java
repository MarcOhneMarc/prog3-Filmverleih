package com.filmverleih.filmverleih;

import com.filmverleih.filmverleih.entity.Customers;
import com.filmverleih.filmverleih.entity.Movies;
import com.filmverleih.filmverleih.entity.Users;
import com.filmverleih.filmverleih.entity.Rentals;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.event.spi.EventSource;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utility {
    // Database Utilities

    //Loading/Instantiating
    public static FXMLLoader loadFXML(String str)
    {       FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource(str));
            return loader;
    }

    //
    public static List<Movies> getFullMovieList() {
        try (SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                List<Movies> movies = session.createQuery("FROM Movies ", Movies.class).getResultList();
                transaction.commit();
                return movies;
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                e.printStackTrace(); // replace with logger
            }
        } catch (Exception e) {
            e.printStackTrace(); // replace with logger
        }
        return new ArrayList<Movies>();
    }

    public Boolean newMovieInDB(String name, int year, String genre, int length, BigDecimal rating, int count, String type, String cover, String comment, String directors, String studio, String actors, int fsk, String movieType) {
        try (SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();

                Movies newMovie = new Movies();
                newMovie.setName(name);
                newMovie.setYear(year);
                newMovie.setGenre(genre);
                newMovie.setLength(length);
                newMovie.setRating(rating);
                newMovie.setCount(count);
                newMovie.setType(type);
                newMovie.setCover(cover);
                newMovie.setComment(comment);
                newMovie.setDirectors(directors);
                newMovie.setStudio(studio);
                newMovie.setActors(actors);
                newMovie.setFsk(fsk);
                newMovie.setType(movieType);

                // Film zur Datenbank hinzufügen
                session.save(newMovie);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                e.printStackTrace(); // replace with logger
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace(); // replace with logger
            return false;
        }
        return true;
    }

    public static Boolean DeleteMovieInDB(int movDelID) {
        try (SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();

                session.createQuery("DELETE FROM Movies WHERE movieid = " + movDelID).executeUpdate();

                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                e.printStackTrace(); // replace with logger
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace(); // replace with logger
            return false;
        }
        return true;
    }

    /**
     * Utility method to update a movie record in the database.
     * @param movieid The ID of the movie to be updated.
     * @param name The new name of the movie.
     * @param year The new year of the movie.
     * @param length The new length of the movie.
     * @param fsk The new FSK rating of the movie.
     * @param rating The new rating of the movie.
     * @param genres The new genres of the movie.
     * @param directors The new directors of the movie.
     * @param count The new count of the movie.
     * @param studio The new studio of the movie.
     * @param actors The new actors of the movie.
     * @param cover The new cover of the movie.
     * @param comment The new comment of the movie.
     * @param type The new type of the movie.
     * @return True if the update was successful, false otherwise.
     */
    public static Boolean UpdateMovieInDB(int movieid,
                                    String name,
                                    int year,
                                    int length,
                                    int fsk,
                                    BigDecimal rating,
                                    String genres,
                                    String directors,
                                    int count,
                                    String studio,
                                    String actors,
                                    String cover,
                                    String comment,
                                    String type) {
        try (SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();

                Query query = session.createQuery("UPDATE Movies SET name = :name" +
                        ", year = :year" +
                        ", length = :length" +
                        ", fsk = :fsk" +
                        ", rating = :rating" +
                        ", genre = :genre" +
                        ", directors = :directors" +
                        ", count = :count" +
                        ", studio = :studio" +
                        ", actors = :actors" +
                        ", cover = :cover" +
                        ", comment = :comment" +
                        ", type = :type" +
                        " WHERE movieid = :movieid");

                query.setParameter("name", name);
                query.setParameter("year", year);
                query.setParameter("length", length);
                query.setParameter("fsk", fsk);
                query.setParameter("rating", rating);
                query.setParameter("genre", genres);
                query.setParameter("directors", directors);
                query.setParameter("count", count);
                query.setParameter("studio", studio);
                query.setParameter("actors", actors);
                query.setParameter("cover", cover);
                query.setParameter("comment", comment);
                query.setParameter("type", type);
                query.setParameter("movieid", movieid);

                query.executeUpdate();

                transaction.commit();

            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                e.printStackTrace(); // replace with logger
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace(); // replace with logger
            return false;
        }
        return true;
    }

    /**
     * Utility method to retrieve a movie from the database by its ID.
     * @param movieId The ID of the movie to retrieve.
     * @return The movie object corresponding to the given ID, or null if no such movie is found.
     */
    public static Movies getMovieById(int movieId) {
        Movies returnMovie = null;
        try (SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();

                returnMovie = session.createQuery("FROM Movies WHERE movieid =" + movieId, Movies.class).getSingleResult();

                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                e.printStackTrace(); // replace with logger
            }
        } catch (Exception e) {
            e.printStackTrace(); // replace with logger
        }
        return returnMovie;
    }
    Movies getMovieByUrl(String url)
    {
        Movies ret = new Movies();
        for(Movies movie:getFullMovieList())
        {
            if (movie.getCover().equals(url)) ret = movie;
        }
        return ret;
    }
    Movies getMovieByName(String name)
    {
        Movies ret = new Movies();
        for(Movies movie:getFullMovieList())
        {
            if (movie.getName().equals(name)) ret = movie;
        }
        return ret;
    }
    EventHandler mouseHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            Movies mov = new Movies();
            if (mouseEvent.getSource() instanceof ImageView)
            {
                ImageView source = (ImageView) mouseEvent.getSource();
                mov = getMovieByUrl(source.getImage().getUrl());
            }
            else
            {
               Label source  = (Label) mouseEvent.getSource();
               mov = getMovieByName(source.getText());
            }
        }
    };


    /**
     * This method provides a List of all User from the table Users
     * @return a list with all users
     */
    public static List<Users> getFullUserList() {
        try (SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                List<Users> users = session.createQuery("FROM Users ", Users.class).getResultList();
                transaction.commit();
                return users;
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  new ArrayList<Users>();
    }

    /**
     * This method provides a List of all Customer from the table customers
     * @return a List with all customers
     */
    public static List<Customers> getFullCustomerList() {
        try (SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                List<Customers> customers = session.createQuery("FROM Customers ", Customers.class).getResultList();
                transaction.commit();
                return customers;
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  new ArrayList<Customers>();
    }

    /**
     * This method checks if a customer is already registered in the db
     * using the customerID and checking its occurrence
     * @param id the customerID that will be checked
     * @return true if customer is registered, false if not
     */
    public static Boolean checkCustomerDuplicate(int id) {
        for(Customers customers:getFullCustomerList())
        {
            if (customers.getCustomerid() == id ) return true;
        }
        return false;
    }

    /**
     * This method adds a customer to db into the customers table
     * @param //customerId the id of the customer
     * @param firstName the firstname of the customer
     * @param lastName the lastname of the customer
     * @param street the street where the customer lives
     * @param postalCode the postalCode where the customer lives
     * @param city the city where the customer lives
     * @param phone the phone number of the customer
     * @param email the email address of the customer
     * @return true if adding was successful, false if not
     */
    public static Boolean addCustomerToDB(String firstName, String lastName, String street, String postalCode, String city, String phone, String email) {
        try (SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();

                Customers customers = new Customers();
                //customers.setCustomerid(customerId);
                customers.setFirstname(firstName);
                customers.setLastname(lastName);
                customers.setStreet(street);
                customers.setPostalcode(postalCode);
                customers.setCity(city);
                customers.setPhone(phone);
                customers.setEmail(email);

                // add customer to db
                session.save(customers);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                e.printStackTrace(); // replace with logger
                System.out.println("customer registration went wrong Code: 77621");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace(); // replace with logger
            System.out.println("customer registration went wrong Code: 77620");
            return false;
        }
        return true;
    }

    /**
     * This method adds a rental to db into the rentals table
     * @param movieID the id of the rented movie
     * @param customerID the id of the customer
     * @param startdate the star date of the rental
     * @param enddate the return date of the rental
     * @return true if adding was successful, false if not
     */
    public static Boolean addRentalToDB(int movieID, int customerID, String startdate, String enddate) {
        try (SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();

                Rentals rental = new Rentals();
                rental.setMovieid(movieID);
                rental.setCustomerid(customerID);
                rental.setStartdate(startdate);
                rental.setEnddate(enddate);

                // add rental to db
                session.save(rental);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                e.printStackTrace(); // replace with logger
                System.out.println("Order went wrong Code: 77619");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace(); // replace with logger
            System.out.println("Order went wrong Code: 77618");
            return false;
        }
        return true;
    }

    /**
     * This method returns the last added customer ID from the
     * customer table by getting the id of the last element of the
     * fullCustomersList();
     * @return the last added customerID
     */
    public static int getLastAddedCustomerID() {
        List<Customers> customersList = getFullCustomerList();
        if (customersList.isEmpty()) {
            return 1;
        }
        return customersList.getLast().getCustomerid();
    }
   public Boolean deleteUserInDB(Users user){
        try (SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();

                session.createQuery("DELETE FROM Users WHERE userid = " + user.getUserid()).executeUpdate();

                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * This method adds a new user to the database
     * @param user the user that will be added to the database
     * @return
     */
    public Boolean addUserToDB(Users user) {
        try (SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.save(user);//add user to db
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     *
     * @param newPassword of the password
     * @param userId of the user
     * @return
     */
    public Boolean UpdateUserPasswordInDB(String newPassword, int userId) {
        try (SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();

                session.createQuery("UPDATE Users SET password =" + "'" +newPassword+ "'" + " WHERE userid = " + userId).executeUpdate();

                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                e.printStackTrace(); // replace with logger
            }
        } catch (Exception e) {
            e.printStackTrace(); // replace with logger
        }
        return true;
    }
}