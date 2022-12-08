package db;

import java.sql.SQLException;

import org.testing_system.Employee;

public class SQLEmployee implements ISQLEmployee {

    private static SQLEmployee instance;
    private DBConnection dbConnection;

    private SQLEmployee() throws SQLException, ClassNotFoundException {
        dbConnection = DBConnection.getInstance();
    }

    public static synchronized SQLEmployee getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new SQLEmployee();
        }
        return instance;
    }


    @Override
    public boolean insert(Employee employee) 
    {
        String SQL = "INSERT INTO Employee(login, password, full_name) VALUES('" +
                        employee.getLogin() + "', '" + employee.getPassword() + "', '" + employee.getFull_name() + "')";
        if(DBConnection.insert_values(SQL))
        {
            return true;
        }
        else
            return false;
    }
    
}
