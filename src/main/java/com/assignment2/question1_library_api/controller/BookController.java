package com.assignment2.question1_library_api.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.ArrayList;
import com.assignment2.question1_library_api.model.Book;

@RestController
@RequestMapping("/books")
public class BookController {

    private List<Book> books;


    public BookController() {
        this.books = new ArrayList<>();

        books.add(new Book("978-0132350884", "C programming", 2008));
        books.add(new Book("978-0201633610", "Spring Book Introduction", 1994));
        books.add(new Book("978-1617294945", "Java Programming", 2018));
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable String isbn) {

        return books.stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        books.add(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> deleteBook(@PathVariable String isbn) {

        boolean removed = books.removeIf(book -> book.getIsbn().equals(isbn));

        if (removed) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
