package dependencyinjection.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Executor {

    private Example example;

    @Autowired
    public Executor(Example example) {
        this.example = example;
    }

    public void execute(){
        example.print();
    }
}
