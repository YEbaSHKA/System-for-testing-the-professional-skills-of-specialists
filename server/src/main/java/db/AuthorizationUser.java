package db;

import java.sql.SQLException;
import java.util.ArrayList;

import org.testing_system.Admin;
import org.testing_system.Employee;

public class AuthorizationUser implements IAuthorizationUser {
    private static AuthorizationUser instance;
    private TestingSystemDB dbConnection;

    private AuthorizationUser() throws SQLException, ClassNotFoundException {
        dbConnection = dbConnection.getInstance();
    }

    public static synchronized AuthorizationUser getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new AuthorizationUser();
        }
        return instance;
    }

    @Override
    public boolean check_admins(Admin admin) {
        String SQL = "SELECT * FROM Admin WHERE login = '" + admin.getLogin() + "' && password = '" + admin.getPassword() + "'";
        ArrayList<String[]> result = dbConnection.getArrayResult(SQL); 
        
        if(result.isEmpty())
        {
            return false;
        }
        else
            return true;
    }

    @Override
    public boolean check_availability(Employee obj) {
        String SQL = "SELECT * FROM Employee WHERE login = '" + obj.getLogin() + "' && password = '" + obj.getPassword() + "'";
        ArrayList<String[]> result = dbConnection.getArrayResult(SQL); 

        if(result.isEmpty())
        {
            return false;
        }
        else
            return true;
    }

    
}
