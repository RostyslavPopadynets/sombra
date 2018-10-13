package rostyslav.popadynets.sombra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyslav.popadynets.sombra.entity.BookAuthor;

@Repository
public interface BookAuthorRepository extends JpaRepository<BookAuthor, Long> {

    BookAuthor findBookAuthorByAuthorId(Long autorId);

    BookAuthor findBookAuthorByBookId(Long autorId);

}
