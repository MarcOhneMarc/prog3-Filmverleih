package com.filmverleih.filmverleih.utilitys;

import com.filmverleih.filmverleih.MainApplication;
import javafx.fxml.FXMLLoader;

/**
 * This class provides methods to load FXML files
 */
public class FXMLUtility {

    /**
     * This method loads a FXML file
     * @param str the name of the FXML
     * @return the FXMLLoader
     */
    public static FXMLLoader loadFXML(String str)
    {       FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApplication.class.getResource(str));
        return loader;
    }

}
