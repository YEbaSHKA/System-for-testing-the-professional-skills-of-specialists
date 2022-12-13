package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.client.UserState;
import org.testing_system.Employee;
import org.testing_system.Test;
import org.testing_system.Topic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import validation.AlertWindow;

public class MandatoryTestController implements Initializable 
{
    private int id;

    @FXML
    private Button appoint_button;

    @FXML
    private Button exit_button;

    @FXML
    private Label login_label;

    @FXML
    private ListView<String> tests_view;

    @FXML
    private ChoiceBox<String> topic_choice_box;

    @FXML
    private ChoiceBox<String> type_choice_box;

    private ObservableList<String> topicList = FXCollections.observableArrayList();

    private ObservableList<String> typeList = FXCollections.observableArrayList("Легкая", "Средняя", "Сложная");

    private ObservableList<String> testList = FXCollections.observableArrayList();

    @FXML
    void appoint_button_click(MouseEvent event) 
    {
        if (tests_view.getSelectionModel().getSelectedIndex() == -1)
        {
            AlertWindow.showAlertWithUncorrectData("Нет доступных тестов, добавьте тест!");    
        } 
        else 
        {
            UserState.client.sendMessage("appointMandatoryTest");
            
            String test_name = tests_view.getSelectionModel().getSelectedItem();
            
            UserState.client.sendMessage(test_name);
            UserState.client.sendObject(id);

            if((boolean) UserState.client.readObject())
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/employeeInfo.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Информация о сотрудниках");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        set_choice_box_value();
        set_view_value(topic_choice_box.getValue(), type_choice_box.getValue());
    }

    private void set_choice_box_value()
    {
        topic_choice_box.getItems().clear();

        UserState.client.sendMessage("getTopics");
        ArrayList<Topic> topics = (ArrayList<Topic>) UserState.client.readObject();

        for (int i = 0; i < topics.size(); i++) 
        {
            topicList.add(topics.get(i).getName());
        }
        
        topic_choice_box.setValue(topicList.get(0));
        topic_choice_box.setItems(topicList);
        
        type_choice_box.setValue(typeList.get(0));
        type_choice_box.setItems(typeList);

        topic_choice_box.setOnAction(event -> set_view_value(topic_choice_box.getValue(), type_choice_box.getValue()));
        type_choice_box.setOnAction(event -> set_view_value(topic_choice_box.getValue(), type_choice_box.getValue()));
    }

    private void set_view_value(String topic, String type)
    {
        tests_view.getItems().clear();
        UserState.client.sendMessage("getTests");
        UserState.client.sendMessage(topic + " " + type);
        
        ArrayList<Test> tests = (ArrayList<Test>) UserState.client.readObject();

        for (Test item : tests) 
        {
            testList.add(item.getName());
        }

        tests_view.setItems(testList);
        tests_view.getSelectionModel().selectFirst();
    }

    public void set_value(Employee employee)
    {
        login_label.setText(employee.getLogin());
        id = employee.getId();
    }
}
