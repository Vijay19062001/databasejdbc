package springdemo.databasejdbc.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springdemo.databasejdbc.entities.Books;
import springdemo.databasejdbc.entities.Users;
import springdemo.databasejdbc.exception.basicexception.BookNotFoundException;
import springdemo.databasejdbc.model.RetentionModel;
import springdemo.databasejdbc.service.servicesimpl.RetentionService;


import java.util.List;

@RestController
@RequestMapping("/api/retention")
//@Validated
public class RetentionController {

    private  RetentionService retentionService;

    @Autowired
    public RetentionController(RetentionService retentionService) {
        this.retentionService = retentionService;
    }


    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public RetentionModel createRetention(
            @Valid @RequestBody RetentionModel retentionModel) {

        // Extract userId from the retentionModel
        Integer userId = Integer.parseInt(retentionModel.getUserId());

        return retentionService.createRetention(retentionModel, userId);
    }




    // Get a single retention record by ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK) // Set status to 200 OK
    public RetentionModel getRetentionById(@PathVariable int id) {
        RetentionModel retention = retentionService.getRetentionById(id);
        if (retention == null) {
            throw new RuntimeException("Retention with id " + id + " not found");
        }
        return retention;
    }


    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<RetentionModel> getAllRetention() {
        return retentionService.getAllRetention(); // Return list of books directly
    }


    // Update retention
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK) // Set status to 200 OK for successful updates
    public RetentionModel updateRetention(@PathVariable int id, @Valid @RequestBody RetentionModel retentionModel) {
        RetentionModel updatedRetention = retentionService.updateRetention(id, retentionModel);
        if (updatedRetention == null) {
            throw new RuntimeException("Retention with id " + id + " not found");
        }
        return updatedRetention;
    }


    // Delete a book by ID
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRetention(@PathVariable int id) {
        RetentionModel retentionEntity = retentionService.getRetentionById(id);

        if (retentionEntity == null) {
            throw new BookNotFoundException("Books with id " + id + " not found");
        }

        retentionService.deleteRetention(id);
    }


}
