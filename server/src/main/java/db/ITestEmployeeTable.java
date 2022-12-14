package db;

import org.testing_system.TestEmployee;

public interface ITestEmployeeTable
{
    boolean check_availability(int id_test, int id_employee);
    boolean insert(TestEmployee test_employee);
    boolean insert_mandatory(int id_test, int id_employee);
    boolean update(TestEmployee test_employee);
    boolean update_result(String login, String test_name, int result);
    boolean update_result(int id_test, int id_employee);
    int get_count_of_mandatory_tests(int id_employee);
    int get_count_of_complete_mandatory_tests(int id_employee);
}
