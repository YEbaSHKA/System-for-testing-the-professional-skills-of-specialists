package db;

import java.util.ArrayList;

import org.testing_system.Employee;

public interface ISQLEmployee {
    boolean insert(Employee employee);
    Employee getEmployee(String login);
    Employee get_employee_by_id(int id);
}
