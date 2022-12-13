package db;

import java.sql.SQLException;
import java.util.ArrayList;

import org.testing_system.ResultOfTest;

public class ResultOfTestTable implements IResultOfTestTable {
    private static ResultOfTestTable instance;
    private TestingSystemDB dbConnection;

    private ResultOfTestTable() throws SQLException, ClassNotFoundException {
        dbConnection = dbConnection.getInstance();
    }

    public static synchronized ResultOfTestTable getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new ResultOfTestTable();
        }
        return instance;
    }

    @Override
    public ArrayList<ResultOfTest> get_results(int id) {
        String SQL = "SELECT Tests.topic, Tests.name, testEmployees.result FROM Tests, testEmployees WHERE Tests.id = testEmployees.id_test &&  testEmployees.id_employee = " + id + "&& testEmployees.result IS NOT NULL";

        ArrayList<String[]> result_sql = dbConnection.getArrayResult(SQL);

        ArrayList<ResultOfTest> results = new ArrayList<>();

        for (String[] items : result_sql) 
        {
            ResultOfTest result = new ResultOfTest();
            result.setId_employee(id);
            result.setTopic_name(items[0]);
            result.setTest_name(items[1]);
            result.setResult_of_test(Integer.parseInt(items[2]));
            results.add(result);
        }
        return results;
    }
}
