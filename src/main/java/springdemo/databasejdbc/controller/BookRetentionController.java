package springdemo.databasejdbc.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springdemo.databasejdbc.model.BookRetentionModel;
import springdemo.databasejdbc.model.RetentionModel;
import springdemo.databasejdbc.service.servicesimpl.BookRetentionService;

import java.util.List;

@RestController
public class BookRetentionController {

    @Autowired
    private BookRetentionService bookRetentionService;

    @GetMapping("/api/book-retention")
    public ResponseEntity<List<BookRetentionModel>> getBookWithRetention(@RequestParam(value = "book_id", required = false, defaultValue = "0") Long id) {

        List<BookRetentionModel> bookRetentionModel = bookRetentionService.getBooksWithRetentions(id);

        return ResponseEntity.ok(bookRetentionModel);

    }

    @GetMapping("/api/book-DbStatus")
    public ResponseEntity<List<RetentionModel>>getBookDbStatus(@RequestParam (required=false)String status){


        List<RetentionModel>bookRecordStatus = bookRetentionService.getAllBookRecordRetentions(status);

        return ResponseEntity.ok(bookRecordStatus);
    }


}
