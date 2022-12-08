package controllers;

import java.io.IOException;
import java.util.regex.*;

import org.client.Connection;
import org.testing_system.Authorization;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import validation.Dialog;

public class MainController {

    @FXML
    private Button admin_btn;

    @FXML
    private Button authorization_btn;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_fied;

    @FXML
    private Button registration_btn;

    @FXML
    void admin_btn_click(MouseEvent event) throws IOException {
        authorization_btn.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/adminEnter.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Вход администратора");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void authorization_click(MouseEvent event) throws IOException {
        if (check_fields()) 
        {
            Dialog.showAlertWithNullInput();
        }
        else 
        {
            Authorization auth = new Authorization();
            auth.setLogin(login_field.getText());
            auth.setPassword(password_fied.getText());
            System.out.println(auth.getLogin() + " " + auth.getPassword());
            
            Connection.client.sendMessage("authorizationEmployee");
            Connection.client.sendObject(auth);
            
            if((boolean) Connection.client.readObject())
            {
                authorization_btn.getScene().getWindow().hide();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/employeeMenu.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("Меню");
                stage.setScene(scene);
                stage.show();
            }
            else
            {
                Dialog.showAlertWithNoLogin();
            }
        }
        // if(auth.getLogin().matches("\\w+") && auth.getPassword().matches("\\w+") )
            // {
        // }
        // else
        // {
        //     Alert alert = new Alert(AlertType.ERROR);
		//     alert.setTitle("Некорректный ввод данных!");

		//     // Header Text: null
		//     alert.setHeaderText(null);
		//     alert.setContentText("Допустимые символы: A-Z, a-z, 0-9, _");

		//     alert.showAndWait();
        // }
        

    }

    @FXML
    void registration_click(MouseEvent event) throws IOException {
        authorization_btn.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/registration.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Регистрация");
        stage.setScene(scene);
        stage.show();
    }

    private boolean check_fields()
    {
        try {
            return login_field.getText().equals("") || password_fied.getText().equals("");
        } catch (Exception e) {
            System.out.println("Empty fields");
            return true;
        }
    }

}