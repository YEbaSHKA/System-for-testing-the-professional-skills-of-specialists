package db;

import java.sql.SQLException;
import java.util.ArrayList;

import org.testing_system.Admin;

public class SQLAdmin implements ISQLAdmin
{
    private static SQLAdmin instance;
    private DBConnection dbConnection;

    private SQLAdmin() throws SQLException, ClassNotFoundException {
        dbConnection = DBConnection.getInstance();
    }

    public static synchronized SQLAdmin getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new SQLAdmin();
        }
        return instance;
    }


    @Override
    public Admin getAdmin(String login) 
    {
        String SQL = "SELECT * FROM Admin WHERE login = '" + login + "'";
        ArrayList<String[]> result = DBConnection.getArrayResult(SQL);
        Admin admin = new Admin();
        for (String[] item : result) {
            admin.setId(Integer.parseInt(item[0]));
            admin.setLogin(item[1]);
            admin.setPassword(item[2]);
        }
        return admin;
    }
    
}
