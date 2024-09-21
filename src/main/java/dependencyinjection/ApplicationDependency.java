package dependencyinjection;

import dependencyinjection.task.Executor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ApplicationDependency {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ApplicationDependency.class, args);

        Executor executor = context.getBean(Executor.class);
        executor.execute();
    }
}
