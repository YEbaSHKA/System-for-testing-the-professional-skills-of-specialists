package db;

import java.sql.SQLException;
import java.util.ArrayList;

import org.testing_system.Test;
import org.testing_system.Topic;

public class SQLTest implements ISQLTest 
{
    private static SQLTest instance;
    private DBConnection dbConnection;

    private SQLTest() throws SQLException, ClassNotFoundException {
        dbConnection = DBConnection.getInstance();
    }

    public static synchronized SQLTest getInstance() throws SQLException, ClassNotFoundException 
    {
        if (instance == null) 
        {
            instance = new SQLTest();
        }
        return instance;
    }

    @Override
    public ArrayList<Test> get_tests_by_topic_and_type(String topic, String type) {
        String SQL = "SELECT * FROM Tests WHERE topic = '" + topic + "' && type = '" + type + "'";
        ArrayList<String[]> result = DBConnection.getArrayResult(SQL);
        ArrayList<Test> tests = new ArrayList<>();
        
        for (String[] items : result) 
        {
            Test test = new Test();
            test.setId(Integer.parseInt(items[0]));
            test.setName(items[1]);
            test.setTopic_name(items[2]);
            test.setType(items[3]);
            tests.add(test);
        }

        return tests;
    }
    
}
