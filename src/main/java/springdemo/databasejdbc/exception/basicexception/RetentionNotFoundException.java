package springdemo.databasejdbc.exception.basicexception;

public class RetentionNotFoundException extends RuntimeException {
    public RetentionNotFoundException(String message) {
        super(message);
    }
}
