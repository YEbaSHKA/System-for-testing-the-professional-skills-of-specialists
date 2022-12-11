package controllers;

import java.io.IOException;

import org.client.Client;
import org.client.Connection;
import org.testing_system.Employee;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import validation.Check;
import validation.Dialog;

public class RegistrationController {
    @FXML
    private Button registration_btn;

    @FXML
    private Button back_to_main_btn;

    @FXML
    private TextField full_name_field;

    @FXML
    private TextField login_reg_field;

    @FXML
    private PasswordField password_reg_fied;

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

    @FXML
    void registration_click(MouseEvent event) throws IOException {
        if (check_fields()) 
        {
            Dialog.showAlertWithNullInput();
        }
        else
        {
            if (Check.checkLoginAndPass(login_reg_field.getText(), password_reg_fied.getText())) 
            {
                if (Check.checkFullName(full_name_field.getText())) 
                {
                    Connection.client.sendMessage("registrationEmployee");
                    Employee employee = new Employee();
                    employee.setFull_name(full_name_field.getText());
                    employee.setLogin(login_reg_field.getText());
                    employee.setPassword(password_reg_fied.getText());
                    Connection.client.sendObject(employee);
                    if ((boolean) Connection.client.readObject()) 
                    {
                        Dialog.showAlertWithExistLogin();
                    }
                    else 
                    {
                        if ((boolean) Connection.client.readObject())
                        {
                            employee = (Employee) Connection.client.readObject();
                            Dialog.correctRegistration();
                            back_to_main_btn.getScene().getWindow().hide();
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/employeeMenu.fxml"));
                            Scene scene = new Scene(fxmlLoader.load());
                            Stage stage = new Stage();
                            stage.setTitle("Меню");
                            stage.setScene(scene);
                            Connection.id = employee.getId();
                            stage.show();
                        } 
                        else 
                        {
                            Dialog.showAlertWithExistLogin();
                        }
                    }
                }
                else 
                {
                    Dialog.showAlertWithUncorrectFullName();
                }
            }
            else 
            {
                Dialog.showAlertWithUncorrectData();
            }
        }
    }

    private boolean check_fields()
    {
        try {
            return full_name_field.getText().equals("") || login_reg_field.getText().equals("") || password_reg_fied.getText().equals("");
        } catch (Exception e) {
            System.out.println("Empty fields");
            return true;
        }
    }
}
