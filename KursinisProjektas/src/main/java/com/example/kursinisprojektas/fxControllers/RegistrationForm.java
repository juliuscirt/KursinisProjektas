package com.example.kursinisprojektas.fxControllers;

import com.example.kursinisprojektas.HelloApplication;
import com.example.kursinisprojektas.hibernateControllers.GenericHibernate;
import com.example.kursinisprojektas.model.*;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class RegistrationForm {
    public TextField userName;
    public TextField userSurname;
    public TextField userLogin;
    public TextField userPassword;
    public TextField customerDelivery;
    public TextField customerCard;
    public TextField customerBilling;
    public RadioButton isCustomer;
    public RadioButton isManager;
    public TextField adminCode;
    public ToggleGroup userType;
    public Text textLogin;
    public Text textName;
    public Text textPassword;
    public Text textSurname;
    public Text textCard;
    public Text textBilling;
    public Text textDelivery;
    public DatePicker customerDOB;
    public Text textDOB;
    public CheckBox isAdmin;
    private EntityManagerFactory entityManagerFactory;
    private GenericHibernate genericHibernate;

    public void registerUser() throws IOException {
        if (isCustomer.isSelected()) {
            if (userName.getText().isEmpty() ||
                    userSurname.getText().isEmpty() ||
                    userLogin.getText().isEmpty() ||
                    userPassword.getText().isEmpty() ||
                    customerCard.getText().isEmpty() ||
                    customerDOB.getValue() == null ||
                    customerBilling.getText().isEmpty() ||
                    customerDelivery.getText().isEmpty()) {
                FxUtils.generateAlert(Alert.AlertType.INFORMATION,"User error" ,"Submittion error", "Ooops, you seem to be missing information!");
            } else {
                User user = new Customer(
                        userName.getText(),
                        userSurname.getText(),
                        userLogin.getText(),
                        userPassword.getText(),
                        customerCard.getText(),
                        customerBilling.getText(),
                        customerDelivery.getText(),
                        customerDOB.getValue()
                );
                genericHibernate.create(user);
                returnToLogin();
            }
        }
        if (isManager.isSelected()) {
            if (Objects.equals(adminCode.getText(), "123")) {
                isAdmin.setSelected(true);
            }
            if (    userLogin.getText().isEmpty() ||
                    userPassword.getText().isEmpty() ||
                    userName.getText().isEmpty() ||
                    userSurname.getText().isEmpty()) {
                FxUtils.generateAlert(Alert.AlertType.INFORMATION,"User error" ,"Submittion error", "Ooops, you seem to be missing information!");
            } else {
                User user = new Manager(
                        userName.getText(),
                        userSurname.getText(),
                        userLogin.getText(),
                        userPassword.getText(),
                        isAdmin.isSelected()
                );

                genericHibernate.create(user);
                returnToLogin();
            }
        }
    }

    public void returnToLogin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-form.fxml"));
        Parent parent = fxmlLoader.load();
        Stage stage = (Stage) userLogin.getScene().getWindow();
        Scene scene = new Scene(parent);
        FxUtils.setStageParameters(stage, scene, false);
    }

    public void setData(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.genericHibernate = new GenericHibernate(entityManagerFactory);
    }

    public void disableFields() {
        customerCard.setVisible(false);
        customerBilling.setVisible(false);
        customerDelivery.setVisible(false);
        customerDOB.setVisible(false);
        adminCode.setVisible(false);
        textCard.setVisible(false);
        textBilling.setVisible(false);
        textDelivery.setVisible(false);
        textDOB.setVisible(false);
        if (isCustomer.isSelected()) {
            customerCard.setVisible(true);
            customerBilling.setVisible(true);
            customerDelivery.setVisible(true);
            customerDOB.setVisible(true);
            textCard.setVisible(true);
            textBilling.setVisible(true);
            textDelivery.setVisible(true);
            textDOB.setVisible(true);
        }
        if (isManager.isSelected()) {
            adminCode.setVisible(true);
        }
    }
}
