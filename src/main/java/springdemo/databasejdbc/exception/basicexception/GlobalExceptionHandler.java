package springdemo.databasejdbc.exception.basicexception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import springdemo.databasejdbc.exception.basicexception.constants.ErrorResponse;
import springdemo.databasejdbc.model.RetentionModel;
import springdemo.databasejdbc.service.servicesimpl.AuditService;

import java.time.ZonedDateTime;


@ControllerAdvice
public class GlobalExceptionHandler
{

    private final AuditService auditService;

    @Autowired
    public GlobalExceptionHandler(AuditService auditService) {
        this.auditService = auditService;
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request)
    {
        RetentionModel retentionModel = new RetentionModel();

        retentionModel.setName((String) ex.getFieldValue("name"));

        StringBuilder errorMessages = new StringBuilder();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errorMessages.append(error.getDefaultMessage()).append("; ")
        );
        String apiName = request.getRequestURI(); // Use the request URI as the API name


        auditService.AuditLog(
                "Name : "+ retentionModel.getName(),
                "API : "+ apiName,
                errorMessages.toString(),
                ZonedDateTime.now()
        );

        ErrorResponse errorResponse = new ErrorResponse() {
            @Override
            public String getMessage() {
                return errorMessages.toString();
            }

            @Override
            public int getStatus() {
                return HttpStatus.BAD_REQUEST.value();
            }
        };

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BasicValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleBasicValidationException(BasicValidationException ex) {

        ErrorResponse errorResponse = new ErrorResponse() {
            @Override
            public String getMessage()
            {
                return ex.getMessage();
            }

            @Override
            public int getStatus() {
                return HttpStatus.BAD_REQUEST.value();

            }
        };

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}





