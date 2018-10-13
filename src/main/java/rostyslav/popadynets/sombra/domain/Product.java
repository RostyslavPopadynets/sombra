package rostyslav.popadynets.sombra.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    private String authorName;

    private String authorGenre;

    private String authorBorn;

    private String bookName;

    private String bookPublished;

    private String bookGenre;

    private int bookRating;

}
