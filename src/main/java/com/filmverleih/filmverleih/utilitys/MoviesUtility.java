package com.filmverleih.filmverleih.utilitys;

import com.filmverleih.filmverleih.entity.Movies;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MoviesUtility {

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
                LoggerUtility.logger.warn("getFullUserList went wrong, could not transact: 005");
            }
        } catch (Exception e) {
            LoggerUtility.logger.warn("build session failed: 006");
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
                LoggerUtility.logger.warn("deleteMovieInDB went wrong, could not transact: 007");
                return false;
            }
        } catch (Exception e) {
            LoggerUtility.logger.warn("build session failed: 008");
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
                LoggerUtility.logger.warn("updateMovieInDB went wrong, could not transact: 009");
            }
        } catch (Exception e) {
            LoggerUtility.logger.warn("build session failed: 010");
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

}
