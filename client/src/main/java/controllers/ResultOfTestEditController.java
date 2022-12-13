package controllers;

import java.io.IOException;

import org.client.UserState;
import org.testing_system.Employee;
import org.testing_system.ResultOfTest;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import validation.AlertWindow;

public class ResultOfTestEditController {

    @FXML
    private Button edit_button;

    @FXML
    private Label employee_login_label;

    @FXML
    private Button exit_button;

    @FXML
    private Spinner<Integer> result_spinner;

    @FXML
    private Label test_name_label;

    @FXML
    void edit_button_click(MouseEvent event) 
    {
        UserState.client.sendMessage("updateTestEmployees");
        String login = employee_login_label.getText();
        String test_name = test_name_label.getText();
        int result = result_spinner.getValue();
        UserState.client.sendMessage(login);
        UserState.client.sendMessage(test_name);
        UserState.client.sendObject(result);

        if ((boolean) UserState.client.readObject()) 
        {
            AlertWindow.correctOperation();    
        }
        else 
        {
            AlertWindow.showAlertWithServerTrouble();
        }
    }

    @FXML
    void exit_button_click(MouseEvent event) throws IOException 
    {
        exit_button.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/employeeInfo.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Меню");
        stage.setScene(scene);
        stage.show();
    }

    public void set_value(ResultOfTest resultOfTest, String login)
    {
        UserState.client.sendMessage("getCountOfQuestions");
        UserState.client.sendMessage(resultOfTest.getTest_name());

        int count_of_question = (int) UserState.client.readObject();
        System.out.println(count_of_question);
        employee_login_label.setText(login);
        test_name_label.setText(resultOfTest.getTest_name());
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, count_of_question);
        valueFactory.setValue(resultOfTest.getResult_of_test());
        result_spinner.setValueFactory(valueFactory);
    }

}
