package db;

import java.util.ArrayList;

import org.testing_system.ResultOfTest;

public interface ISQLResultOfTest 
{
    ArrayList<ResultOfTest> get_results(int id);
}
