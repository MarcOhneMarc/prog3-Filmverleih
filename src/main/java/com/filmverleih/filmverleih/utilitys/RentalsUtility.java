package com.filmverleih.filmverleih.utilitys;

import com.filmverleih.filmverleih.entity.Customers;
import com.filmverleih.filmverleih.entity.Movies;
import com.filmverleih.filmverleih.entity.Rentals;
import com.filmverleih.filmverleih.utilitys.CustomersUtility;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

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
        List<Customers> customersList = CustomersUtility.getFullCustomerList();
        if (customersList.isEmpty()) {
            return 1;
        }
        return customersList.getLast().getCustomerid();
    }
}
