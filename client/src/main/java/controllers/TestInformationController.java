package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.client.UserState;
import org.testing_system.Question;
import org.testing_system.Test;
import org.testing_system.Topic;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import validation.AlertWindow;
import validation.WrapTextTableCell;

public class TestInformationController implements Initializable 
{

    @FXML
    private Button add_question_btn;

    @FXML
    private Button add_test_btn;

    @FXML
    private Button add_topic_btn;

    @FXML
    private TableColumn<Question, Integer> correct_answer_column;

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
    private TableColumn<Question, String> first_answer_column;

    @FXML
    private TableColumn<Question, String> question_name_column;

    @FXML
    private TableView<Question> question_table_view;

    @FXML
    private TableColumn<Question, String> second_answer_column;

    @FXML
    private TableColumn<Test, String> test_name_column;

    @FXML
    private TableView<Test> test_table_view;

    @FXML
    private TableColumn<Question, String> third_answer_column;

    @FXML
    private ChoiceBox<String> topic_choice_box;

    @FXML
    private TableColumn<Test, String> topic_column;

    @FXML
    private ChoiceBox<String> type_choice_box;

    @FXML
    private TableColumn<Test, String> type_column;

    private ObservableList<String> topicList = FXCollections.observableArrayList();

    private ObservableList<String> typeList = FXCollections.observableArrayList("Легкая", "Средняя", "Сложная");

    private ObservableList<Test> testList = FXCollections.observableArrayList();

    private ObservableList<Question> questionList = FXCollections.observableArrayList();


    @FXML
    void add_question_btn_click(MouseEvent event) throws IOException 
    {
        if (test_table_view.getSelectionModel().getSelectedIndex() == -1) 
        {
            AlertWindow.showAlertWithUncorrectData("Нет доступных тестов, сначала добавьте тест!");
        }
        else
        {
            add_question_btn.getScene().getWindow().hide();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/questionAdd.fxml"));
            Parent root = fxmlLoader.load();
            QuestionAddController controller = fxmlLoader.getController();
            Scene scene;
            scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Добавление вопроса");
            stage.setScene(scene);
            controller.set_value(test_table_view.getSelectionModel().getSelectedItem().getId());
            stage.show();
        }
    }

    @FXML
    void add_test_btn_click(MouseEvent event) throws IOException 
    {
        if (topicList.isEmpty()) 
        {
            AlertWindow.showAlertWithUncorrectData("Нет доступных тем, сначала добавьте тему!");
        }
        else
        {
            add_test_btn.getScene().getWindow().hide();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/testAdd.fxml"));
            Parent root = fxmlLoader.load();
            TestAddController controller = fxmlLoader.getController();
            Scene scene;
            scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Добавление теста");
            stage.setScene(scene);
            controller.set_value(topic_choice_box.getValue());
            stage.show();
        }
    }

    @FXML
    void add_toppic_btn_click(MouseEvent event) throws IOException 
    {
        add_test_btn.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/topicAdd.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene;
        scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Добавление темы");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void delete_question_btn_click(MouseEvent event) 
    {
        if (question_table_view.getSelectionModel().getSelectedIndex() == -1) 
        {
            AlertWindow.showAlertWithUncorrectData("Нет доступных вопросов, сначала добавьте вопрос!");
        }
        else
        {
            UserState.client.sendMessage("deleteQuestion");
            UserState.client.sendObject(question_table_view.getSelectionModel().getSelectedItem());
            if ((boolean) UserState.client.readObject())
            {
                AlertWindow.correctOperation();
                set_question_table_view(test_table_view.getSelectionModel().getSelectedItem());
            }
            else
            {
                AlertWindow.showAlertWithServerTrouble();
            }
        }
    }

    @FXML
    void delete_test_btn_click(MouseEvent event) 
    {
        if (test_table_view.getSelectionModel().getSelectedIndex() == -1) 
        {
            AlertWindow.showAlertWithUncorrectData("Нет доступных тестов, сначала добавьте тест!");
        }
        else
        {
            UserState.client.sendMessage("deleteTest");
            UserState.client.sendObject(test_table_view.getSelectionModel().getSelectedItem());
            if ((boolean) UserState.client.readObject())
            {
                AlertWindow.correctOperation();
                set_test_table_view_value(topic_choice_box.getValue(), type_choice_box.getValue());
            }
            else
            {
                AlertWindow.showAlertWithServerTrouble();
            }
        }
    }

    @FXML
    void delete_topic_btn_click(MouseEvent event) 
    {
        if (topicList.isEmpty()) 
        {
            AlertWindow.showAlertWithUncorrectData("Нет доступных тем, сначала добавьте тему!");
        }
        else
        {
            UserState.client.sendMessage("deleteTopic");
            Topic topic = new Topic();
            topic.setName(topic_choice_box.getValue());
            UserState.client.sendObject(topic);
            if ((boolean) UserState.client.readObject())
            {
                AlertWindow.correctOperation();
                set_check_box_value();
            }
            else
            {
                AlertWindow.showAlertWithServerTrouble();
            }
        }
    }

    @FXML
    void edit_question_btn_click(MouseEvent event) throws IOException 
    {
        if (question_table_view.getSelectionModel().getSelectedIndex() == -1) 
        {
            AlertWindow.showAlertWithUncorrectData("Нет доступных вопросов, сначала добавьте вопрос!");
        }
        else
        {
            edit_question_btn.getScene().getWindow().hide();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/questionEdit.fxml"));
            Parent root = fxmlLoader.load();
            QuestionEditController controller = fxmlLoader.getController();
            Scene scene;
            scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Изменение информации о вопросе");
            stage.setScene(scene);
            controller.set_value(question_table_view.getSelectionModel().getSelectedItem());
            stage.show();
        }
    }

    @FXML
    void edit_test_btn_click(MouseEvent event) throws IOException 
    {
        if (test_table_view.getSelectionModel().getSelectedIndex() == -1) 
        {
            AlertWindow.showAlertWithUncorrectData("Нет доступных тестов, сначала добавьте тест!");
        }
        else
        {
            edit_test_btn.getScene().getWindow().hide();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/testEdit.fxml"));
            Parent root = fxmlLoader.load();
            TestEditController controller = fxmlLoader.getController();
            Scene scene;
            scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Изменение информации о тесте");
            stage.setScene(scene);
            controller.set_value(test_table_view.getSelectionModel().getSelectedItem(), topicList);
            stage.show();
        }
    }

    @FXML
    void edit_topic_btn_click(MouseEvent event) throws IOException 
    {
        if (topicList.isEmpty()) 
        {
            AlertWindow.showAlertWithUncorrectData("Нет доступных тем, сначала добавьте тему!");
        }
        else
        {
            edit_test_btn.getScene().getWindow().hide();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/topicEdit.fxml"));
            Parent root = fxmlLoader.load();
            TopicEditController controller = fxmlLoader.getController();
            Scene scene;
            scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Изменение информации о теме");
            stage.setScene(scene);
            controller.set_value(topic_choice_box.getValue());
            stage.show();
        }
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
        set_test_table_view_value(topic_choice_box.getValue(), type_choice_box.getValue());
        set_question_table_view(test_table_view.getSelectionModel().getSelectedItem());

        test_table_view.getSelectionModel().selectedItemProperty().addListener((observable_list, old_value, new_value) -> {
            if (new_value != null) {
                set_question_table_view(new_value);
            }
        });
    }

    private void set_check_box_value()
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

        topic_choice_box.setOnAction(event -> set_test_table_view_value(topic_choice_box.getValue(), type_choice_box.getValue()));
        type_choice_box.setOnAction(event -> set_test_table_view_value(topic_choice_box.getValue(), type_choice_box.getValue()));

    }

    private void set_test_table_view_value(String topic, String type)
    {
        test_table_view.getItems().clear();
        UserState.client.sendMessage("getTests");
        UserState.client.sendMessage(topic + " " + type);
        
        topic_column.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getTopic_name()));
        test_name_column.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getName()));
        type_column.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getType()));
        
        
        ArrayList<Test> tests = (ArrayList<Test>) UserState.client.readObject();
        
        testList.addAll(tests);

        test_table_view.setItems(testList);
        test_table_view.getSelectionModel().selectFirst();

        if (testList.isEmpty()) 
        {
            question_table_view.getItems().clear();   
        }
    }

    private void set_question_table_view(Test test)
    {
        question_table_view.getItems().clear();
        UserState.client.sendMessage("getsQuestions");
        UserState.client.sendObject(test.getId());
        
        question_name_column.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getName()));
        first_answer_column.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getFirst_answer()));
        second_answer_column.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getSecond_answer()));
        third_answer_column.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getThird_answer()));
        correct_answer_column.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getCorrect_answer_number()));
        
        question_name_column.setCellFactory(cell -> new WrapTextTableCell<>());
        first_answer_column.setCellFactory(cell -> new WrapTextTableCell<>());
        second_answer_column.setCellFactory(cell -> new WrapTextTableCell<>());
        third_answer_column.setCellFactory(cell -> new WrapTextTableCell<>());

        ArrayList<Question> questions = (ArrayList<Question>) UserState.client.readObject();

        questionList.addAll(questions);

        question_table_view.setItems(questionList);
        question_table_view.getSelectionModel().selectFirst();
    }

    
}

