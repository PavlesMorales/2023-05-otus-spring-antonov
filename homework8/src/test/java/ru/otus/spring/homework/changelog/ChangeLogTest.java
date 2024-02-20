package ru.otus.spring.homework.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.spring.homework.models.entity.Author;
import ru.otus.spring.homework.models.entity.Book;
import ru.otus.spring.homework.models.entity.Comment;
import ru.otus.spring.homework.models.entity.Genre;
import ru.otus.spring.homework.repositories.AuthorRepository;
import ru.otus.spring.homework.repositories.BookRepository;
import ru.otus.spring.homework.repositories.CommentRepository;
import ru.otus.spring.homework.repositories.GenreRepository;

import java.util.List;
import java.util.stream.IntStream;

@ChangeLog
public class ChangeLogTest {

    private static final String PART_ID = "00000000000000000000000";

    @ChangeSet(order = "001", id = "dropDb", author = "im", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertAuthors", author = "im", runAlways = true)
    public void insertAuthors(AuthorRepository repository) {
        repository.saveAll(getAuthors());

    }

    @ChangeSet(order = "003", id = "insertGenres", author = "im", runAlways = true)
    public void insertGenres(GenreRepository repository) {
        repository.saveAll(getGenres());
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "im", runAlways = true)
    public void insertBooks(BookRepository repository) {
        repository.saveAll(getBooks(getAuthors(), getGenres()));
    }

    @ChangeSet(order = "005", id = "insertComments", author = "im", runAlways = true)
    public void insertComments(CommentRepository repository) {
        repository.saveAll(getComments(getBooks(getAuthors(), getGenres()).get(0)));
    }


    public static List<Author> getAuthors() {

        return IntStream.range(1, 5).boxed()
                .map(num -> Author.builder()
                        .id(PART_ID + num)
                        .firstName("firstName_" + num)
                        .lastName("lastName_" + num)
                        .build())
                .toList();
    }

    public static List<Genre> getGenres() {
        return IntStream.range(1, 5).boxed()
                .map(num -> Genre.builder()
                        .id(PART_ID + num)
                        .name("genre_" + num)
                        .build())
                .toList();
    }

    public static List<Book> getBooks(final List<Author> authors, final List<Genre> genres) {

        return IntStream.range(1, 5).boxed()
                .map(num -> Book.builder()
                        .id(PART_ID + num)
                        .title("title_" + num)
                        .author(authors.get(num - 1))
                        .genre(genres.get(num - 1))
                        .build())
                .toList();
    }

    public static List<Comment> getComments(Book book) {
        return IntStream.range(1, 5).boxed()
                .map(num -> Comment.builder()
                        .id(PART_ID + num)
                        .text("text_" + num)
                        .book(book)
                        .build())
                .toList();
    }
}
