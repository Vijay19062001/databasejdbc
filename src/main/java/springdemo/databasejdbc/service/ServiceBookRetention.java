package springdemo.databasejdbc.service;

import springdemo.databasejdbc.model.BookRetentionModel;

import java.util.List;

public interface ServiceBookRetention {

    public List<BookRetentionModel> getBookWithRetention(Integer bookId);



    }
