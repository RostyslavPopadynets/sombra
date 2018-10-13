package rostyslav.popadynets.sombra.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyslav.popadynets.sombra.entity.Books;
import rostyslav.popadynets.sombra.repository.BookRepository;
import rostyslav.popadynets.sombra.service.BookService;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void addBook(Books book) {
        bookRepository.save(book);
    }

    @Override
    public Books findBookById(Long bookid) {
        Books book = null;
        try {
            book = bookRepository.findBooksById(bookid);
        } catch (Exception e) {
            System.err.println("Error");
        }
        return book;
    }

    @Override
    public List<Books> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public void deleteBook(Long bookid) {
        Books book = bookRepository.findBooksById(bookid);
        if (book != null) {
            bookRepository.delete(book);
        } else {
            System.err.println("Error");
        }
    }

    @Override
    public void updateBook(Books book) {
        bookRepository.save(book);
    }
}
