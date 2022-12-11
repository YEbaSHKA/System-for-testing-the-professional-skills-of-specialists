package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.client.Client;
import org.client.Connection;
import org.testing_system.Topic;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TestInformationController implements Initializable 
{

    @FXML
    private Button add_question_btn;

    @FXML
    private Button add_test_btn;

    @FXML
    private Button add_topic_btn;

    @FXML
    private TableColumn<?, ?> correct_answer_column;

    @FXML
    private Button delete_question_btn;

    @FXML
    private Button delete_test_btn;

    @FXML
    private Button delete_topic_btn;

    @FXML
    private Button edit_question_btn;

    @FXML
    private Button edit_test_btn;

    @FXML
    private Button edit_topic_btn;

    @FXML
    private Button exit_admin_button;

    @FXML
    private TableColumn<?, ?> first_answer_column;

    @FXML
    private TableColumn<?, ?> question_name_column;

    @FXML
    private TableView<?> question_table_view;

    @FXML
    private TableColumn<?, ?> second_answer_column;

    @FXML
    private TableColumn<?, ?> test_name_column;

    @FXML
    private TableView<?> test_table_view;

    @FXML
    private TableColumn<?, ?> third_answer_column;

    @FXML
    private ChoiceBox<String> topic_choice_box;

    @FXML
    private TableColumn<?, ?> topic_column;

    @FXML
    private ChoiceBox<?> type_choice_box;

    @FXML
    private TableColumn<?, ?> type_column;

    private ObservableList<String> topicList = FXCollections.observableArrayList();

    @FXML
    void add_question_btn_click(MouseEvent event) {

    }

    @FXML
    void add_test_btn_click(MouseEvent event) {

    }

    @FXML
    void add_toppic_btn_click(MouseEvent event) {

    }

    @FXML
    void delete_question_btn_click(MouseEvent event) {

    }

    @FXML
    void delete_test_btn_click(MouseEvent event) {

    }

    @FXML
    void delete_topic_btn_click(MouseEvent event) {

    }

    @FXML
    void edit_question_btn_click(MouseEvent event) {

    }

    @FXML
    void edit_test_btn_click(MouseEvent event) {

    }

    @FXML
    void edit_topic_btn_click(MouseEvent event) {

    }

    @FXML
    void exit_admin_button_click(MouseEvent event) throws IOException
    {
        exit_admin_button.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/adminMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Меню");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        set_check_box_value();
    }

    private void set_check_box_value()
    {
        Connection.client.sendMessage("getTopics");
        ArrayList<Topic> topics = (ArrayList<Topic>) Connection.client.readObject();

        for (int i = 0; i < topics.size(); i++) 
        {
            topicList.add(topics.get(i).getName());
        }
        topic_choice_box.setValue(topics.get(0).getName());
        topic_choice_box.setItems(topicList);

    }
}