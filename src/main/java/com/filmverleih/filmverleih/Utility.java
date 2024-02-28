package com.filmverleih.filmverleih;

import com.filmverleih.filmverleih.entity.Movies;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.event.spi.EventSource;

import java.io.File;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utility {
    //<editor-fold desc = "FXML-Freakshow">
    public static FXMLLoader loadFXML(String str) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApplication.class.getResource(str));
        return loader;
    }
    //</editor-fold>
    //<editor-fold desc = "Database-Doolaroos">
    public static List<Movies> getFullMovieList() {
        try (SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                session.clear();
                transaction = session.beginTransaction();
                List<Movies> movies = session.createQuery("FROM Movies ", Movies.class).getResultList();
                transaction.commit();
                return movies;
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                e.printStackTrace(); // replace with logger
            }
            finally{
                session.close();
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
                session.save(newMovie);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                e.printStackTrace();
                return false;
            }
            finally{
                session.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Boolean DeleteMovieInDB(int movDelID) {
        try (SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                session.clear();
                transaction = session.beginTransaction();
                session.createQuery("DELETE FROM Movies WHERE movieid = " + movDelID).executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                e.printStackTrace();
                return false;
            }
            finally{
                session.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Boolean UpdateMovieInDB(int movUpID, String column, String columnValue) {
        try (SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                session.clear();
                transaction = session.beginTransaction();
                Movies movieToUpdate = session.get(Movies.class, movUpID);
                if (movieToUpdate != null){
                    switch (column){
                        case "name":
                            movieToUpdate.setName(columnValue);
                            break;
                        case "year":
                            movieToUpdate.setYear(Integer.parseInt(columnValue));
                            break;
                        case "genre":
                            movieToUpdate.setGenre(columnValue);
                            break;
                        case "length":
                            movieToUpdate.setLength(Integer.parseInt(columnValue));
                            break;
                        case "rating":
                            movieToUpdate.setRating(BigDecimal.valueOf(Long.parseLong(columnValue)));
                            break;
                        case "count":
                            movieToUpdate.setCount(Integer.parseInt(columnValue));
                            break;
                        case "type":
                            movieToUpdate.setType(columnValue);
                            break;
                        case "cover":
                            movieToUpdate.setCover(columnValue);
                            break;
                        case "directors":
                            movieToUpdate.setDirectors(columnValue);
                            break;
                        case "studio":
                            movieToUpdate.setStudio(columnValue);
                            break;
                        case "actors":
                            movieToUpdate.setActors(columnValue);
                            break;
                        case "fsk":
                            movieToUpdate.setFsk(Integer.parseInt(columnValue));
                            break;
                        case "comment":
                            movieToUpdate.setComment(columnValue);
                            break;
                    }
                    session.update(movieToUpdate);
                }
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                e.printStackTrace();
            }
            finally{
                session.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    //</editor-fold>
    //<editor-fold desc = "Node-Nookies">
    public static String findColumnByRow(int row){
        switch (row){
            case 1: return "name";
            case 2: return "year";
            case 3: return "genre";
            case 4: return "length";
            case 5: return "rating";
            case 6: return "count";
            case 7: return "type";
            case 8: return "cover";
            case 9: return "directors";
            case 10: return "studio";
            case 11: return "actors";
            case 12: return "fsk";
            case 0 : return "comment";
        }
        return "";
    }
    //</editor-fold>
    //<editor-fold desc = "File-FlicFlacs">
    public static String getAbsolutePath (String resourceName) {
        try{
        URL res = MainApplication.class.getResource(resourceName);
        File file = Paths.get(res.toURI()).toFile();
        String absolutePath = file.getAbsolutePath();
        return absolutePath;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    //</editor-fold>
}
