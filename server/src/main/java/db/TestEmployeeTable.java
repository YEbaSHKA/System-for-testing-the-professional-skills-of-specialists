package db;

import java.sql.SQLException;
import java.util.ArrayList;

import org.testing_system.TestEmployee;

public class TestEmployeeTable implements ITestEmployeeTable
{
    private static TestEmployeeTable instance;
    private TestingSystemDB dbConnection;

    private TestEmployeeTable() throws SQLException, ClassNotFoundException {
        dbConnection = dbConnection.getInstance();
    }

    public static synchronized TestEmployeeTable getInstance() throws SQLException, ClassNotFoundException 
    {
        if (instance == null) 
        {
            instance = new TestEmployeeTable();
        }
        return instance;
    }

    @Override
    public boolean check_availability(int id_test, int id_employee) {
        String SQL = "SELECT * FROM testEmployees WHERE id_test = " + id_test + " && id_employee = " + id_employee;
        ArrayList<String[]> result = dbConnection.getArrayResult(SQL);

        return !result.isEmpty();
    }

    @Override
    public boolean insert(TestEmployee test_employee) {
        String SQL = "INSERT INTO testEmployees(id_test, id_employee, result) VALUES(" +
                    test_employee.getId_test() + ", " + test_employee.getId_employee() + ", " + test_employee.getResult() + ")";
        
        return dbConnection.insert_values(SQL);
    }

    @Override
    public boolean update(TestEmployee test_employee) {
        String SQL = "UPDATE testEmployees SET result = " + test_employee.getResult() +
                    " WHERE id_test = " + test_employee.getId_test() + " && id_employee = " + test_employee.getId_employee();
        return dbConnection.insert_values(SQL);
    }

    @Override
    public boolean update_result(String login, String test_name, int result)
    {
        String SQL = "UPDATE testEmployees, Tests, Employee SET testEmployees.result = " + result 
                    + " WHERE testEmployees.id_test = Tests.id && Tests.name = '" + test_name 
                    + "' && testEmployees.id_employee = Employee.id && Employee.login = '" + login + "'";
        return dbConnection.insert_values(SQL);
    }
    
    @Override
    public boolean update_result(int test_id, int employee_id)
    {
        String SQL = "UPDATE testEmployees SET result = NULL WHERE id_test = " + test_id 
        + " && id_employee = " + employee_id;
        return dbConnection.insert_values(SQL);
    }

    @Override
    public boolean insert_mandatory(int id_test, int id_employee) {
        String SQL = "INSERT INTO testEmployees(id_test, id_employee, result) VALUES(" +
                    id_test + ", " + id_employee + ", NULL)";
        
        return dbConnection.insert_values(SQL);
    }

}
