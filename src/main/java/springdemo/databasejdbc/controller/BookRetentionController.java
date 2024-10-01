package springdemo.databasejdbc.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springdemo.databasejdbc.model.BookRetentionModel;
import springdemo.databasejdbc.model.RetentionModel;
import springdemo.databasejdbc.service.servicesimpl.BookRetentionService;

import java.time.LocalDate;
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


    @GetMapping("/api/book-dateRetention")
    public ResponseEntity<List<BookRetentionModel>> getBookWithRetention(
            @RequestParam(value = "book_id", required = false, defaultValue = "0") Long id,
            @RequestParam(value = "borrow_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate borrowDate,
            @RequestParam(value = "return_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returnDate) {



        List<BookRetentionModel> bookRetentionModel = bookRetentionService.getBooksWithDateRecords(id, borrowDate, returnDate);

        return ResponseEntity.ok(bookRetentionModel);
    }


}
