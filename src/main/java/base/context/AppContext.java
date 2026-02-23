package base.context;

import base.config.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppContext {

    private static final ApplicationContext context =
            new AnnotationConfigApplicationContext(AppConfig.class);

    private AppContext() {}

    public static ApplicationContext getContext() {
        return context;
    }


}
