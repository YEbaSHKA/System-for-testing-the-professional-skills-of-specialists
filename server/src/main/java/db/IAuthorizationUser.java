package db;

import org.testing_system.Admin;
import org.testing_system.Employee;

public interface IAuthorizationUser 
{
    boolean check_admins(Admin admin);
    boolean check_availability(Employee employee);
}
