package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.client.UserState;
import org.testing_system.Question;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import validation.AlertWindow;

public class QuestionAddController implements Initializable
{

    private int id_test;

    @FXML
    private Spinner<Integer> correct_spinner;

    @FXML
    private Button add_button;

    @FXML
    private Button exit_button;

    @FXML
    private TextArea first_area;

    @FXML
    private TextArea name_area;

    @FXML
    private TextArea second_area;

    @FXML
    private TextArea third_area;

    @FXML
    void add_button_click(MouseEvent event) 
    {
        if (check_input_fields())
        {
            AlertWindow.showAlertWithNullInput();    
        }
        else
        {
            UserState.client.sendMessage("insertQuestion");
            Question question = new Question();

            question.setName(name_area.getText());
            question.setFirst_answer(first_area.getText());
            question.setSecond_answer(second_area.getText());
            question.setThird_answer(third_area.getText());
            question.setCorrect_answer_number(correct_spinner.getValue());
            question.setId_test(id_test);

            UserState.client.sendObject(question);

            if ((boolean) UserState.client.readObject()) 
            {
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
                AlertWindow.showAlertWithUncorrectData("Название такого вопроса уже существует!");
            }
        }
    }

    @FXML
    void exit_button_click(MouseEvent event) throws IOException 
    {
        exit_button.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/testInformation.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Меню");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private boolean check_input_fields()
    {
        return name_area.getText().equals("") || first_area.getText().equals("") || second_area.getText().equals("") || third_area.getText().equals("");
    }

    public void set_value(int id)
    {
        id_test = id;
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 3);
        valueFactory.setValue(1);
        correct_spinner.setValueFactory(valueFactory);
    }
}