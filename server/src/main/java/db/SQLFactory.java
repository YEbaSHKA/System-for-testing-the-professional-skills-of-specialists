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

    @Override
    public SQLAdmin get_admin() throws SQLException, ClassNotFoundException {
        return SQLAdmin.getInstance();
    }

    @Override
    public SQLTopic get_topics() throws SQLException, ClassNotFoundException {
        return SQLTopic.getInstance();
    }

    @Override
    public SQLTest get_tests() throws SQLException, ClassNotFoundException {
        return SQLTest.getInstance();
    }

    @Override
    public SQLQuestion get_question() throws SQLException, ClassNotFoundException {
        return SQLQuestion.getInstance();
    }

    @Override
    public SQLTestsEmployee get_tests_employee() throws SQLException, ClassNotFoundException {
        return SQLTestsEmployee.getInstance();
    }
    
}
