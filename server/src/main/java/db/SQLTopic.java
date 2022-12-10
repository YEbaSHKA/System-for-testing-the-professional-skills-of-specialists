package db;

import java.sql.SQLException;
import java.util.ArrayList;

import org.testing_system.Topic;

public class SQLTopic implements ISQLTopic{
    private static SQLTopic instance;
    private DBConnection dbConnection;

    private SQLTopic() throws SQLException, ClassNotFoundException {
        dbConnection = DBConnection.getInstance();
    }

    public static synchronized SQLTopic getInstance() throws SQLException, ClassNotFoundException 
    {
        if (instance == null) 
        {
            instance = new SQLTopic();
        }
        return instance;
    }

    @Override
    public ArrayList<Topic> get_topics() 
    {
        String SQl = "SELECT * FROM Topics";
        ArrayList<String[]> sql_result = DBConnection.getArrayResult(SQl);
        ArrayList<Topic> topics = new ArrayList<>();
        for (String[] items : sql_result) 
        {
            Topic topic = new Topic();
            topic.setName(items[0]);
            topics.add(topic);
        }

        return topics;
    }
}
