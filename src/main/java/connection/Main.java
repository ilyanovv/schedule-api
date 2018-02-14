package connection;

import connection.config.DBConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ApplicationContext context = new AnnotationConfigApplicationContext(DBConfig.class);
        DBConnection connection = context.getBean(DBConnection.class);
        connection.getConnection();
    }
}
