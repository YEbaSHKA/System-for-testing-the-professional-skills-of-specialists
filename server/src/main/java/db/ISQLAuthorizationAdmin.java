package db;

import org.testing_system.Authorization;

public interface ISQLAuthorizationAdmin {
    boolean check_admins(Authorization auth);
}
