package controllers;

import java.io.IOException;
import java.lang.ModuleLayer.Controller;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.client.UserState;
import org.testing_system.Employee;
import org.testing_system.ResultOfTest;
import org.testing_system.Topic;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class EmployeeMenuController implements Initializable {

    @FXML
    private Button edit_button;

    @FXML
    private Button exit_user_btn;

    @FXML
    private Button exit_user_btn1;

    @FXML
    private Label first_name_label;

    @FXML
    private Label last_name_label;

    @FXML
    private Label login_label;

    @FXML
    private AnchorPane main_page_tab;

    @FXML
    private Label password_label;

    @FXML
    private Label patronymic_label;

    @FXML
    private AnchorPane profile_page_tab;

    @FXML
    private TableColumn<ResultOfTest, Integer> result_column;

    @FXML
    private TableView<ResultOfTest> table_view;

    @FXML
    private TableColumn<ResultOfTest, String> test_column;

    @FXML
    private TableColumn<ResultOfTest, String> topic_column;

    ObservableList<ResultOfTest> results_list = FXCollections.observableArrayList();

    @FXML
    void edit_button_click(MouseEvent event) throws IOException 
    {
        edit_button.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/editEmployee.fxml"));
        Parent root = fxmlLoader.load();
        EditEmployeeController controller = fxmlLoader.getController();
        Scene scene;
        scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Изменение личной информации");
        stage.setScene(scene);
        controller.setValue(first_name_label.getText(), last_name_label.getText(), patronymic_label.getText(), login_label.getText());
        stage.show();
    }

    @FXML
    void exit_btn_click(MouseEvent event) throws IOException 
    {
        exit_user_btn.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Авторизация");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) 
    {
        main_pain_initialize();
        profile_pain_initialize();
    }

    private void profile_pain_initialize()
    {
        UserState.client.sendMessage("getEmployeeInf");
        UserState.client.sendObject(UserState.current_user_id);

        Employee employee = (Employee) UserState.client.readObject();
        String[] full_name = employee.getFull_name().split(" ");
        
        
        char[] temp_password = employee.getPassword().toCharArray();
        String password = "";
        for (int i = 0; i < temp_password.length; i++) 
        {
            password += "*";   
        }

        last_name_label.setText(full_name[0]);
        first_name_label.setText(full_name[1]);
        patronymic_label.setText(full_name[2]);
        login_label.setText(employee.getLogin());
        password_label.setText(password);

        topic_column.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getTopic_name()));
        test_column.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getTest_name()));
        result_column.setCellValueFactory(field -> new SimpleObjectProperty<>(field.getValue().getResult_of_test()));

        table_view.setItems(get_results());

    }
    
    private ObservableList<ResultOfTest> get_results()
    {
        ObservableList<ResultOfTest> results_list = FXCollections.observableArrayList();
        ArrayList<ResultOfTest> results = (ArrayList<ResultOfTest>) UserState.client.readObject();

        for (int i = 0; i < results.size(); i++) 
        {
            results_list.add(new ResultOfTest(results.get(i).getId_employee(),
                    results.get(i).getTopic_name(),
                    results.get(i).getTest_name(),
                    results.get(i).getResult_of_test()));
            table_view.setItems(results_list);
        }
        return results_list;
    }

    private void main_pain_initialize()
    {
        UserState.client.sendMessage("getTopics");

        ArrayList<Topic> topics = (ArrayList<Topic>) UserState.client.readObject();

        FlowPane flow_pane = new FlowPane(20, 20);
        for (Topic i : topics) 
        {
            Button button = new Button(i.getName());
            button.setId(i.getName());
            button.setMinSize(150, 150);
            button.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() 
            {
             
                @Override
                public void handle(ActionEvent event) 
                {
                    button.getScene().getWindow().hide();
                    try 
                    {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tests.fxml"));
                        Parent root = fxmlLoader.load();
                        TestsController controller = fxmlLoader.getController();
                        Scene scene;
                        scene = new Scene(root);
                        Stage stage = new Stage();
                        controller.setStage(stage);
                        stage.setTitle(button.getId());
                        stage.setScene(scene);
                        // TestsController.setLable(button.getId());
                        stage.show();
                    }
                    catch (IOException e) 
                    {
                        e.printStackTrace();
                    }
                }
            });
            flow_pane.setMinSize(500, 700);
            flow_pane.getChildren().add(button);
        }
        main_page_tab.getChildren().add(flow_pane);
        AnchorPane.setTopAnchor(flow_pane, (double) 50);
        AnchorPane.setLeftAnchor(flow_pane, (double) 100);
    }
    
}
