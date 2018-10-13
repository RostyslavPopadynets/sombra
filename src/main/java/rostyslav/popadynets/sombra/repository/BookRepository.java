package rostyslav.popadynets.sombra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyslav.popadynets.sombra.entity.Books;

@Repository
public interface BookRepository extends JpaRepository<Books, Long> {

    Books findBooksById(Long id);

    Books findBooksByName(String name);

}
