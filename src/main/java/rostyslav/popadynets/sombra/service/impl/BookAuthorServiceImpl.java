package rostyslav.popadynets.sombra.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyslav.popadynets.sombra.entity.BookAuthor;
import rostyslav.popadynets.sombra.repository.BookAuthorRepository;
import rostyslav.popadynets.sombra.service.BookAuthorService;

import java.util.List;

@Service
public class BookAuthorServiceImpl implements BookAuthorService {

    @Autowired
    private BookAuthorRepository bookAuthorRepository;

    @Override
    public void addBookAuthor(BookAuthor bookAuthor) {
        bookAuthorRepository.save(bookAuthor);
    }

    @Override
    public List<BookAuthor> findAllBookAuthors() {
        return bookAuthorRepository.findAll();
    }

    @Override
    public void deleteBookAuthorByBook(Long bookId) {
        BookAuthor bookAuthor = bookAuthorRepository.findBookAuthorByBookId(bookId);
        if (bookAuthor != null) {
            bookAuthorRepository.delete(bookAuthor);
        } else {
            System.err.println("Error");
        }
    }

    @Override
    public void deleteBookAuthorByAuthor(Long authorId) {
        BookAuthor bookAuthor = bookAuthorRepository.findBookAuthorByAuthorId(authorId);
        if (bookAuthor != null) {
            bookAuthorRepository.delete(bookAuthor);
        } else {
            System.err.println("Error");
        }
    }

    @Override
    public void updateBookAuthor(BookAuthor bookAuthor) {
        bookAuthorRepository.save(bookAuthor);
    }
}
