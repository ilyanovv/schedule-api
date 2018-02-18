package connection.config;

import connection.DBConnection;
import connection.LocalConnection;
import connection.OpenshiftConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ComponentScan(basePackages = {
//        "connection"
//})
public class DBConfig {
    @Bean
    DBConnection dbConnection() {
        //return new OpenshiftConnection();
        return new LocalConnection();
    }
}
