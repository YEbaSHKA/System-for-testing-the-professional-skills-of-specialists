package db;

import java.sql.SQLException;
import java.util.ArrayList;

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

    @Override
    public Employee getEmployee(String login) {
        String SQL = "SELECT * FROM Employee WHERE login = '" + login + "'";
        ArrayList<String[]> result = DBConnection.getArrayResult(SQL);
        Employee employee = new Employee();
        for (String[] item : result) {
            employee.setId(Integer.parseInt(item[0]));
            employee.setLogin(item[1]);
            employee.setPassword(item[2]);
            employee.setFull_name(item[3]);
        }
        return employee;
    }
    
}
