package controllers;

import java.io.IOException;

import org.client.UserState;
import org.testing_system.Employee;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import validation.AlertWindow;
import validation.Check;

public class EmployeeAddController
{

    @FXML
    private Button add_button;

    @FXML
    private Button close_button;

    @FXML
    private TextField first_name_field;

    @FXML
    private TextField last_name_field;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private TextField patronymic_field;

    @FXML
    void add_button_click(MouseEvent event) 
    {
        if (check_input_fields())
        {
            AlertWindow.showAlertWithNullInput();    
        }
        else
        {
            if (Check.checkLoginAndPass(login_field.getText(), password_field.getText())) 
            {
                if (Check.checkFullName(last_name_field.getText() + " " + first_name_field.getText() + " " + patronymic_field.getText())) 
                {
                    UserState.client.sendMessage("addEmployee");
                
                    Employee employee = new Employee();
                    employee.setLogin(login_field.getText());
                    employee.setPassword(password_field.getText());
                    employee.setFull_name(last_name_field.getText() + " " + first_name_field.getText() + " " + patronymic_field.getText());
                    
                    UserState.client.sendObject(employee);

                    if ((boolean) UserState.client.readObject()) 
                    {
                        AlertWindow.correctOperation();
                    } 
                    else 
                    {
                        AlertWindow.showAlertWithUncorrectData("Такой пользователь существует!");
                    }
                }
                else
                {
                    AlertWindow.showAlertWithUncorrectFullName();
                }
            }
            else
            {
                AlertWindow.showAlertWithUncorrectData();
            }
        }
    }

    @FXML
    void close_button_click(MouseEvent event) throws IOException 
    {
        close_button.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/employeeInfo.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Информация о пользователях");
        stage.setScene(scene);
        stage.show();
    }

    private boolean check_input_fields()
    {
        return last_name_field.getText().equals("") || first_name_field.getText().equals("") || patronymic_field.getText().equals("") || login_field.getText().equals("") || password_field.getText().equals("");
    }

}
