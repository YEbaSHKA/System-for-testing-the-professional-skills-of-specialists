package db;

import java.sql.SQLException;
import java.util.ArrayList;

import org.testing_system.Authorization;

public class SQLAuthorizationAdmin implements ISQLAuthorizationAdmin {
    private static SQLAuthorizationAdmin instance;
    private DBConnection dbConnection;

    private SQLAuthorizationAdmin() throws SQLException, ClassNotFoundException {
        dbConnection = DBConnection.getInstance();
    }

    public static synchronized SQLAuthorizationAdmin getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new SQLAuthorizationAdmin();
        }
        return instance;
    }

    @Override
    public boolean check_admins(Authorization auth) {
        String SQL = "SELECT * FROM Admin WHERE login = '" + auth.getLogin() + "' && password = '" + auth.getPassword() + "'";
        ArrayList<String[]> result = DBConnection.getArrayResult(SQL); 
        
        if(result.isEmpty())
        {
            return false;
        }
        else
            return true;
    }

    
}
