package db;

import org.testing_system.TestEmployee;

public interface ITestEmployeeTable
{
    boolean check_availability(int id_test, int id_employee);
    boolean insert(TestEmployee test_employee);
    boolean update(TestEmployee test_employee);
}
