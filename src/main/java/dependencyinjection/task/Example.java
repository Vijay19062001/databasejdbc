package dependencyinjection.task;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Example {

   @Bean
    public void print(){
        System.out.println("Injection");

    }
}
