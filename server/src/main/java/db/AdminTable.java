package db;

import java.sql.SQLException;
import java.util.ArrayList;

import org.testing_system.Admin;

public class AdminTable implements IAdminTable
{
    private static AdminTable instance;
    private TestingSystemDB dbConnection;

    private AdminTable() throws SQLException, ClassNotFoundException {
        dbConnection = dbConnection.getInstance();
    }

    public static synchronized AdminTable getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new AdminTable();
        }
        return instance;
    }


    @Override
    public Admin getAdmin(String login) 
    {
        String SQL = "SELECT * FROM Admin WHERE login = '" + login + "'";
        ArrayList<String[]> result = dbConnection.getArrayResult(SQL);
        Admin admin = new Admin();
        for (String[] item : result) {
            admin.setId(Integer.parseInt(item[0]));
            admin.setLogin(item[1]);
            admin.setPassword(item[2]);
        }
        return admin;
    }
    
}
