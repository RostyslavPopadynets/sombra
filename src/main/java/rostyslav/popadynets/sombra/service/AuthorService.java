package rostyslav.popadynets.sombra.service;

import rostyslav.popadynets.sombra.entity.Authors;
import rostyslav.popadynets.sombra.entity.Books;

import java.util.List;

public interface AuthorService {

    public void addAuthor(Authors author);

    public Authors findAuthorById(Long authorid);

    public List<Authors> findAllAuthor();

    public void deleteAuthor(Long authorid);

    public void updateAuthor(Authors author);

}
