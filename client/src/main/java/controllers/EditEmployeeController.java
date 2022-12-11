package controllers;

import java.io.IOException;
import java.lang.ModuleLayer.Controller;
import java.net.URL;
import java.util.ResourceBundle;

import org.client.Client;
import org.client.Connection;
import org.testing_system.Employee;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import validation.Check;
import validation.Dialog;

public class EditEmployeeController implements Initializable
{

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
    private PasswordField old_password_field;

    @FXML
    private TextField patronymic_field;

    @FXML
    void close_button_click(MouseEvent event) throws IOException
    {
        close_button.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/employeeMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Меню");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void edit_button_click(MouseEvent event)
    {
        if (Check.checkFullName(last_name_field.getText() + " " + first_name_field.getText() + " " + patronymic_field.getText()))
        {
            String check_input = checkCorrectInput();
            if( check_input == "1of2")
            {
                Dialog.showAlertWithUncorrectData("Введите оба поля с паролем!");
            }
            else if(check_input == "nopassF")
            {
                Dialog.showAlertWithUncorrectFullName();
            }
            else if (check_input == "F")
            {
                Dialog.showAlertWithNullInput();
            }
            else if (check_input == "nopassT") 
            {
                Employee employee = new Employee();
                employee.setId(Connection.id);
                employee.setLogin(login_field.getText());
                employee.setPassword(old_password_field.getText());
                employee.setFull_name(last_name_field.getText() + " " + first_name_field.getText() + " " + patronymic_field.getText());

                Connection.client.sendMessage("updateEmployeeWitinPass");
                Connection.client.sendObject(employee);

                if ((boolean) Connection.client.readObject())
                {
                    Dialog.correctOperation();    
                }
                else
                {
                    Dialog.showAlertWithServerTrouble();
                }
            }
            else
            {
                if (Check.checkLoginAndPass(login_field.getText(), new_password_field.getText())) 
                {
                    Employee employee = new Employee();
                    employee.setId(Connection.id);
                    employee.setLogin(login_field.getText());
                    employee.setPassword(old_password_field.getText());
                    employee.setFull_name(last_name_field.getText() + " " + first_name_field.getText() + " " + patronymic_field.getText());

                    Connection.client.sendMessage("updateEmployee");
                    Connection.client.sendObject(employee);
                    if ((boolean) Connection.client.readObject())
                    {
                        Connection.client.sendMessage(new_password_field.getText());

                        if ((boolean) Connection.client.readObject())
                        {
                            Dialog.correctOperation();    
                        }
                        else
                        {
                            Dialog.showAlertWithServerTrouble();
                        }   
                    }
                    else
                    {
                        Dialog.showAlertWithUncorrectData("Неправтльный старый пароль!");
                    }
                }
                else
                {
                    Dialog.showAlertWithUncorrectData();
                } 
            }
                       
        }
        else
        {
            Dialog.showAlertWithUncorrectFullName();
        }
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public void setValue(String firstname, String lastname, String patronymic, String login)
    {
        first_name_field.setText(firstname);
        last_name_field.setText(lastname);
        patronymic_field.setText(patronymic);
        login_field.setText(login);
    }

    private String checkCorrectInput()
    {
        if(new_password_field.getText().equals("") && old_password_field.getText().equals(""))
        {
            boolean check = first_name_field.getText().equals("") || last_name_field.getText().equals("") || patronymic_field.getText().equals("");
            if (check)
            {
                return "nopassF";    
            }
            else
                return "nopassT";
        }
        else if(new_password_field.getText().equals("") || old_password_field.getText().equals(""))
        {
            return "1of2";
        }
        else
        {
            boolean check = first_name_field.getText().equals("") || last_name_field.getText().equals("") || patronymic_field.getText().equals("");
            if (check)
            {
                return "F";    
            }
            else
                return "T";
        }
    }
}