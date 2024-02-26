package com.filmverleih.filmverleih;
import com.filmverleih.filmverleih.entity.Movies;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 *  SetMovTest
 *  This class is used to test the database Curd Operations with the entity Movies
 *  19.02.2024
 *  Copyright by Torvalds
 */
public class SetMovTest {

    public static void main(String[] args) {
        SetMovTest test = new SetMovTest();
        boolean menue = true;
        Scanner scanner = new Scanner(System.in);
        while (menue){
            System.out.println("Was willste machen: 1 Liste Ausgeben  2 Test Film Hinzufügen 3 Delete 4 Update  5 Exit");
            int inp = scanner.nextInt();
            if (inp == 1){
                List<Movies> movieList = test.getFullMovieList();
                for (Movies movie : movieList) {
                    System.out.println(movie.toSting());
                }
            } else if (inp == 2) {
                test.newMovieInDB();
            } else if (inp == 3) {
                System.out.println("Was soll gelöscht werden? ");
                int delID = scanner.nextInt();
                test.DeleteMovieInDB(delID);
            } else if (inp == 4) {
                System.out.println("Was soll aktualisiert werden? ");
                int upID = scanner.nextInt();
                test.UpdateMovieInDB(upID);
            } else if (inp == 5) {
                menue = false;
            }
        }
    }


     public List<Movies> getFullMovieList() {
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


}