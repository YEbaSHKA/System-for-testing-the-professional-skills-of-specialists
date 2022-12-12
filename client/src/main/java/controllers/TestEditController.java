package controllers;

import java.io.IOException;

import org.client.UserState;

import org.testing_system.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import validation.AlertWindow;

public class TestEditController {

    private int id;

    @FXML
    private Button edit_button;

    @FXML
    private Button exit_button;

    @FXML
    private TextArea name_area;

    @FXML
    private ChoiceBox<String> topic_choice_box;

    @FXML
    private ChoiceBox<String> type_choice_box;

    private ObservableList<String> typeList = FXCollections.observableArrayList("Легкая", "Средняя", "Сложная");

    @FXML
    void edit_button_click(MouseEvent event) 
    {
        if (check_input_fields())
        {
            AlertWindow.showAlertWithNullInput();    
        }
        else
        {
            UserState.client.sendMessage("updateTest");
            
            Test test = new Test();

            test.setId(id);
            test.setName(name_area.getText());
            test.setTopic_name(topic_choice_box.getValue());
            test.setType(type_choice_box.getValue());

            UserState.client.sendObject(test);
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
    void exit_button_click(MouseEvent event) throws IOException {
        exit_button.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/testInformation.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Меню");
        stage.setScene(scene);
        stage.show();
    }

    public void set_value(Test test, ObservableList<String> observable_list)
    {
        name_area.setText(test.getName());

        topic_choice_box.setValue(test.getTopic_name());
        topic_choice_box.setItems(observable_list);

        type_choice_box.setValue(test.getType());
        type_choice_box.setItems(typeList);

        id = test.getId();
    }

    private boolean check_input_fields()
    {
        return name_area.getText().equals("");
    }

}