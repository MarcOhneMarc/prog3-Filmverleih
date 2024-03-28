package com.filmverleih.filmverleih;

import com.filmverleih.filmverleih.entity.Movies;
import com.filmverleih.filmverleih.entity.Users;
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

    public Boolean DeleteMovieInDB(int movDelID) {
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

    public Boolean UpdateMovieInDB(int movUpID) {
        try (SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();

                session.createQuery("UPDATE Movies SET name = 'Titanische Könste Updtaet' WHERE movieid = " + movUpID).executeUpdate();

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
     * This method provides a List of all User from the Table Users
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
     * This method deletes a user from the database
     * @param user
     * @return
     */
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
