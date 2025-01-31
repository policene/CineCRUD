import com.policene.cinecrud.entity.Movie;
import com.policene.cinecrud.exceptions.GenderNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MovieTest {

    @Test
    @DisplayName("Should Throw Exception When Title Is Empty")
    public void shouldThrowExceptionWhenTitleIsEmpty(){
        Movie movie = new Movie();

        Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> movie.setTitle(""));

        String expectedMessage = "ERROR: Title must have at least 2 characters.";
        String actualMessage = ex.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Should Throw Exception When Title Is Null")
    public void shouldThrowExceptionWhenTitleIsNull(){
        Movie movie = new Movie();

        Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> movie.setTitle(null));

        String expectedMessage = "ERROR: Title must have at least 2 characters.";
        String actualMessage = ex.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Should Throw Exception When Title Is Not Two Characters Long")
    public void shouldThrowExceptionWhenTitleIsNotTwoCharactersLong(){
        Movie movie = new Movie();

        Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> movie.setTitle("O"));

        String expectedMessage = "ERROR: Title must have at least 2 characters.";
        String actualMessage = ex.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Should Save Movie Title")
    public void shouldSaveMovieTitle(){
        Movie movie = new Movie();

        movie.setTitle("Title Example");

        Assertions.assertEquals("Title Example", movie.getTitle());
    }

    @Test
    @DisplayName("Should Throw Exception When Director Is Empty")
    public void shouldThrowExceptionWhenDirectorIsEmpty(){
        Movie movie = new Movie();

        Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> movie.setDirector(""));

        String expectedMessage = "ERROR: Director's name must have at least 4 characters.";
        String actualMessage = ex.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Should Throw Exception When Director Is Null")
    public void shouldThrowExceptionWhenDirectorIsNull(){
        Movie movie = new Movie();

        Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> movie.setDirector(null));

        String expectedMessage = "ERROR: Director's name must have at least 4 characters.";
        String actualMessage = ex.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Should Throw Exception When Director Is Not Four Characters Long")
    public void shouldThrowExceptionWhenDirectorIsNotFourCharactersLong(){
        Movie movie = new Movie();

        Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> movie.setDirector("Leo"));

        String expectedMessage = "ERROR: Director's name must have at least 4 characters.";
        String actualMessage = ex.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Should Throw Exception When Director Has Number")
    public void shouldThrowExceptionWhenDirectorHasNumber(){
        Movie movie = new Movie();

        Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> movie.setDirector("Marcos3"));

        String expectedMessage = "ERROR: Director's name can't have a number.";
        String actualMessage = ex.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }


    @Test
    @DisplayName("Should Save Movie Director")
    public void shouldSaveMovieDirector(){
        Movie movie = new Movie();

        movie.setDirector("Director Example");

        Assertions.assertEquals("Director Example", movie.getDirector());
    }

    @Test
    @DisplayName("Should Throw Exception When Gender Is Null")
    public void shouldThrowExceptionWhenGenderIsNull() {

        Movie movie = new Movie();
        Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> movie.setGender(null));

        String expectedMessage = "ERROR: Gender can't be null.";
        String actualMessage = ex.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Should Throw Exception When Gender Is Empty")
    public void shouldThrowExceptionWhenGenderIsEmpty() {

        Movie movie = new Movie();
        Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> movie.setGender(""));

        String expectedMessage = "ERROR: Gender can't be null.";
        String actualMessage = ex.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Should Throw Exception When Gender Not Found")
    public void shouldThrowExceptionWhenGenderNotFound() {

        Movie movie = new Movie();
        Exception ex = Assertions.assertThrows(GenderNotFoundException.class, () -> movie.setGender("Fantasia"));

        String expectedMessage = "ERROR: Gender not found.";
        String actualMessage = ex.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Should Save Movie Gender")
    public void shouldSaveMovieGender(){
        Movie movie = new Movie();

        movie.setGender("Ficção Científica");

        Assertions.assertEquals("Ficção Científica", movie.getGender());
    }

    @Test
    @DisplayName("Should Throw Exception When Year Has Letter")
    public void shouldThrowExceptionWhenYearHasLetter(){
        Movie movie = new Movie();

        Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> movie.setYear("2222D"));

        String expectedMessage = "ERROR: Year must be a sequence of four numbers.";
        String actualMessage = ex.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Should Throw Exception When Year Out Of Range")
    public void shouldThrowExceptionWhenYearOutOfRange(){
        Movie movie = new Movie();

        Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> movie.setYear("2028"));

        String expectedMessage = "ERROR: Invalid year.";
        String actualMessage = ex.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Should Save Movie Year")
    public void shouldSaveMovieYear(){
        Movie movie = new Movie();

        movie.setYear("2000");

        Assertions.assertEquals(Integer.parseInt("2000"), movie.getYear());
    }

    @Test
    @DisplayName("Should Throw Exception When Rating Has Letter")
    public void shouldThrowExceptionWhenRatingHasLetter(){
        Movie movie = new Movie();

        Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> movie.setRating("d"));

        String expectedMessage = "ERROR: Rating must contain only numbers.";
        String actualMessage = ex.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Should Throw Exception When Rating Out Of Range")
    public void shouldThrowExceptionWhenRatingOutOfRange(){
        Movie movie = new Movie();

        Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> movie.setRating("104"));

        String expectedMessage = "ERROR: Rating must be a number between 0 and 100.";
        String actualMessage = ex.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Should Save Movie Rating")
    public void shouldSaveMovieRating(){
        Movie movie = new Movie();

        movie.setRating("85");

        Assertions.assertEquals(Integer.parseInt("85"), movie.getRating());
    }

}
