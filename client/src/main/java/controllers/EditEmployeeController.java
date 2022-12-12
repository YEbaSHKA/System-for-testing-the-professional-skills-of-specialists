package controllers;

import java.io.IOException;
import java.lang.ModuleLayer.Controller;
import java.net.URL;
import java.util.ResourceBundle;

import org.client.UserState;
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
import validation.AlertWindow;

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
                AlertWindow.showAlertWithUncorrectData("Введите оба поля с паролем!");
            }
            else if(check_input == "nopassF")
            {
                AlertWindow.showAlertWithUncorrectFullName();
            }
            else if (check_input == "F")
            {
                AlertWindow.showAlertWithNullInput();
            }
            else if (check_input == "nopassT") 
            {
                Employee employee = new Employee();
                employee.setId(UserState.current_user_id);
                employee.setLogin(login_field.getText());
                employee.setPassword(old_password_field.getText());
                employee.setFull_name(last_name_field.getText() + " " + first_name_field.getText() + " " + patronymic_field.getText());

                UserState.client.sendMessage("updateEmployeeWitinPass");
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
                if (Check.checkLoginAndPass(login_field.getText(), new_password_field.getText())) 
                {
                    Employee employee = new Employee();
                    employee.setId(UserState.current_user_id);
                    employee.setLogin(login_field.getText());
                    employee.setPassword(old_password_field.getText());
                    employee.setFull_name(last_name_field.getText() + " " + first_name_field.getText() + " " + patronymic_field.getText());

                    UserState.client.sendMessage("updateEmployee");
                    UserState.client.sendObject(employee);
                    if ((boolean) UserState.client.readObject())
                    {
                        UserState.client.sendMessage(new_password_field.getText());

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
                        AlertWindow.showAlertWithUncorrectData("Неправтльный старый пароль!");
                    }
                }
                else
                {
                    AlertWindow.showAlertWithUncorrectData();
                } 
            }
                       
        }
        else
        {
            AlertWindow.showAlertWithUncorrectFullName();
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