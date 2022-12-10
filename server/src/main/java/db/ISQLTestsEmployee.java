package db;

import org.testing_system.TestEmployee;

public interface ISQLTestsEmployee 
{
    boolean check_availability(int id_test, int id_employee);
    boolean insert(TestEmployee test_employee);
    boolean update(TestEmployee test_employee);
}
