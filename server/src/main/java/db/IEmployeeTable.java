package db;

import java.util.ArrayList;

import org.testing_system.Employee;

public interface IEmployeeTable 
{
    boolean insert(Employee employee);
    Employee getEmployee(String login);
    Employee get_employee_by_id(int id);
    boolean update_within_pass(Employee employee);
    boolean update(Employee employee);
    boolean check_pass(Employee employee);
}
