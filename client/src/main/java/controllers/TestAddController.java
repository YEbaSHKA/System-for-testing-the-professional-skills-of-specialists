package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.client.UserState;
import org.testing_system.Question;
import org.testing_system.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import validation.AlertWindow;

public class TestAddController implements Initializable
{

    private String topic;

    @FXML
    private Button add_button;

    @FXML
    private Button exit_button;

    @FXML
    private TextArea name_area;

    @FXML
    private ChoiceBox<String> type_choice_box;

    private ObservableList<String> typeList = FXCollections.observableArrayList("Легкая", "Средняя", "Сложная");

    @FXML
    void add_button_click(MouseEvent event) 
    {
        if (check_input_fields())
        {
            AlertWindow.showAlertWithNullInput();    
        }
        else
        {
            UserState.client.sendMessage("insertTest");
            Test test = new Test();

            test.setName(name_area.getText());
            test.setTopic_name(topic);
            test.setType(type_choice_box.getValue());

            UserState.client.sendObject(test);

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
                AlertWindow.showAlertWithUncorrectData("Название такого теста уже существует!");
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
        return name_area.getText().equals("");
    }

    public void set_value(String topic_value)
    {
        topic = topic_value;
        type_choice_box.setValue(typeList.get(0));
        type_choice_box.setItems(typeList);
    }
}