package com.filmverleih.filmverleih.utilitys;

import com.filmverleih.filmverleih.MainApplication;
import javafx.fxml.FXMLLoader;

public class FXMLUtility {

    public static FXMLLoader loadFXML(String str)
    {       FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApplication.class.getResource(str));
        return loader;
    }

}
