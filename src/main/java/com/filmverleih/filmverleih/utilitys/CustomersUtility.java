package com.filmverleih.filmverleih.utilitys;

import com.filmverleih.filmverleih.entity.Customers;
import com.filmverleih.filmverleih.entity.Movies;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class CustomersUtility {
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
     * This method gets a customer by its id
     * @param id the id of the customer
     * @return the customer with the id
     */
    public static Customers getCustomerById(int id)
    {
        Customers ret = new Customers();
        for(Customers customer:getFullCustomerList())
        {
            if (customer.getCustomerid() == id) ret = customer;
        }
        return ret;
    }
}
