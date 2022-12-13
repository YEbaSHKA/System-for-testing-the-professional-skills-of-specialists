package controllers;

import java.io.IOException;
import java.util.ArrayList;

import org.client.UserState;
import org.testing_system.Test;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class EmployeeMandatoryTestController {

    @FXML
    private Button complete_button;

    @FXML
    private Button exit_button;

    @FXML
    private ListView<String> tests_view;

    private ArrayList<Test> tests;

    private ObservableList<String> testList = FXCollections.observableArrayList();

    @FXML
    void complete_button_click(MouseEvent event) throws IOException 
    {
        UserState.client.sendMessage("getTestIdByName");

        UserState.client.sendMessage(tests_view.getSelectionModel().getSelectedItem());
        
        UserState.current_test_id = (int) UserState.client.readObject();
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/passingTheTest.fxml"));
        Parent root = fxmlLoader.load();
        PassingTheTestController controller = fxmlLoader.getController();
        Scene scene;
        scene = new Scene(root);
        Stage stage = new Stage();
        controller.setStage(stage);
        stage.setTitle(tests_view.getSelectionModel().getSelectedItem());
        stage.setScene(scene);
        complete_button.getScene().getWindow().hide();
        stage.show();
    }

    @FXML
    void exit_button_click(MouseEvent event) throws IOException 
    {
        exit_button.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/employeeMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Меню");
        stage.setScene(scene);
        stage.show();
    }

    public void set_value(ArrayList<Test> mandatory_tests)
    {
        tests = mandatory_tests;
        
        tests_view.getItems().clear();

        for (Test item : tests) 
        {
            testList.add(item.getName());
        }

        tests_view.setItems(testList);
        tests_view.getSelectionModel().selectFirst();
    }

}
