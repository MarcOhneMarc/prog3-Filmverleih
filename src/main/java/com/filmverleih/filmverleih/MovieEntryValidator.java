package com.filmverleih.filmverleih;

public class MovieEntryValidator {
    public static boolean notEmptyIsValid(String string) {
        return !string.isEmpty();
    }

    public static boolean yearIsValid(int year) {
        return year >= 1920 && year <= 2024;
    }

    public static boolean fskIsValid(int fsk) {
        return fsk == 0 || fsk == 6 || fsk == 12 || fsk == 16 || fsk == 18;
    }

    public static boolean ratingIsValid(String rating) {
        return rating.matches("^\\d\\.\\d$");
    }

    //For genre1 / director1
    public static boolean splitTextFieldIsValid(String string1, String string2, String string3) {
        return !(string1.isEmpty() && (!string2.isEmpty() || !string3.isEmpty()));
    }

    //For genre2 / director2
    public static boolean splitTextFieldIsValid(String string2, String string3) {
        return !(string2.isEmpty() && !string3.isEmpty());
    }

    /*public static boolean director1IsValid(String changedDirectors, String directors1, String directors2, String directors3) {
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
    }*/

    public static boolean listWithCommaIsValid(String actors) {
        return actors.matches("^(\\w+\\.?\\w*( \\w+\\.?\\w*)*(, \\w+\\.?\\w*( \\w+\\.?\\w*)*)*)?$");
    }

    public static boolean linkToCoverIsValid(String linkToCover) {
        return linkToCover.isEmpty() || linkToCover.matches("^(http://|https://)[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&/=]*((\\.jpg)|(\\.png)))$");
    }
}