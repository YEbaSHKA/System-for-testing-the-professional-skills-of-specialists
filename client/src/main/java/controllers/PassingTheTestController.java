package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.client.Client;
import org.client.Connection;
import org.testing_system.Question;
import org.testing_system.TestEmployee;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class PassingTheTestController implements Initializable
{
    private TabPane tabPane;

    private ObservableList<Spinner> spinners = FXCollections.observableArrayList();
    
    private Stage stage;
    
    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    @FXML
    private Pane main_pane;

    @FXML
    private Button start_button;

    @FXML
    private Button exit_button;

    @FXML
    private Button send_results;

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

    @FXML
    void send_results_cclick(MouseEvent event) 
    {
        send_results.setVisible(false);
        exit_button.setVisible(true);
        ArrayList<Integer> values = new ArrayList<>();
        for (Spinner item : spinners) {
            values.add((Integer)item.getValue());
        }
        Connection.client.sendMessage("getResultOfPassTheTest");

        Connection.client.sendObject(values);

        TestEmployee test_employee = new TestEmployee();
        test_employee.setId_test(Connection.id_test);
        test_employee.setId_employee(Connection.id);

        Connection.client.sendObject(test_employee);
        
        if((boolean) Connection.client.readObject())
        {
            validation.Dialog.successPass(Connection.client.readObject().toString());
        }
        else
        {
            validation.Dialog.showAlertWithServerTrouble();
        }
    }


    @FXML
    void start_button_click(MouseEvent event) 
    {
        exit_button.setVisible(false);
        start_button.setVisible(false);
        send_results.setVisible(true);
        Connection.client.sendMessage("getsQuestions");
        Connection.client.sendObject(Connection.id_test);

        ArrayList<Question> questions = (ArrayList<Question>) Connection.client.readObject();
        
        int count = 1;
        tabPane = new TabPane();
        for (Question item : questions) 
        {

            Tab tab = new Tab();
            tab.setClosable(false);
            tab.setText(count + "-й вопрос");
            
            AnchorPane anchor_pane = new AnchorPane();
            anchor_pane.setMinSize(750, 515);
            
            Label name_question = new Label(item.getName());
            name_question.setMaxSize(400, 100);
            name_question.setWrapText(true);

            
            Label first = new Label("1. " + item.getFirst_answer());
            first.setMaxSize(400, 100);
            first.setWrapText(true);

            Label second = new Label( "2. " + item.getSecond_answer());
            second.setMaxSize(400, 100);
            second.setWrapText(true);
            
            Label third = new Label("3. " + item.getThird_answer());
            third.setMaxSize(400, 100);
            third.setWrapText(true);

            VBox label_vbox = new VBox(20, name_question, first, second, third);
            AnchorPane.setTopAnchor(label_vbox, 50.0);
            AnchorPane.setLeftAnchor(label_vbox, 50.0);
            
            Spinner spinner = new Spinner<>();
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 3);
            valueFactory.setValue(1);
            spinner.setValueFactory(valueFactory);
            AnchorPane.setBottomAnchor(spinner, 45.0);
            AnchorPane.setRightAnchor(spinner, 200.0);
            spinners.add(spinner);

            Label answer = new Label("Ответ: ");
            AnchorPane.setBottomAnchor(answer, 50.0);
            AnchorPane.setLeftAnchor(answer, 50.0);

            anchor_pane.getChildren().addAll(label_vbox, answer, spinner);
            

            tab.setContent(anchor_pane);
            
            tabPane.getTabs().add(tab);
            count++;
        }

        main_pane.getChildren().add(tabPane);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        send_results.setVisible(false);
    }

}
