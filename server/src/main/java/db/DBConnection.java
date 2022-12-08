package db;

import java.io.ObjectInputFilter.Status;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class DBConnection {
    private static DBConnection instance;

    private String dbUser = "root";
    private String dbPass = "password";
    

    public static Connection dbConnection;

    public DBConnection() throws ClassNotFoundException, SQLException {
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        String connectionString = "jdbc:mysql://localhost/testing_system?serverTimezone=Europe/Moscow&useSSL=false";


        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
    }

    public static synchronized DBConnection getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }


    public static ArrayList<String[]> getArrayResult(String str) {
        
        ArrayList<String[]> masResult = new ArrayList<String[]>();
        
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(str);
            int count = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()) {
                String[] arrayString = new String[count];
                for (int i = 1;  i <= count; i++)
                    arrayString[i - 1] = resultSet.getString(i);

                masResult.add(arrayString);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return masResult;
    }

    public static boolean insert_values(String str) 
    {
        int count = 0;    
        try {
            Statement statement = dbConnection.createStatement();
            count = statement.executeUpdate(str);
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        if(count == 0)
        {
            return false;
        }
        else
            return true;
    }
}
