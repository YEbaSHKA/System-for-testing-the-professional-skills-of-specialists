package controllers;

import java.io.IOException;

import org.client.UserState;
import org.testing_system.Topic;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import validation.AlertWindow;

public class TopicEditController {
    private String topic_name;

    @FXML
    private Button edit_button;

    @FXML
    private Button exit_button;

    @FXML
    private TextField name_field;

    @FXML
    void edit_button_click(MouseEvent event) {
        if (check_input())
        {
            AlertWindow.showAlertWithNullInput();    
        }
        else
        {
            UserState.client.sendMessage("updateTopic");
            
            Topic topic = new Topic();

            topic.setName(topic_name);

            UserState.client.sendObject(topic);
            UserState.client.sendObject(name_field.getText());
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

    public void set_value(String name)
    {
        topic_name = name;
        name_field.setText(topic_name);
    }

    public boolean check_input()
    {
        return name_field.getText().equals("");
    }
}