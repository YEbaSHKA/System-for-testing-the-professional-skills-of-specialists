package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.client.UserState;
import org.testing_system.Employee;
import org.testing_system.ResultOfTest;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import validation.AlertWindow;

public class EmployeeInfoController implements Initializable 
{

    @FXML
    private ProgressBar mandatory_progress_bar;

    @FXML
    private Label progress_label;

    @FXML
    private Button add_employee_btn;

    @FXML
    private Button appoint_test;

    @FXML
    private Button delete_employee_btn;

    @FXML
    private Button edit_button;

    @FXML
    private Button edit_employee_btn;

    @FXML
    private Button exit_button;

    @FXML
    private TableView<Employee> employee_table_view;

    @FXML
    private TableColumn<Employee, String> full_name_column;

    @FXML
    private TableColumn<Employee, String> login_column;

    @FXML
    private TableColumn<ResultOfTest, Integer> result_column;

    @FXML
    private TableColumn<ResultOfTest, String> test_column;

    @FXML
    private TableView<ResultOfTest> test_results;

    @FXML
    private TableColumn<ResultOfTest, String> topic_column;

    ObservableList<Employee> employee_list = FXCollections.observableArrayList();
    ObservableList<ResultOfTest> results_list = FXCollections.observableArrayList();

    @FXML
    void appoint_test_click(MouseEvent event) throws IOException 
    {
        if (employee_table_view.getSelectionModel().getSelectedIndex() == -1) 
        {
            AlertWindow.showAlertWithUncorrectData("Нет доступных сотрудников добавьте сотрудника!");
        }
        else
        {
            appoint_test.getScene().getWindow().hide();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mandatoryTest.fxml"));
            Parent root = fxmlLoader.load();
            MandatoryTestController controller = fxmlLoader.getController();
            Scene scene;
            scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Назначение обязательного теста сотруднику");
            stage.setScene(scene);
            controller.set_value(employee_table_view.getSelectionModel().getSelectedItem());
            stage.showAndWait();
        }
    }

    @FXML
    void add_employee_btn_click(MouseEvent event) throws IOException 
    {
        add_employee_btn.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/employeeAdd.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Добавить сотрудника");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void delete_employee_btn_click(MouseEvent event) 
    {
        if (employee_table_view.getSelectionModel().getSelectedIndex() == -1) 
        {
            AlertWindow.showAlertWithUncorrectData("Нет доступных сотрудников добавьте сотрудника!");
        }
        else
        {
            UserState.client.sendMessage("deleteEmployee");

            UserState.client.sendObject(employee_table_view.getSelectionModel().getSelectedItem());
            if ((boolean) UserState.client.readObject()) 
            {
                AlertWindow.correctOperation();
                set_employee_table_view();
            }
            else
            {
                AlertWindow.showAlertWithServerTrouble();
            }
        }
    }

    @FXML
    void edit_button_click(MouseEvent event) throws IOException 
    {
        if (test_results.getSelectionModel().getSelectedIndex() == -1) 
        {
            AlertWindow.showAlertWithUncorrectData("Сотрудник не прошел ни одного теста!");
        }
        else
        {
            edit_button.getScene().getWindow().hide();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resultOfTestEdit.fxml"));
            Parent root = fxmlLoader.load();
            ResultOfTestEditController controller = fxmlLoader.getController();
            Scene scene;
            scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Изменение результата прохождения теста");
            stage.setScene(scene);
            controller.set_value(test_results.getSelectionModel().getSelectedItem(), employee_table_view.getSelectionModel().getSelectedItem().getLogin());
            stage.showAndWait();
        }
    }

    @FXML
    void edit_employee_btn_click(MouseEvent event) throws IOException 
    {
        if (employee_table_view.getSelectionModel().getSelectedIndex() == -1) 
        {
            AlertWindow.showAlertWithUncorrectData("Нет доступных сотрудников добавьте сотрудника!");
        }
        else
        {
            edit_button.getScene().getWindow().hide();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/employeeEdit.fxml"));
            Parent root = fxmlLoader.load();
            EmployeeEditController controller = fxmlLoader.getController();
            Scene scene;
            scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Изменение личной информации");
            stage.setScene(scene);
            controller.set_value(employee_table_view.getSelectionModel().getSelectedItem());
            stage.showAndWait();
        }
    }

    @FXML
    void exit_button_click(MouseEvent event) throws IOException 
    {
        exit_button.getScene().getWindow().hide();
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
        set_employee_table_view();
        set_test_results(employee_table_view.getSelectionModel().getSelectedItem());
        set_progress(employee_table_view.getSelectionModel().getSelectedItem());
        employee_table_view.getSelectionModel().selectedItemProperty().addListener((observable_list, old_value, new_value) -> {
            if (new_value != null) 
            {
                set_test_results(new_value);
                set_progress(new_value);
            }
        });
    }

    private void set_progress(Employee employee)
    {
        UserState.client.sendMessage("getCountOfMandatoryTests");
        UserState.client.sendObject(employee);

        int count_of_mandatory_tests = (int) UserState.client.readObject();

        if (count_of_mandatory_tests == 0) 
        {
            progress_label.setText("У сотрудника нет обязательных тестов");
            mandatory_progress_bar.setProgress(0.00);
        }
        else
        {
            UserState.client.sendMessage("getCountOfCompleteMandatoryTests");
            UserState.client.sendObject(employee);

            int count_of_complete_mandatory_tests = (int) UserState.client.readObject();

            double progress = (double) count_of_complete_mandatory_tests / count_of_mandatory_tests;

            mandatory_progress_bar.setProgress(progress);
            progress_label.setText(Integer.toString((int) (progress * 100)) + "%");
            System.out.println(count_of_mandatory_tests + " " + count_of_complete_mandatory_tests + " " + progress);
        }
    }

    private void set_employee_table_view()
    {
        employee_table_view.getItems().clear();
        UserState.client.sendMessage("getEmployees");
        
        full_name_column.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getFull_name()));
        login_column.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getLogin()));
        
        ArrayList<Employee> employees = (ArrayList<Employee>) UserState.client.readObject();

        employee_list.addAll(employees);
        
        employee_table_view.setItems(employee_list);
        employee_table_view.getSelectionModel().selectFirst();

        if (employee_list.isEmpty()) 
        {
            test_results.getItems().clear();    
        }
    }

    private void set_test_results(Employee employee)
    {
        test_results.getItems().clear();

        UserState.client.sendMessage("getResults");
        UserState.client.sendObject(employee.getId());

        topic_column.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getTopic_name()));
        test_column.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getTest_name()));
        result_column.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getResult_of_test()));

        ArrayList<ResultOfTest> results = (ArrayList<ResultOfTest>) UserState.client.readObject();

        results_list.addAll(results);

        test_results.setItems(results_list);
        test_results.getSelectionModel().selectFirst();
    }

}
