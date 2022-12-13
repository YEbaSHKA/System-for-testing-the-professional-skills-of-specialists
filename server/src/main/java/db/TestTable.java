package db;

import java.sql.SQLException;
import java.util.ArrayList;

import org.testing_system.Test;
import org.testing_system.Topic;

public class TestTable implements ITestTable
{
    private static TestTable instance;
    private TestingSystemDB dbConnection;

    private TestTable() throws SQLException, ClassNotFoundException {
        dbConnection = dbConnection.getInstance();
    }

    public static synchronized TestTable getInstance() throws SQLException, ClassNotFoundException 
    {
        if (instance == null) 
        {
            instance = new TestTable();
        }
        return instance;
    }

    @Override
    public ArrayList<Test> get_tests_by_topic_and_type(String topic, String type) {
        String SQL = "SELECT * FROM Tests WHERE topic = '" + topic + "' && type = '" + type + "'";
        ArrayList<String[]> result = dbConnection.getArrayResult(SQL);
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

    @Override
    public boolean update(Test test) 
    {
        String SQL = "UPDATE Tests SET name = '" + test.getName() + "', topic = '" 
                    + test.getTopic_name() + "', type = '" + test.getType() 
                    + "' WHERE id = " + test.getId();
        return dbConnection.insert_values(SQL);
    }

    @Override
    public boolean insert(Test test) 
    {
        String SQL = "INSERT INTO Tests(name, topic, type) Values('" 
                    + test.getName() + "', '" + test.getTopic_name() + "', '" 
                    + test.getType() + "')";
        return dbConnection.insert_values(SQL);
    }

    @Override
    public boolean check(Test test) {
        String SQL = "SELECT * FROM Tests WHERE name = '" + test.getName() + "'";

        ArrayList<String[]> result = dbConnection.getArrayResult(SQL);
        return result.isEmpty();
    }

    @Override
    public boolean delete(Test test) {
        String SQL = "DELETE FROM Tests WHERE id = " + test.getId();
        return dbConnection.insert_values(SQL);
    }

    @Override
    public Test get_test_by_name(String name) {
        String SQL = "SELECT * FROM Tests WHERE name = '" + name + "'";
        ArrayList<String[]> result = dbConnection.getArrayResult(SQL);

        Test test = new Test();
        for (String[] item : result) 
        {
            test.setId(Integer.parseInt(item[0]));
            test.setName(item[1]);
            test.setTopic_name(item[2]);
            test.setType(item[3]);    
        }

        return test;
    }

    @Override
    public ArrayList<Test> get_mandatory_tests(int id_employee) {
        String SQL = "SELECT Tests.* FROM Tests, testEmployees WHERE Tests.id = testEmployees.id_test && testEmployees.id_employee = " + id_employee + " && testEmployees.result IS NULL";
        ArrayList<String[]> result = dbConnection.getArrayResult(SQL);
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
