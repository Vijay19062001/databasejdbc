package springdemo.databasejdbc.service.servicesimpl;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import springdemo.databasejdbc.entities.Books;
import springdemo.databasejdbc.entities.RetentionEntity;
import springdemo.databasejdbc.entities.Users;
import springdemo.databasejdbc.exception.basicexception.BookNotFoundException;
import springdemo.databasejdbc.mapper.BookMapper;
import springdemo.databasejdbc.model.BookModel;
import springdemo.databasejdbc.repository.BookRepository;
import springdemo.databasejdbc.repository.RetentionRepository;
import springdemo.databasejdbc.repository.UserRepository;
import springdemo.databasejdbc.service.ServiceBook;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;
import jakarta.mail.internet.MimeMessage;

@Service
public class BookService implements ServiceBook{

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final RetentionRepository retentionRepository;
    private final UserRepository userRepository;
    @PersistenceContext
    private final EntityManager entityManager;
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Autowired
    public BookService(BookRepository bookRepository, BookMapper bookMapper, RetentionRepository retentionRepository, UserRepository userRepository, EntityManager entityManager, JavaMailSender mailSender, SpringTemplateEngine templateEngine, AuditService auditService) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.retentionRepository = retentionRepository;
        this.userRepository = userRepository;
        this.entityManager = entityManager;
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.auditService = auditService;
    }

    private final AuditService auditService;
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    @Override
    public List<BookModel> getAllBooks() {
        logger.info("Fetching all books from the repository");

        try {
            List<BookModel> books = bookRepository.findAll().stream()
                    .map(bookMapper::toModel)
                    .collect(Collectors.toList());

            logger.info("Successfully fetched {} books", books.size());
            return books;
        } catch (Exception e) {
            logger.error("Error occurred while fetching books: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch books", e); // Consider a custom exception in production
        }
    }

    @Override
    public BookModel getBookById(Long id) {
        logger.info("Fetching the book with Id: {}", id);

        BookModel book = bookRepository.findById(id)
                .map(bookMapper::toModel)
                .orElse(null);

        if (book != null) {
            logger.info("Successfully retrieved the book with Id: {}", id);
        } else {
            logger.warn("No book found with Id: {}", id);
        }

        return book;
    }

    @Override
    public BookModel createBook(BookModel bookModel,RetentionEntity retentionEntity) {
        logger.info("Creating the Books");

            Books book = bookMapper.toEntity(bookModel);

            Books saved = bookRepository.save(book);

            auditService.AuditLog(
                   retentionEntity .getName(),
                    "API Book Creation",
                    "Created Successfully",
                    ZonedDateTime.now()
            );

            logger.info("Created Successfully");
            return bookMapper.toModel(saved);

    }

    @Override
    public BookModel updateBook(Long id, BookModel bookModel) {
        logger.info("Update the Book with id: {}", id);

        Books book = bookRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Book not found with id: {}", id);
                    return new BookNotFoundException("Book with id " + id + " not found");
                });

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
            logger.error("Invalid date format for book update: {}", e.getMessage());
            throw new IllegalArgumentException("Invalid date format. Expected format is yyyyMMdd.", e);
        }

        Books updatedBooks = bookRepository.save(book);
        logger.info("Updated the Book successfully");


        auditService.AuditLog(
                book.getBookName(),
                "API Book Update",
                "Updated Successfully",
                ZonedDateTime.now()
        );

        return bookMapper.toModel(updatedBooks);
    }


    @Override
    public void deleteBook(Long id) {
        logger.info("Deleting the book with id: {}", id);
        bookRepository.deleteById(id);
        logger.info("Book with id: {} deleted successfully", id);
    }


    @Override
    public Page<BookModel> getBooksWithPagingAndSorting(Integer pageNo, Integer pageSize, String sortBy, String sortDir, String searchText, String authorName) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Books> query = cb.createQuery(Books.class);
        Root<Books> book = query.from(Books.class);
        List<Predicate> predicates = new ArrayList<>();

        if (searchText != null && !searchText.isEmpty()) {
            predicates.add(cb.like(cb.lower(book.get("bookName")), searchText.toLowerCase() + "%"));
        }

        if (authorName != null && !authorName.isEmpty()) {
            predicates.add(cb.like(cb.lower(book.get("author")), authorName.toLowerCase() + "%"));
        }

        query.where(predicates.toArray(new Predicate[0]));
        query.orderBy(sort.stream()
                .map(order -> order.isAscending() ? cb.asc(book.get(order.getProperty())) : cb.desc(book.get(order.getProperty())))
                .collect(Collectors.toList()));

        List<Books> books = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        List<BookModel> bookModels = books.stream()
                .map(bookMapper::toModel)
                .collect(Collectors.toList());

        return new PageImpl<>(bookModels, pageable, bookModels.size());
    }

    @Override
    public List<Books> searchBooks(String bookName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Books> criteriaQuery = criteriaBuilder.createQuery(Books.class);
        Root<Books> bookRoot = criteriaQuery.from(Books.class);
        Predicate bookNamePredicate = criteriaBuilder.like(
                criteriaBuilder.lower(bookRoot.get("bookName")),
                bookName.toLowerCase() + "%"
        );
        criteriaQuery.where(bookNamePredicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public void sendBookRetentionEmail(int id)
    {
        Books books = bookRepository.findById(Long.valueOf(id)).orElseThrow(() -> new RuntimeException("Pet not found with ID: " + id));

        List<Users> users = userRepository.findByUserId(id);
        List<RetentionEntity> retentionEntities = retentionRepository.findByBookId(id);

        Context context = new Context();
        context.setVariable("bookName", books.getBookName());
        context.setVariable("prices", books.getPrices());
        context.setVariable("userName", users.getFirst().getUserName());
        context.setVariable("userEmail", users.getFirst().getUserEmail());
        context.setVariable("retentionEntities", retentionEntities);

        String emailContent = templateEngine.process("EmailTemplate", context);

        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper help = new MimeMessageHelper(mailMessage);

        try
        {
            help.setTo(users.getFirst().getUserEmail());;
            help.setSubject("Book Retentions Information");
            help.setText(emailContent, true);
            mailSender.send(mailMessage);
        }
        catch (MessagingException e)
        {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    @Value("${scheduler.enabled}")
    private boolean isSchedulerEnabled;

    @Scheduled(cron = "${scheduler.cron}")
    public void scheduleDailyBookInfoEmail() {
        logger.info("Scheduling daily books info email for all books");

        if (!isSchedulerEnabled) {
            logger.info("Scheduler is disabled. Skipping daily books info email sending.");
            return;
        }

        logger.info("Starting scheduled task to send daily book retention emails.");

        try {
            List<Users> allUsers = userRepository.findAll();
            logger.info("Found {} users to send emails.", allUsers.size());

            for (Users user : allUsers) {
                try {
                    logger.info("Sending email to user: {}", user.getUserId());
                    sendBookRetentionEmail(user.getUserId());
                    logger.info("Successfully sent email to user: {}", user.getUserId());

                    // Log the successful email sending action
                    auditService.AuditLog(
                            "System",
                            "Scheduled Email Service",
                            "Sent daily book retention email successfully",
                            ZonedDateTime.now()
                    );

                } catch (Exception e) {
                    logger.error("Failed to send email to user: {}", user.getUserId(), e);
                }
            }

        } catch (Exception e) {
            logger.error("Unexpected error in scheduleDailyBookInfoEmail", e);
        }
    }


}

