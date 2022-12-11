package controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AdminMenuController {

    @FXML
    private Button employee_inf;

    @FXML
    private Button exit_button;

    @FXML
    private Button test_inf;

    @FXML
    void employee_inf_click(MouseEvent event)
    {

    }

    @FXML
    void exit_button_click(ContextMenuEvent event) throws IOException {
        exit_button.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Авторизация");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void test_inf_click(MouseEvent event) throws IOException {
        test_inf.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/testInformation.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Информация о тестах");
        stage.setScene(scene);
        stage.show();
    }

}