package db;

import java.sql.SQLException;

public class SQLFactory extends AbstractFactory {

    @Override
    public SQLAuthorizationEmployee check_availability() throws SQLException, ClassNotFoundException {
        return SQLAuthorizationEmployee.getInstance();
    }

    @Override
    public SQLAuthorizationAdmin check_admins() throws SQLException, ClassNotFoundException {
        return SQLAuthorizationAdmin.getInstance();
    }

    @Override
    public SQLEmployee get_employee() throws SQLException, ClassNotFoundException {
        return SQLEmployee.getInstance();
    }
    
}
