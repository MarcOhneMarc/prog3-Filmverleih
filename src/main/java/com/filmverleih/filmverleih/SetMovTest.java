package com.filmverleih.filmverleih;
import com.filmverleih.filmverleih.entity.Movies;
import com.filmverleih.filmverleih.entity.Rentals;
import com.filmverleih.filmverleih.utilitys.LoggerUtility;
import com.filmverleih.filmverleih.utilitys.RentalsUtility;
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

        try (SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();

                Rentals rental = new Rentals();
                rental.setMovieid(20);
                rental.setCustomerid(1);
                rental.setStartdate("20-02-2024");
                rental.setEnddate("25-02-2024");

                // add rental to db
                session.save(rental);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                LoggerUtility.logger.warn("addRentalToDB went wrong, could not transact: 011");
            }
        } catch (Exception e) {
            LoggerUtility.logger.warn("build session failed: 012");
        }

    }

}