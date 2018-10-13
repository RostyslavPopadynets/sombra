package rostyslav.popadynets.sombra.service;

import rostyslav.popadynets.sombra.entity.Books;

import java.util.List;

public interface BookService {

    public void addBook(Books book);

    public Books findBookById(Long bookid);

    public List<Books> findAllBooks();

    public void deleteBook(Long bookid);

    public void updateBook(Books book);

}
