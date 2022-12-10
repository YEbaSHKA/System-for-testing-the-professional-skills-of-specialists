package controllers;

import java.io.IOException;

import org.client.Connection;
import org.testing_system.Admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import validation.Dialog;

public class AdminEnterController {

    @FXML
    private TextField admin_login_field;

    @FXML
    private PasswordField admin_password_fied;

    @FXML
    private Button admin_authorization_btn;

    @FXML
    private Button back_to_main_btn;

    @FXML
    void admin_authorization_click(MouseEvent event) throws IOException {
        if (check_fields()) 
        {
            Dialog.showAlertWithNullInput();
        }
        else 
        {
            Admin admin = new Admin();
            admin.setLogin(admin_login_field.getText());
            admin.setPassword(admin_password_fied.getText());
            System.out.println(admin.getLogin() + " " + admin.getPassword());
            
            Connection.client.sendMessage("authorizationAdmin");
            Connection.client.sendObject(admin);
            
            if((boolean) Connection.client.readObject())
            {
                admin = (Admin) Connection.client.readObject();
                Connection.id = admin.getId();
                
                admin_authorization_btn.getScene().getWindow().hide();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/adminMenu.fxml"));
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
    }

    @FXML
    void back_to_main_click(MouseEvent event) throws IOException {
        back_to_main_btn.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Авторизация");
        stage.setScene(scene);
        stage.show();
    }

    private boolean check_fields()
    {
        try {
            return admin_login_field.getText().equals("") || admin_password_fied.getText().equals("");
        } catch (Exception e) {
            System.out.println("Empty fields");
            return true;
        }
    }
}