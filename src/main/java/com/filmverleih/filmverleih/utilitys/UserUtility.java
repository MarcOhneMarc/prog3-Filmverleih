package com.filmverleih.filmverleih.utilitys;

import com.filmverleih.filmverleih.entity.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class UserUtility {

    /**
     * This method provides a List of all User from the table Users
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
                LoggerUtility.logger.warn("getFullUserList went wrong, could not transact");
            }
        } catch (Exception e) {
            LoggerUtility.logger.warn("build session failed");
        }
        return  new ArrayList<Users>();
    }

}
