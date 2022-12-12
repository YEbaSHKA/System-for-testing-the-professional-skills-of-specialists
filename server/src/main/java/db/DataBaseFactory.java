package db;

import java.sql.SQLException;

public class DataBaseFactory extends AbstractFactory {

    @Override
    public EmployeeTable get_employee() throws SQLException, ClassNotFoundException {
        return EmployeeTable.getInstance();
    }

    @Override
    public AdminTable get_admin() throws SQLException, ClassNotFoundException {
        return AdminTable.getInstance();
    }

    @Override
    public TopicTable get_topics() throws SQLException, ClassNotFoundException {
        return TopicTable.getInstance();
    }

    @Override
    public TestTable get_tests() throws SQLException, ClassNotFoundException {
        return TestTable.getInstance();
    }

    @Override
    public QuestionTable get_question() throws SQLException, ClassNotFoundException {
        return QuestionTable.getInstance();
    }

    @Override
    public TestEmployeeTable get_tests_employee() throws SQLException, ClassNotFoundException {
        return TestEmployeeTable.getInstance();
    }

    @Override
    public ResultOfTestTable get_result_of_test() throws SQLException, ClassNotFoundException {
        return ResultOfTestTable.getInstance();
    }

    @Override
    public AuthorizationUser get_authorization() throws SQLException, ClassNotFoundException {
        return AuthorizationUser.getInstance();
    }
    
}
