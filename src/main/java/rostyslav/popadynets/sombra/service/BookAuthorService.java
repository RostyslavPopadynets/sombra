package rostyslav.popadynets.sombra.service;

import rostyslav.popadynets.sombra.entity.BookAuthor;

import java.util.List;

public interface BookAuthorService {

    public void addBookAuthor(BookAuthor bookAuthor);

    public List<BookAuthor> findAllBookAuthors();

    public void deleteBookAuthorByBook(Long bookId);

    public void deleteBookAuthorByAuthor(Long authorId);

    public void updateBookAuthor(BookAuthor bookAuthor);

}
