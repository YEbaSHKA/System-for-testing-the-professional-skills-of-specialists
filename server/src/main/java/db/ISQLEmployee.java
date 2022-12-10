package db;

import org.testing_system.Employee;

public interface ISQLEmployee {
    boolean insert(Employee employee);
    Employee getEmployee(String login);
}
