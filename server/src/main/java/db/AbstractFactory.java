package db;

import java.sql.SQLException;

public abstract class AbstractFactory {
    public abstract SQLAuthorizationEmployee check_availability() throws SQLException, ClassNotFoundException;
    public abstract SQLTestsEmployee get_tests_employee() throws SQLException, ClassNotFoundException;
    public abstract SQLAuthorizationAdmin check_admins() throws SQLException, ClassNotFoundException;
    public abstract SQLResultOfTest get_result_of_test() throws SQLException, ClassNotFoundException;
    public abstract SQLQuestion get_question() throws SQLException, ClassNotFoundException;
    public abstract SQLEmployee get_employee() throws SQLException, ClassNotFoundException;
    public abstract SQLTopic get_topics() throws SQLException, ClassNotFoundException;
    public abstract SQLAdmin get_admin() throws SQLException, ClassNotFoundException;
    public abstract SQLTest get_tests() throws SQLException, ClassNotFoundException;
}
