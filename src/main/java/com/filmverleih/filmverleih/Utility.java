package com.filmverleih.filmverleih;

import com.filmverleih.filmverleih.entity.Movies;
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

    public List<Movies> newMovieInDB() {
        try (SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();

                Movies newMovie = new Movies();
                newMovie.setName("Titanische Künste");
                newMovie.setYear(1997);
                newMovie.setGenre("Romance, Drama");
                newMovie.setLength(195);
                newMovie.setRating(BigDecimal.valueOf(7.5));
                newMovie.setCount(1);
                newMovie.setType("DVD");
                newMovie.setCover("titanic.jpg");
                newMovie.setComment("A classic romantic drama film.");
                newMovie.setDirectors("James Cameron");
                newMovie.setStudio("20th Century Fox");
                newMovie.setActors("Leonardo DiCaprio, Kate Winslet");
                newMovie.setFsk(16);

                // Film zur Datenbank hinzufügen
                session.save(newMovie);

                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                e.printStackTrace(); // replace with logger
            }
        } catch (Exception e) {
            e.printStackTrace(); // replace with logger
        }
        return new ArrayList<Movies>();
    }

    public List<Movies> DeleteMovieInDB(int movDelID) {
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
            }
        } catch (Exception e) {
            e.printStackTrace(); // replace with logger
        }
        return new ArrayList<Movies>();
    }

    public List<Movies> UpdateMovieInDB(int movUpID) {
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
        return new ArrayList<Movies>();
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

}
