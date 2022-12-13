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

public class EmployeeEditController 
{
    private int id;

    @FXML
    private Button close_button;

    @FXML
    private Button edit_button;

    @FXML
    private TextField first_name_field;

    @FXML
    private TextField last_name_field;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField new_password_field;

    @FXML
    private TextField old_password_field;

    @FXML
    private TextField patronymic_field;

    @FXML
    void close_button_click(MouseEvent event) throws IOException 
    {
        close_button.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/employeeInfo.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Информация о сотрудниках");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void edit_button_click(MouseEvent event) 
    {
        if(!check_input_fields())
        {
            if (Check.checkFullName(last_name_field.getText() + " " + first_name_field.getText() + " " + patronymic_field.getText()))
            {
                if (Check.checkLoginAndPass(login_field.getText(), new_password_field.getText())) 
                {
                    Employee employee = new Employee();
                    employee.setId(id);
                    employee.setLogin(login_field.getText());
                    employee.setPassword(new_password_field.getText());
                    employee.setFull_name(last_name_field.getText() + " " + first_name_field.getText() + " " + patronymic_field.getText());

                    UserState.client.sendMessage("editEmployee");
                    UserState.client.sendObject(employee);

                    if ((boolean) UserState.client.readObject())
                    {
                        AlertWindow.correctOperation();    
                    }
                    else
                    {
                        AlertWindow.showAlertWithServerTrouble();
                    }   
                }
                else
                {
                    AlertWindow.showAlertWithUncorrectData();
                } 

            }
            else
            {
                AlertWindow.showAlertWithUncorrectFullName();
            }
        }
        else
        {
            AlertWindow.showAlertWithNullInput();
        }
    }

    public void set_value(Employee employee)
    {
        String[] full_name = employee.getFull_name().split(" ");
        id = employee.getId();
        last_name_field.setText(full_name[0]);
        first_name_field.setText(full_name[1]);
        patronymic_field.setText(full_name[2]);
        login_field.setText(employee.getLogin());
        old_password_field.setText(employee.getPassword());
    }

    private boolean check_input_fields()
    {
        return last_name_field.getText().equals("") || first_name_field.getText().equals("") || patronymic_field.getText().equals("") || old_password_field.getText().equals("") || new_password_field.getText().equals(""); 
    }
}
