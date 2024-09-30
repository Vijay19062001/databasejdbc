package springdemo.databasejdbc.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springdemo.databasejdbc.model.BookRetentionModel;
import springdemo.databasejdbc.service.servicesimpl.BookRetentionService;

import java.util.List;

@RestController
public class BookRetentionController {

    @Autowired
    private BookRetentionService bookRetentionService;

    @GetMapping("/api/book-retention")
    public ResponseEntity<List<BookRetentionModel>> getBookWithRetention(@RequestParam(value = "book_id", required = false, defaultValue = "0") Integer id) {

        List<BookRetentionModel> bookRetentionModel = bookRetentionService.getBookWithRetention(id);

        return ResponseEntity.ok(bookRetentionModel);

    }


}
