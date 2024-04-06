package com.filmverleih.filmverleih;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MovieEntryValidator {
    /**
     * Checks if the given string is not empty.
     *
     * @param string The string to be checked.
     * @return true if the string is not empty, otherwise false.
     */
    public static boolean notEmptyIsValid(String string) {
        return !string.isEmpty();
    }

    /**
     * Checks if the given year is valid (between 1920 and 2024, inclusive).
     *
     * @param year The year to be checked.
     * @return true if the year is valid, otherwise false.
     */
    public static boolean yearIsValid(int year) {
        return year >= 1920 && year <= 2024;
    }

    /**
     * Checks if the given FSK (Voluntary Self Regulation) is valid.
     *
     * @param fsk The FSK to be checked.
     * @return true if the FSK is valid, otherwise false.
     */
    public static boolean fskIsValid(int fsk) {
        return fsk == 0 || fsk == 6 || fsk == 12 || fsk == 16 || fsk == 18;
    }

    /**
     * Checks if the given rating is valid (in the format "x.x").
     *
     * @param rating The rating to be checked.
     * @return true if the rating is valid, otherwise false.
     */
    public static boolean ratingIsValid(String rating) {
        return rating.matches("^\\d\\.\\d$");
    }

    /**
     * Checks if the input fields for genre and director are valid.
     * For genre1 / director1
     *
     * @param string1 The first string (genre).
     * @param string2 The second string (director).
     * @param string3 The third string (optional director).
     * @return true if the input fields are valid, otherwise false.
     */
    public static boolean splitTextFieldIsValid(String string1, String string2, String string3) {
        return !(string1.isEmpty() && (!string2.isEmpty() || !string3.isEmpty()));
    }

    /**
     * Checks if the input fields for genre and director are valid.
     * For genre2 / director2
     *
     * @param string2 The first string (genre).
     * @param string3 The second string (director).
     * @return true if the input fields are valid, otherwise false.
     */
    public static boolean splitTextFieldIsValid(String string2, String string3) {
        return !(string2.isEmpty() && !string3.isEmpty());
    }

    /**
     * Checks if the input of actors (separated by commas) is valid.
     *
     * @param actors The input of actors to be checked.
     * @return true if the input is valid, otherwise false.
     */
    public static boolean listWithCommaIsValid(String actors) {
        return actors.matches("^(\\w+\\.?-?\\w*( \\w+\\.?-?\\w*)*(, \\w+\\.?-?\\w*( \\w+\\.?-?\\w*)*)*)?$");
    }

    /**
     * Checks if the link to the cover is valid.
     *
     * @param linkToCover The link to the cover to be checked.
     * @return true if the link is valid or empty, otherwise false.
     */
    public static boolean linkToCoverIsValid(String linkToCover) {
        try {
            if (!linkToCover.isEmpty() && linkToCover.matches("^(http://|https://)[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&/=]*((\\.jpg)|(\\.png)))$")) {
                Image image = ImageIO.read(new URL(linkToCover));
                return image != null;
            } else return linkToCover.isEmpty();
        } catch (MalformedURLException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }
}