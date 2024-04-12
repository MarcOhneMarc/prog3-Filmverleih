package com.filmverleih.filmverleih.utilitys;

import com.filmverleih.filmverleih.entity.Movies;
import jakarta.persistence.NoResultException;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

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
                LoggerUtility.logger.warn("getFullUserList went wrong, could not transact:\n" + e.getMessage());
            }
        } catch (Exception e) {
            LoggerUtility.logger.warn("build session failed:\n" + e.getMessage());
        }
        return new ArrayList<Movies>();
    }


    public static Boolean newMovieInDB(String name, int year, String genre, int length, BigDecimal rating, int count, String type, String cover, String comment, String directors, String studio, String actors, int fsk) {
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

                // Film zur Datenbank hinzufügen
                session.save(newMovie);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                LoggerUtility.logger.warn("newMovieInDB went wrong, could not transact:\n" + e.getMessage());
                return false;
            }
        } catch (Exception e) {
            LoggerUtility.logger.warn("build session failed:\n" + e.getMessage());
            return false;
        }
        return true;
    }



    /**
     * A function to delete a movie from the database.
     *
     * @param  movDelID   the ID of the movie to be deleted
     * @return           true if the movie was successfully deleted, false otherwise
     */
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
                LoggerUtility.logger.warn("deleteMovieInDB went wrong, could not transact:\n" + e.getMessage());
                return false;
            }
        } catch (Exception e) {
            LoggerUtility.logger.warn("build session failed:\n" + e.getMessage());
            return false;
        }
        return true;
    }


    /**
     * Update a movie in the database.
     *
     * @param  movUpID   the ID of the movie to be updated
     * @return          true if the movie was successfully updated
     */
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
                LoggerUtility.logger.warn("UpdateMovieInDB went wrong, could not transact:\n" + e.getMessage());
            }
        } catch (Exception e) {
            LoggerUtility.logger.warn("build session failed:\n" + e.getMessage());
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
                LoggerUtility.logger.warn("UpdateMovieInDB went wrong, could not transact:\n" + e.getMessage());
                return false;
            }
        } catch (Exception e) {
            LoggerUtility.logger.warn("build session failed:\n" + e.getMessage());
            return false;
        }
        LoggerUtility.logger.info("movie updated successfully: " + movieid + ", " + name);
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
            } catch (NoResultException e) {
                return null;
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                LoggerUtility.logger.warn("getMovieByID went wrong, could not transact:\n" + e.getMessage());
            }
        } catch (Exception e) {
            LoggerUtility.logger.warn("build session failed:\n" + e.getMessage());
        }
        return returnMovie;
    }

    /**
     * This method returns the movie count of a certain movie
     * defined by its movieID
     * @param id the id of the movie which count will be returned
     * @return the count of the movie (how many are rentable)
     */
    public static int getMovieCountByID(int id) {
        for(Movies movie:getFullMovieList()) {
            if (movie.getMovieid() == id) return movie.getCount();
        }
        return -1;
    }

    /**
     * This method increases the movie count of a certain movie
     * for example when it is returned to the store
     * @param id the movieID of the movie of which the count is increased
     * @return true if successful, false if unsuccessful
     */
    public static Boolean increaseMovieCountByID(int id) {
        int movieCount = getMovieCountByID(id);

        if (movieCount != -1) {

            try (SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
                 Session session = sessionFactory.openSession()) {
                Transaction transaction = null;
                try {
                    transaction = session.beginTransaction();

                    Query query = session.createQuery("UPDATE Movies SET count = :count WHERE movieid = :movieid");
                    query.setParameter("count", movieCount + 1);
                    query.setParameter("movieid", id);

                    query.executeUpdate();
                    transaction.commit();

                } catch (Exception e) {
                    if (transaction != null) transaction.rollback();
                    LoggerUtility.logger.warn("increasing movie count went wrong, could not transact:\n" + e.getMessage());
                    return false;
                }
            } catch (Exception e) {
                LoggerUtility.logger.warn("build session failed (increasing movie count):\n" + e.getMessage());
                return false;
            }
        } else {
            LoggerUtility.logger.warn("Movie not found; ID: " + id);
            return false;
        }
        return true;
    }

    /**
     * This method decreases the movie count of a certain movie
     * for example when it is rented
     * @param movie the movie of which the count is decreased
     * @return true if successful, false if unsuccessful
     */
    public static Boolean decreaseMovieCount(Movies movie) {
        int movieCount = movie.getCount();
        int movieID = movie.getMovieid();

        if (movieCount != -1 && movieCount > 0) {

            try (SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
                 Session session = sessionFactory.openSession()) {
                Transaction transaction = null;
                try {
                    transaction = session.beginTransaction();

                    Query query = session.createQuery("UPDATE Movies SET count = :count WHERE movieid = :movieid");
                    query.setParameter("count", movieCount - 1);
                    query.setParameter("movieid", movieID);

                    query.executeUpdate();
                    transaction.commit();

                } catch (Exception e) {
                    if (transaction != null) transaction.rollback();
                    LoggerUtility.logger.warn("increasing movie count went wrong, could not transact:\n" + e.getMessage());
                    return false;
                }
            } catch (Exception e) {
                LoggerUtility.logger.warn("build session failed (decreasing movie count):\n" + e.getMessage());
                return false;
            }
        } else {
            LoggerUtility.logger.warn("Movie not found or not enough copies available for rental: " + movie.getName());
            return false;
        }
        return true;
    }

}
