package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.client.Connection;
import org.testing_system.Topic;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class EmployeeMenuController implements Initializable {

    @FXML
    private Button exit_user_btn;

    @FXML
    private AnchorPane main_page_tab;

    @FXML
    private AnchorPane profile_page_tab;

    @FXML
    private AnchorPane ressults_page_tab;

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
        Connection.client.sendMessage("getTopics");

        ArrayList<Topic> topics = (ArrayList<Topic>) Connection.client.readObject();
        // Node[] buttons = new Node[topics.size()];

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
            // buttons[j] = button;
            flow_pane.setMinSize(500, 700);
            flow_pane.getChildren().add(button);
            // flow_pane = new FlowPane(20, 20, button);
            // main_page_tab.getChildren().add(flow_pane);
        }
        main_page_tab.getChildren().add(flow_pane);
        AnchorPane.setTopAnchor(flow_pane, (double) 50);
        AnchorPane.setLeftAnchor(flow_pane, (double) 100);


        
        // Button btn = new Button("DDDDDDDDDDDDDD");
        // btn.setMinSize(200, 200);
        // System.out.println(btn.getText());
        // Label label = new Label("asdsadasd");
// 
        // flow_pane = new FlowPane(10, 10, btn);
        
        // main_page_tab.getChildren().addAll(buttons);
        

        // flow_pane.getChildren().add(btn);

        // for (Topic i : topics) {
        //     int a = 0, b = 0;
        //     Button btn = new Button(i.getName());
        //     grid_pane.add(btn, a, b);
        //     grid_pane.getChildren().addAll(btn);
        //     a ++;
        //     b ++;
        // }

    }
    
}
