package rostyslav.popadynets.sombra.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyslav.popadynets.sombra.entity.Authors;
import rostyslav.popadynets.sombra.entity.Books;
import rostyslav.popadynets.sombra.repository.AuthorRepository;
import rostyslav.popadynets.sombra.service.AuthorService;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public void addAuthor(Authors author) {
        authorRepository.save(author);
    }

    @Override
    public Authors findAuthorById(Long authorid) {
        Authors autor = null;
        try {
            autor = authorRepository.findAuthorsById(authorid);
        } catch (Exception e) {
            System.err.println("Error");
        }
        return autor;
    }

    @Override
    public List<Authors> findAllAuthor() {
        return authorRepository.findAll();
    }

    @Override
    public void deleteAuthor(Long authorid) {
        Authors autor = authorRepository.findAuthorsById(authorid);
        if (autor != null) {
            authorRepository.delete(autor);
        } else {
            System.err.println("Error");
        }
    }

    @Override
    public void updateAuthor(Authors author) {
        authorRepository.save(author);
    }
}
