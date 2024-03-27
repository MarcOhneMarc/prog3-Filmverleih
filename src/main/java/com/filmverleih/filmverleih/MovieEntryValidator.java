package com.filmverleih.filmverleih;

public class MovieEntryValidator {
    public static boolean nameIsValid(String name) {
        if (name != null) {
            return !name.isEmpty();
        }
        return false;
    }

    public static boolean yearIsValid(int year) {
        return year >= 1920 && year <= 2024;
    }

    public static boolean fskIsValid(int fsk) {
        return fsk == 0 || fsk == 6 || fsk == 12 || fsk == 16 || fsk == 18;
    }

    public static boolean ratingIsValid(String rating) {
        if (rating != null) {
            return rating.matches("^\\d\\.\\d$");
        }
        return false;
    }

    public static boolean genre1IsValid(String changedGenres, String genre1, String genre2, String genre3) {
        if (changedGenres != null) {
            return !(genre1.isEmpty() && (!genre2.isEmpty() || !genre3.isEmpty()));
        }
        return true;
    }

    public static boolean genre2IsValid(String changedGenres, String genre2, String genre3) {
        if (changedGenres != null) {
            return !(genre2.isEmpty() && !genre3.isEmpty());
        }
        return true;
    }

    public static boolean director1IsValid(String changedDirectors, String directors1, String directors2, String directors3) {
        if (changedDirectors != null) {
            return !(directors1.isEmpty() && (!directors2.isEmpty() || !directors3.isEmpty()));
        }
        return true;
    }

    public static boolean director2IsValid(String changedDirectors, String directors2, String directors3) {
        if (changedDirectors != null) {
            return !(directors2.isEmpty() && !directors3.isEmpty());
        }
        return true;
    }

    public static boolean actorsIsValid(String actors) {
        if (actors != null) {
            return actors.matches("^(\\w+\\.?\\w*( \\w+\\.?\\w*)*(, \\w+\\.?\\w*( \\w+\\.?\\w*)*)*)?$");
        }
        return true;
    }

    public static boolean linkToCoverIsValid(String linkToCover) {
        if (linkToCover != null) {
            return linkToCover.isEmpty() || linkToCover.matches("^(http://|https://)[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&/=]*((\\.jpg)|(\\.png)))$");
        }
        return true;
    }
}