package db;

import java.sql.SQLException;
import java.util.ArrayList;

import org.testing_system.Employee;

public class EmployeeTable implements IEmployeeTable {

    private static EmployeeTable instance;
    private TestingSystemDB dbConnection;

    private EmployeeTable() throws SQLException, ClassNotFoundException {
        dbConnection = dbConnection.getInstance();
    }

    public static synchronized EmployeeTable getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new EmployeeTable();
        }
        return instance;
    }


    @Override
    public boolean insert(Employee employee) 
    {
        String SQL = "INSERT INTO Employee(login, password, full_name) VALUES('" +
                        employee.getLogin() + "', '" + employee.getPassword() + "', '" + employee.getFull_name() + "')";
        return dbConnection.insert_values(SQL);
    }

    @Override
    public Employee getEmployee(String login) {
        String SQL = "SELECT * FROM Employee WHERE login = '" + login + "'";
        ArrayList<String[]> result = dbConnection.getArrayResult(SQL);
        Employee employee = new Employee();
        for (String[] item : result) {
            employee.setId(Integer.parseInt(item[0]));
            employee.setLogin(item[1]);
            employee.setPassword(item[2]);
            employee.setFull_name(item[3]);
        }
        return employee;
    }

    @Override
    public Employee get_employee_by_id(int id) {
        String SQL = "SELECT * FROM Employee WHERE id = " + id;
        
        ArrayList<String[]> result = dbConnection.getArrayResult(SQL);
        Employee employee = new Employee();

        for (String[] item : result) 
        {
            employee.setId(id);
            employee.setLogin(item[1]);
            employee.setPassword(item[2]);
            employee.setFull_name(item[3]);
        }

        return employee;
    }

    @Override
    public boolean update_within_pass(Employee employee) 
    {
        String SQL = "UPDATE Employee SET login = '" + employee.getLogin() + "', full_name = '" + employee.getFull_name() + "' WHERE id = " + employee.getId();

        return dbConnection.insert_values(SQL);
    }

    @Override
    public boolean update(Employee employee) {
        String SQL = "UPDATE Employee SET login = '" + employee.getLogin() + "', full_name = '" + employee.getFull_name() + "', password = '" + employee.getPassword() + "' WHERE id = " + employee.getId();
        
        return dbConnection.insert_values(SQL);
    }

    @Override
    public boolean check_pass(Employee employee) {
        String SQL = "SELECT * FROM Employee WHERE id = '" + employee.getId() + "' && password = '" + employee.getPassword() + "'";

        ArrayList<String[]> result = dbConnection.getArrayResult(SQL);
        return !result.isEmpty();
    }

    @Override
    public ArrayList<Employee> get_employees() {
        String SQL = "SELECT * FROM Employee";
        ArrayList<String[]> result = dbConnection.getArrayResult(SQL);

        ArrayList<Employee> employees = new ArrayList<>();
        for (String[] items : result) 
        {
            Employee employee = new Employee();
            employee.setId(Integer.parseInt(items[0]));
            employee.setLogin(items[1]);
            employee.setPassword(items[2]);
            employee.setFull_name(items[3]);
            employees.add(employee);
        }
        return employees;
    }

    @Override
    public boolean delete(Employee employee) {
        String SQL = "DELETE FROM Employee WHERE id = " + employee.getId();
        return dbConnection.insert_values(SQL);
    }
    
}
