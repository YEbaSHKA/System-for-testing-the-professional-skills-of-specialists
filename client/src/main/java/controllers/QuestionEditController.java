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

public class QuestionEditController implements Initializable
{
    private Stage stage;

    private int id;

    @FXML
    private Spinner<Integer> correct_spinner;

    @FXML
    private Button edit_button;

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
    void edit_button_click(MouseEvent event) 
    {
        if (check_input_fields())
        {
            AlertWindow.showAlertWithNullInput();    
        }
        else
        {
            UserState.client.sendMessage("updateQuestion");
            Question question = new Question();

            question.setId(id);
            question.setName(name_area.getText());
            question.setFirst_answer(first_area.getText());
            question.setSecond_answer(second_area.getText());
            question.setThird_answer(third_area.getText());
            question.setCorrect_answer_number(correct_spinner.getValue());

            UserState.client.sendObject(question);

            if ((boolean) UserState.client.readObject()) 
            {
                AlertWindow.correctOperation();
            }
            else
            {
                AlertWindow.showAlertWithServerTrouble();
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

    public void set_stage(Stage stage)
    {
        this.stage = stage;
    }

    public void set_value(Question question)
    {
        id = question.getId();
        name_area.setText(question.getName());
        first_area.setText(question.getFirst_answer());
        second_area.setText(question.getSecond_answer());
        third_area.setText(question.getThird_answer());
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 3);
        valueFactory.setValue(question.getCorrect_answer_number());
        correct_spinner.setValueFactory(valueFactory);
    }

    private boolean check_input_fields()
    {
        return name_area.getText().equals("") || first_area.getText().equals("") || second_area.getText().equals("") || third_area.getText().equals("");
    }
}