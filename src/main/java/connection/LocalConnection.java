package connection;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Component
public class LocalConnection implements DBConnection {
    private static Connection basicConnection = null;

    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        if (basicConnection == null) {
            Class.forName("com.mysql.jdbc.Driver");
            Properties properties=new Properties();
            properties.setProperty("user", "root");
            properties.setProperty("password","root");
            /*
            настройки указывающие о необходимости конвертировать данные из Unicode
	        в UTF-8, который используется в нашей таблице для хранения данных
            */
            properties.setProperty("useUnicode","true");
            properties.setProperty("characterEncoding","UTF-8");
            //String url = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
            String url = "jdbc:mysql://localhost:3306/schedule";
            basicConnection = DriverManager.getConnection(url, properties);
            System.out.println("Connected to MYDB");
            System.out.println(url);
        }
        return basicConnection;
    }

    @Override
    public Connection getConnection(final String username, final String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Properties properties=new Properties();
        properties.setProperty("user",username);
        properties.setProperty("password",password);
        /*
         настройки указывающие о необходимости конвертировать данные из Unicode
	     в UTF-8, который используется в нашей таблице для хранения данных
         */
        properties.setProperty("useUnicode","true");
        properties.setProperty("characterEncoding","UTF-8");
       // String url = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
        String url = "jdbc:mysql://localhost:3306/schedule";
        Connection extendedConnection = DriverManager.getConnection(url, properties);
        System.out.println(url);
        System.out.println("Connected to MYDB - extended");
        return extendedConnection;
    }

}
