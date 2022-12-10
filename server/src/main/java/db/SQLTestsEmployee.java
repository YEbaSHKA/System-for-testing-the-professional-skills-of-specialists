package db;

import java.sql.SQLException;
import java.util.ArrayList;

import org.testing_system.TestEmployee;

public class SQLTestsEmployee implements ISQLTestsEmployee
{
    private static SQLTestsEmployee instance;
    private DBConnection dbConnection;

    private SQLTestsEmployee() throws SQLException, ClassNotFoundException {
        dbConnection = DBConnection.getInstance();
    }

    public static synchronized SQLTestsEmployee getInstance() throws SQLException, ClassNotFoundException 
    {
        if (instance == null) 
        {
            instance = new SQLTestsEmployee();
        }
        return instance;
    }

    @Override
    public boolean check_availability(int id_test, int id_employee) {
        String SQL = "SELECT * FROM testEmployees WHERE id_test = " + id_test + " && id_employee = " + id_employee;
        ArrayList<String[]> result = DBConnection.getArrayResult(SQL);

        return !result.isEmpty();
    }

    @Override
    public boolean insert(TestEmployee test_employee) {
        String SQL = "INSERT INTO testEmployees(id_test, id_employee, result) VALUES(" +
                    test_employee.getId_test() + ", " + test_employee.getId_employee() + ", " + test_employee.getResult() + ")";
        
        return DBConnection.insert_values(SQL);
    }

    @Override
    public boolean update(TestEmployee test_employee) {
        String SQL = "UPDATE testEmployees SET result = " + test_employee.getResult() +
                    " WHERE id_test = " + test_employee.getId_test() + " && id_employee = " + test_employee.getId_employee();
        return DBConnection.insert_values(SQL);
    }
}
