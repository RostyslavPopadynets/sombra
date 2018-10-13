package rostyslav.popadynets.sombra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rostyslav.popadynets.sombra.domain.Product;
import rostyslav.popadynets.sombra.entity.Authors;
import rostyslav.popadynets.sombra.entity.BookAuthor;
import rostyslav.popadynets.sombra.entity.Books;
import rostyslav.popadynets.sombra.repository.AuthorRepository;
import rostyslav.popadynets.sombra.repository.BookRepository;
import rostyslav.popadynets.sombra.service.AuthorService;
import rostyslav.popadynets.sombra.service.BookAuthorService;
import rostyslav.popadynets.sombra.service.BookService;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

@RestController
public class BaseController {

    @Autowired
    AuthorService authorService;

    @Autowired
    BookService bookService;

    @Autowired
    BookAuthorService bookAuthorService;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    private List<Books>  getBooksAuthoreMoreOneBook() {
        List<Books> books = new LinkedList<>();
        List<Long> l = new LinkedList<>();
        Set<Long> booksWith1moreAutor = new HashSet<>();
        List<BookAuthor> bookAuthors = bookAuthorService.findAllBookAuthors();

        for(BookAuthor ba : bookAuthors) {
            l.add(ba.getAuthorId());
        }
        int n = 1;
        int i = 1;
        for (Long num : l) {
            for (; i < l.size(); i++) {
                if (num == l.get(i)) {
                    booksWith1moreAutor.add(l.get(i));
                }
            }
            i = 1 + n;
            n++;
        }
        for (Long o : booksWith1moreAutor) {
            for(BookAuthor ba : bookAuthors) {
                if (o == ba.getAuthorId()) {
                    books.add(bookService.findBookById(ba.getBookId()));
                }
            }
        }
        return books;
    }

    @GetMapping("/authorsOlder55")
    public ResponseEntity<List<Authors>> getAuthorsOlder55Years() {
        List<Authors> authors = new LinkedList<>();
        List<Authors> authorsSorted = new LinkedList<>();
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sombra",
                    "Rostyslav", "28041996edge");
            String query = "Select * from authors order by born";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Authors author = new Authors();
                author.setId(resultSet.getLong(1));
                author.setBorn(resultSet.getString(2));
                author.setGenre(resultSet.getString(3));
                author.setName(resultSet.getString(4));
                authors.add(author);
                author = null;
            }
            for (Authors a : authors) {
                if (a.getBorn() != null) {
                    int year = Integer.parseInt(a.getBorn().substring(0, 4));
                    int month = Integer.parseInt(a.getBorn().substring(5, 7));
                    int day = Integer.parseInt(a.getBorn().substring(8, 10));
                    int yearNow = LocalDate.now().getYear();
                    int monthNow = LocalDate.now().getMonthValue();
                    int dayNow = LocalDate.now().getDayOfMonth();
                    if ((yearNow - year) > 55) {
                        authorsSorted.add(a);
                    }
                    if ((yearNow - year) == 55) {
                        if (monthNow > month) {
                            authorsSorted.add(a);
                        }
                        if (monthNow == month) {
                            if (dayNow > day) {
                                authorsSorted.add(a);
                            }
                        }
                    }
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<List<Authors>>(authorsSorted, HttpStatus.OK);
    }

    @GetMapping("/booksAuthoreMoreOneBooks")
    public ResponseEntity<List<Books>> getBooksAuthoreMoreOneBooks() {
        return new ResponseEntity<List<Books>>(getBooksAuthoreMoreOneBook(), HttpStatus.OK);
    }

    @GetMapping("/authorWithMoreBooks")
    public ResponseEntity<Set<Authors>> getAutorWithMoreBooks() {
        List<Books> books = getBooksAuthoreMoreOneBook();
        List<BookAuthor> bookAuthors = bookAuthorService.findAllBookAuthors();
        List<Long> booksId = new ArrayList<>();
        Map<Long, Integer> map = new HashMap<>();
        List<Long> authorsId = new ArrayList<>();
        List<Authors> authorsBD = authorService.findAllAuthor();
        Set<Authors> authors = new HashSet<>();

        for (Books b : books) {
            for (BookAuthor ba : bookAuthors) {
                if (ba.getBookId() == b.getId()) {
                    booksId.add(ba.getAuthorId());
                }
            }
        }

        int n = 0;
        for (Long o : booksId) {
            for(Long o2 : booksId) {
                if (o == o2) {
                    n++;
                }
                map.put(o, n);
            }
            n = 0;
        }

        long idAuthorWithMaxBooks;
        int max = 0;
        for (Map.Entry<Long, Integer> m : map.entrySet()) {
            max = m.getValue();
            if (m.getValue() > max) {
                max = m.getValue();
            }
        }

        for (Map.Entry<Long, Integer> m : map.entrySet()) {
            if (m.getValue() == max) {
                authorsId.add(m.getKey());
            }
        }

        for (Long l : authorsId) {
            for (Authors a : authorsBD) {
                if (a.getId() == l) {
                    authors.add(a);
                }
            }
        }

        return new ResponseEntity<Set<Authors>>(authors, HttpStatus.OK);
    }

    @GetMapping("/numberBooksByGenre")
    public ResponseEntity<Map<String, Integer>> getNumberBooksByGenre() {
        List<Books> books = bookService.findAllBooks();
        Map<String, Integer> map = new HashMap<>();
        for (Books b : books) {
                map.put(b.getGenre(), 0);
        }
        for(Map.Entry<String, Integer> m : map.entrySet()) {
            for (Books b : books) {
                if (m.getKey().equals(b.getGenre())) {
                    map.put(m.getKey(), m.getValue() + 1);
                }
            }
        }

        return new ResponseEntity<Map<String, Integer>>(map, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Product product) {

        Authors author = new Authors();
        author.setName(product.getAuthorName());
        author.setBorn(product.getAuthorBorn());
        author.setGenre(product.getAuthorGenre());
        authorService.addAuthor(author);

        Books book = new Books();
        book.setName(product.getBookName());
        book.setGenre(product.getBookGenre());
        book.setPublished(product.getBookPublished());
        book.setRating(product.getBookRating());
        bookService.addBook(book);

        BookAuthor bookAuthor = new BookAuthor();
        bookAuthor.setAuthorId(authorRepository.findAuthorsByName(author.getName()).getId());
        bookAuthor.setBookId(bookRepository.findBooksByName(book.getName()).getId());
        bookAuthorService.addBookAuthor(bookAuthor);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/books")
    public ResponseEntity<List<Books>> getAllBooks() {
        return new ResponseEntity<List<Books>>(bookService.findAllBooks(), HttpStatus.OK);
    }

    @GetMapping("/authors")
    public ResponseEntity<List<Authors>> getAllAuthors() {
        return new ResponseEntity<List<Authors>>(authorService.findAllAuthor(), HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Books> getBookById(@PathVariable("id") Long id) {
        Books book = bookService.findBookById(id);
        if (book == null) {
            System.err.println("Книга з таким id не знайдена!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Books>(book, HttpStatus.OK);
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<Authors> getAuthorById(@PathVariable("id") Long id) {
        Authors author = authorService.findAuthorById(id);
        if (author == null) {
            System.err.println("Автора з таким id не знайдено!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Authors>(author, HttpStatus.OK);
    }

    @PutMapping("books/{id}")
    public ResponseEntity<Void> updateBook(@PathVariable("id") Long id,
                                              @RequestBody Books book
    ){
        Books bookNew = bookService.findBookById(id);
        if (bookNew != null) {
            bookNew.setName(book.getName());
            bookNew.setGenre(book.getGenre());
            bookNew.setPublished(book.getPublished());
            bookNew.setRating(book.getRating());
            bookService.updateBook(bookNew);

            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("authors/{id}")
    public ResponseEntity<Void> updateAuthor(@PathVariable("id") Long id,
                                           @RequestBody Authors author
    ){
        Authors authorNew = authorService.findAuthorById(id);
        if (authorNew != null) {
            authorNew.setName(author.getName());
            authorNew.setGenre(author.getGenre());
            authorNew.setBorn(author.getBorn());
            authorService.updateAuthor(authorNew);

            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id) {
        Books bookNew = bookService.findBookById(id);
        if (bookNew != null) {
            bookService.deleteBook(bookNew.getId());
            bookAuthorService.deleteBookAuthorByBook(id);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("authors/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable("id") Long id) {
        Authors authorNew = authorService.findAuthorById(id);
        if (authorNew != null) {
            authorService.deleteAuthor(authorNew.getId());
            bookAuthorService.deleteBookAuthorByAuthor(id);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    }

}
