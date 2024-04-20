package com.example.kursinisprojektas.fxControllers;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FxUtils {
    public static void setStageParameters(Stage stage, Scene scene, boolean modal) {
        stage.setScene(scene);
        if (modal) {
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } else {
            stage.show();
        }
    }
    public static void generateAlert(Alert.AlertType alertType,String title , String header, String text){
        //See here, you can just copy paste - https://code.makery.ch/blog/javafx-dialogs-official/
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();
    }
}
