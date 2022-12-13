package controllers;

import java.io.IOException;
import java.util.regex.*;

import org.client.UserState;
// import org.testing_system.Authorization;
import org.testing_system.Employee;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import validation.AlertWindow;

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
            AlertWindow.showAlertWithNullInput();
        }
        else 
        {
            Employee employee = new Employee();
            employee.setLogin(login_field.getText());
            employee.setPassword(password_fied.getText());
            System.out.println(employee.getLogin() + " " + employee.getPassword());
            
            UserState.client.sendMessage("authorizationEmployee");
            UserState.client.sendObject(employee);
            
            if((boolean) UserState.client.readObject())
            {
                employee = (Employee) UserState.client.readObject();
                UserState.current_user_id = employee.getId();
                
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/employeeMenu.fxml"));
                Parent root = fxmlLoader.load();
                EmployeeMenuController controller = fxmlLoader.getController();
                Scene scene;
                scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Меню");
                stage.setScene(scene);
                
                if(controller.check_mandatory_tests())
                {   
                    controller.mandatory_test_button.setVisible(false);
                }
                else
                {
                    AlertWindow.mandatoryTestAvailable();
                    controller.mandatory_test_button.setVisible(true);
                }
                authorization_btn.getScene().getWindow().hide();
                stage.show();
            }
            else
            {
                AlertWindow.showAlertWithNoLogin();
            }
        }

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