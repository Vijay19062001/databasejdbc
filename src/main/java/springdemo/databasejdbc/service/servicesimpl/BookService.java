package springdemo.databasejdbc.service.servicesimpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import springdemo.databasejdbc.entities.Books;
import springdemo.databasejdbc.exception.basicexception.BookNotFoundException;
import springdemo.databasejdbc.mapper.BookMapper;
import springdemo.databasejdbc.mapper.RetentionMapper;
import springdemo.databasejdbc.model.BookModel;
import springdemo.databasejdbc.repository.BookRepository;
import springdemo.databasejdbc.service.ServiceBook;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService implements ServiceBook {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private RetentionMapper retentionMapper;


    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<BookModel> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toModel)
                .collect(Collectors.toList());
    }


    @Override
    public BookModel getBookById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toModel)
                .orElse(null);
    }

    @Override
    public BookModel createBook(BookModel bookModel) {
        Books book = bookMapper.toEntity(bookModel);
        Books saved = bookRepository.save(book);

        return bookMapper.toModel(saved);
    }

    @Override
    public BookModel updateBook(Long id, BookModel bookModel) {
        Books book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Books with id " + id + " not found"));

        book.setBookName(bookModel.getBookName());
        book.setAuthor(bookModel.getAuthor());
        book.setPrices(Double.valueOf(bookModel.getPrices()));
        book.setPublisherCompany(bookModel.getPublisherCompany());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        try {
            LocalDate publishDate = LocalDate.parse(bookModel.getPublishDate(), formatter);
            book.setPublishDate(publishDate);

            LocalDate createdDate = LocalDate.parse(bookModel.getCreatedDate(), formatter);
            book.setCreatedDate(createdDate);

            LocalDate updatedDate = LocalDate.parse(bookModel.getUpdatedDate(), formatter);
            book.setUpdatedDate(updatedDate);

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format.Expected format is yyyyMMdd", e);
        }
        Books updatesBooks = bookRepository.save(book);
        return bookMapper.toModel(updatesBooks);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Page<BookModel> getBooksWithPagingAndSorting(Integer pageNo, Integer pageSize, String sortBy, String sortDir,String searchText,String authorName) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Books> query = cb.createQuery(Books.class);
        Root<Books> book = query.from(Books.class);
        List<Predicate> predicates = new ArrayList<>();

        if (searchText != null && !searchText.isEmpty()) {
            predicates.add(cb.like(cb.lower(book.get("bookName")),  searchText.toLowerCase() + "%"));
        }

        if(authorName != null && !authorName.isEmpty()){
            predicates.add(cb.like(cb.lower(book.get("author")), authorName.toLowerCase()+"%"));
        }

        query.where(predicates.toArray(new Predicate[0]));

        query.orderBy(sort.stream()
                .map(order -> order.isAscending() ? cb.asc(book.get(order.getProperty())) : cb.desc(book.get(order.getProperty())))
                .collect(Collectors.toList()));

        // Execute the main query
        List<Books> books = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        List<BookModel>bookModels =books.stream()
                .map(bookMapper::toModel)
                .collect(Collectors.toList());

        return new PageImpl<>(bookModels, pageable,bookModels.size());
    }

    @Override
    public List<Books> searchBooks(String bookName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Books> criteriaQuery = criteriaBuilder.createQuery(Books.class);
        Root<Books> bookRoot = criteriaQuery.from(Books.class);           // The root of the query (the entity you are querying)
        Predicate bookNamePredicate = criteriaBuilder.like(          // Create a Predicate for the condition "bookName LIKE %bookName%"
                criteriaBuilder.lower(bookRoot.get("bookName")),
                  bookName.toLowerCase() + "%"
        );
        criteriaQuery.where(bookNamePredicate);        // Apply the Predicate to the query
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
 }
