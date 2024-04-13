package com.filmverleih.filmverleih.utilitys;

import com.filmverleih.filmverleih.entity.Customers;
import com.filmverleih.filmverleih.entity.Movies;
import com.filmverleih.filmverleih.entity.Rentals;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for Rentals
 */
public class RentalsUtility {
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
                LoggerUtility.logger.warn("addRentalToDB went wrong, could not transact:\n" + e.getMessage());
                return false;
            }
        } catch (Exception e) {
            LoggerUtility.logger.warn("build session failed:\n" + e.getMessage());
            return false;
        }
        LoggerUtility.logger.info("Rental added successfully: " + movieID + ", " + customerID);
        return true;
    }

    /**
     * Utility method to retrieve a rental from the database by its ID.
     * @param customerId The ID of the customer to retrieve.
     * @param movieId The ID of the movie to retrieve.
     * @return The rental object corresponding to the given ID, or null if no such movie is found.
     */
    public static Rentals getRentalById(int customerId, int movieId) {
        Rentals returnRental = null;
        try (SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();

                returnRental = session.createQuery("FROM Rentals WHERE customerid =" + customerId + "AND movieid =" + movieId, Rentals.class).getSingleResult();

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
        return returnRental;
    }

    /**
     * This method returns all rentals from db
     * @return list of all rentals
     */
    public static List<Rentals> getAllRentedMovies() {
        try (SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                List<Rentals> rentals = session.createQuery("FROM Rentals" , Rentals.class).getResultList();
                for (Rentals rental : rentals) {
                    LoggerUtility.logger.info(rental.getMovieid() + " " + rental.getCustomerid() + " " + rental.getStartdate() + " " + rental.getEnddate());
                }
                transaction.commit();
                return rentals;
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                LoggerUtility.logger.warn("getAllRentedMovies went wrong, could not transact:\n" + e.getMessage());
            }
        } catch (Exception e) {
            LoggerUtility.logger.warn("build session failed:\n" + e.getMessage());
        }
        return new ArrayList<Rentals>();
    }



    /**
     * This method returns the last added customer ID from the
     * customer table by getting the id of the last element of the
     * fullCustomersList();
     * @return the last added customerID
     */
    public static int getLastAddedCustomerID() {
        List<Customers> customersList = CustomersUtility.getFullCustomerList();
        if (customersList.isEmpty()) {
            return 1;
        }
        return customersList.getLast().getCustomerid();
    }


    /**
     * This method deletes a rental from db
     * @param movieid the id of the movie
     * @param customerid the id of the customer
     * @return true if deleting was successful, false if not
     */
    public static boolean deleteRentalFromDB(int movieid, int customerid) {
        try (SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.createQuery("DELETE FROM Rentals WHERE movieid = " + movieid + " AND customerid = " + customerid).executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                LoggerUtility.logger.warn("deleteRentalFromDB went wrong, could not transact:\n" + e.getMessage());
                return false;
            }
        } catch (Exception e) {
            LoggerUtility.logger.warn("build session failed:\n" + e.getMessage());
            return false;
        }
        LoggerUtility.logger.info("Rental deleted successfully: " + movieid + ", " + customerid);
        return true;
    }


    /**
     * This method extends the rental
     * @param movieid the id of the movie
     * @param customerid the id of the customer
     * @param enddate the end date of the rental
     * @return true if extending was successful, false if not
     */
    public static boolean extendRentalinDB(int movieid, int customerid,  String enddate) {
        try (SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.createQuery("UPDATE Rentals SET enddate = '" + enddate + "' WHERE movieid = " + movieid + " AND customerid = " + customerid).executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                LoggerUtility.logger.warn("extendRentalInDB went wrong, could not transact:\n" + e.getMessage());
                return false;
            }
        } catch (Exception e) {
            LoggerUtility.logger.warn("build session failed:\n" + e.getMessage());
            return false;
        }
        LoggerUtility.logger.info("Rental extended successfully: " + movieid + ", " + customerid);
        return true;
    }
}
