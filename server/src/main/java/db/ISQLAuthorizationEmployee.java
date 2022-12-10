package db;

import org.testing_system.Admin;
import org.testing_system.Employee;

public interface ISQLAuthorizationEmployee {
    boolean check_availability(Employee employee);
}
