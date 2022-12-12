package db;

import java.sql.SQLException;

public abstract class AbstractFactory {
    public abstract TestEmployeeTable get_tests_employee() throws SQLException, ClassNotFoundException;
    public abstract ResultOfTestTable get_result_of_test() throws SQLException, ClassNotFoundException;
    public abstract AuthorizationUser get_authorization() throws SQLException, ClassNotFoundException;
    public abstract QuestionTable get_question() throws SQLException, ClassNotFoundException;
    public abstract EmployeeTable get_employee() throws SQLException, ClassNotFoundException;
    public abstract TopicTable get_topics() throws SQLException, ClassNotFoundException;
    public abstract AdminTable get_admin() throws SQLException, ClassNotFoundException;
    public abstract TestTable get_tests() throws SQLException, ClassNotFoundException;
}
