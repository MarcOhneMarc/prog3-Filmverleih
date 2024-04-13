package com.filmverleih.filmverleih.utilitys;

import com.filmverleih.filmverleih.entity.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for Users
 */
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
                LoggerUtility.logger.warn("getFullUserList went wrong, could not transact:\n" + e.getMessage());
            }
        } catch (Exception e) {
            LoggerUtility.logger.warn("build session failed:\n" + e.getMessage());
        }
        return  new ArrayList<Users>();
    }


    /**
     * This method deletes a user from the database
     * @param user the user that will be deleted
     * @return true if the user was deleted
     */
    public static Boolean deleteUserInDB(Users user){
        try (SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();

                session.createQuery("DELETE FROM Users WHERE userid = " + user.getUserid()).executeUpdate();

                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                LoggerUtility.logger.warn("deleteUserInDB went wrong, could not transact:\n" + e.getMessage());
                return false;
            }
        } catch (Exception e) {
            LoggerUtility.logger.warn("build session failed:\n" + e.getMessage());
            return false;
        }
        LoggerUtility.logger.info("User deleted successfully: " + user.getUserid());
        return true;
    }

    /**
     * This method adds a new user to the database
     * @param user the user that will be added to the database
     * @return true if the user was added
     */
    public static Boolean addUserToDB(Users user) {
        try (SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.save(user);//add user to db
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                LoggerUtility.logger.warn("addUserToDB went wrong, could not transact:\n" + e.getMessage());
                return false;
            }
        } catch (Exception e) {
            LoggerUtility.logger.warn("build session failed:\n" + e.getMessage());
            return false;
        }
        LoggerUtility.logger.info("User added successfully: " + user.getUserid());
        return true;
    }

    /**
     * This method changes the password of a user
     * @param newPassword of the password
     * @param userId of the user
     * @return
     */
    public static Boolean UpdateUserPasswordInDB(String newPassword, int userId) {
        try (SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();

                session.createQuery("UPDATE Users SET password =" + "'" +newPassword+ "'" + " WHERE userid = " + userId).executeUpdate();

                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                LoggerUtility.logger.warn("updateUserPassword went wrong, could not transact:\n" + e.getMessage());
            }
        } catch (Exception e) {
            LoggerUtility.logger.warn("build session failed:\n" + e.getMessage());
        }
        LoggerUtility.logger.info("Password changed successfully...");
        return true;
    }
}
