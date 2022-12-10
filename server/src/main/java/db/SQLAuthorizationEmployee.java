package db;

import java.sql.SQLException;
import java.util.ArrayList;

import org.testing_system.Employee;

public class SQLAuthorizationEmployee implements ISQLAuthorizationEmployee {

    private static SQLAuthorizationEmployee instance;
    private DBConnection dbConnection;

    SQLAuthorizationEmployee() throws SQLException, ClassNotFoundException {
        dbConnection = DBConnection.getInstance();
    }

    public static synchronized SQLAuthorizationEmployee getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new SQLAuthorizationEmployee();
        }
        return instance;
    }

    @Override
    public boolean check_availability(Employee obj) {
        String SQL = "SELECT * FROM Employee WHERE login = '" + obj.getLogin() + "' && password = '" + obj.getPassword() + "'";
        ArrayList<String[]> result = DBConnection.getArrayResult(SQL); 

        if(result.isEmpty())
        {
            return false;
        }
        else
            return true;
    }
    
}
