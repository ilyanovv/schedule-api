package connection.context;

import connection.config.DBConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Context {
    private static ApplicationContext applicationContext = null;

    public static ApplicationContext getContext() {
        if (applicationContext == null) {
            applicationContext = new AnnotationConfigApplicationContext(DBConfig.class);
        }
        return applicationContext;
    }
}
