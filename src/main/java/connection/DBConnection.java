package connection;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;

public interface DBConnection {
    Connection getConnection() throws ClassNotFoundException, SQLException;
    Connection getConnection(final String username, final String password) throws ClassNotFoundException, SQLException;
}
