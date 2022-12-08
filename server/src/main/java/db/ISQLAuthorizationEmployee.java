package db;

import org.testing_system.Authorization;

public interface ISQLAuthorizationEmployee {
    boolean check_availability(Authorization auth);
}
