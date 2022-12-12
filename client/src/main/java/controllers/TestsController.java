package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.client.UserState;
import org.client.Main;
import org.testing_system.Test;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class TestsController /*implements Initializable*/
{

    private Stage stage;

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    @FXML
    private Button show_easy_tests;

    @FXML
    private Button show_hard_tests;

    @FXML
    private Button show_middle_tests;

    @FXML
    private Button back_to_menu;

    @FXML
    private AnchorPane tests_anchorn_pane;

    private FlowPane tests_flow_pane;


    @FXML
    void back_to_menu_click(MouseEvent event) throws IOException {
        back_to_menu.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/employeeMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Меню");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void show_easy_tests_click(MouseEvent event) 
    {
        show_test_list("Легкая");
    }   

    @FXML
    void show_hard_tests_click(MouseEvent event) 
    {
        show_test_list("Сложная");
    }

    @FXML
    void show_middle_tests_click(MouseEvent event) 
    {
        show_test_list("Средняя");
    }

    private void show_test_list(String type)
    {
        UserState.client.sendMessage("getTests");
        UserState.client.sendMessage(stage.getTitle() + " " + type);
        
        ArrayList<Test> tests = (ArrayList<Test>) UserState.client.readObject();
        
        if(tests_flow_pane != null)
        {
            tests_flow_pane.getChildren().clear();
        }
        tests_flow_pane = new FlowPane(Orientation.VERTICAL ,20, 20);
        for (Test i : tests) 
        {
            Button button = new Button(i.getName());
            button.setId(i.getName());
            button.setMinSize(100, 35);
            button.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() 
            {
             
                @Override
                public void handle(ActionEvent event) 
                {
                    button.getScene().getWindow().hide();
                    try
                    {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/passingTheTest.fxml"));
                        Parent root = fxmlLoader.load();
                        PassingTheTestController controller = fxmlLoader.getController();
                        Scene scene;
                        scene = new Scene(root);
                        Stage stage = new Stage();
                        controller.setStage(stage);
                        stage.setTitle(button.getId());
                        stage.setScene(scene);
                        UserState.current_test_id  = i.getId();
                        stage.show();
                    }
                    catch (IOException e) 
                    {
                            e.printStackTrace();
                        }
                    }
                });
                // buttons[j] = button;
                
                tests_flow_pane.setMinSize(500, 380);
                tests_flow_pane.getChildren().add(button);
            }
            // tests_flow_pane.getChildren().clear();
            tests_anchorn_pane.getChildren().add(tests_flow_pane);
            AnchorPane.setTopAnchor(tests_flow_pane, (double) 150);
            AnchorPane.setLeftAnchor(tests_flow_pane, (double) 25);
    }
}
