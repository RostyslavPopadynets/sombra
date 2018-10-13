package rostyslav.popadynets.sombra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyslav.popadynets.sombra.entity.Authors;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Authors, Long> {

    Authors findAuthorsById(Long id);

    Authors findAuthorsByName(String name);

}
