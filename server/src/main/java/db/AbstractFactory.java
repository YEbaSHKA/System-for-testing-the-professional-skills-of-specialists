package db;

import java.sql.SQLException;

public abstract class AbstractFactory {
    public abstract SQLAuthorizationEmployee check_availability() throws SQLException, ClassNotFoundException;
    public abstract SQLAuthorizationAdmin check_admins() throws SQLException, ClassNotFoundException;
    public abstract SQLEmployee get_employee() throws SQLException, ClassNotFoundException;
}
