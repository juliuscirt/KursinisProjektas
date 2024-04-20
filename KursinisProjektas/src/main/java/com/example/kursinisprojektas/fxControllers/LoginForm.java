package com.example.kursinisprojektas.fxControllers;

import com.example.kursinisprojektas.HelloApplication;
import com.example.kursinisprojektas.hibernateControllers.ShopHibernate;
import com.example.kursinisprojektas.model.User;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginForm implements Initializable {
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;
    private EntityManagerFactory entityManagerFactory;

    public void validateAndLoadMain() throws IOException {
        ShopHibernate shopHibernate = new ShopHibernate(entityManagerFactory);
        var user = shopHibernate.getUserByCredentials(loginField.getText(), passwordField.getText());
        if(user != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-window.fxml"));
            Parent parent = fxmlLoader.load();
            MainWindow mainWindow = fxmlLoader.getController();
            mainWindow.setData(entityManagerFactory, (User) user);
            Stage stage = (Stage) loginField.getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setTitle("Gamer Store");
            stage.setScene(scene);
            stage.show();
        }
        else{
            FxUtils.generateAlert(Alert.AlertType.INFORMATION, "User error","Login error", "No such user or wrong credentials");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        entityManagerFactory = Persistence.createEntityManagerFactory("ShopSystem");
    }

    public void registerNewUser() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("registration-form.fxml"));
        Parent parent = fxmlLoader.load();
        //Access controller of main window. Each form has its own controller, so make sure that you make no mistake here
        RegistrationForm registrationForm = fxmlLoader.getController();
        registrationForm.setData(entityManagerFactory);
        Stage stage = (Stage) loginField.getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setTitle("Registrations");
        FxUtils.setStageParameters(stage, scene, false);
    }
}
