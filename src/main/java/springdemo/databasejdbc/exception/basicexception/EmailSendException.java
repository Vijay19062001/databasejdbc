package springdemo.databasejdbc.exception.basicexception;

import jakarta.mail.MessagingException;

public class EmailSendException extends RuntimeException {

    public EmailSendException(String message, MessagingException e) {
        super(message);
    }
}
