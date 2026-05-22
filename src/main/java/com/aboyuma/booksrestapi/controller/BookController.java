package com.aboyuma.booksrestapi.controller;

import com.aboyuma.booksrestapi.entity.Book;
import com.aboyuma.booksrestapi.exception.BookNotFoundException;
import com.aboyuma.booksrestapi.dto.BookRequestDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(
    name = "Books REST API Endpoints",
    description = "Operations related to books"
)
@RestController
@RequestMapping("/api/books")
public class BookController {

    // Simulate database using ArrayList
    private final List<Book> books = new ArrayList<>();

    public BookController() {
        initializeBooks();
    }

    // Initialize sample data
    private void initializeBooks() {

        books.addAll(List.of(
            new Book(
                1L,
                "Basic Music Theory",
                "Somchai Srisuk",
                "Music",
                5
            ),
            new Book(
                2L,
                "Guitar for Beginners",
                "Nattapong Devakul",
                "Music",
                4
            ),
            new Book(
                3L,
                "Piano Fundamentals",
                "Anong Kanyawee",
                "Music",
                5
            ),
            new Book(
                4L,
                "Graphic Design Basics",
                "Kritsada Chaiyasit",
                "Design",
                3
            ),
            new Book(
                5L,
                "Adobe Photoshop for Beginners",
                "Patchara Wongsawat",
                "Design",
                4
            )
        ));
    }

    /*
     * GET /api/books
     */
    @Operation(
        summary = "Get all books",
        description = "Retrieve a list of all available books"
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = "application/json")
    public List<Book> getBooks(
        @Parameter(description = "Optional category filter")
        @RequestParam(required = false) String category,
        @Parameter(description = "Optional author filter")
        @RequestParam(required = false) String author,
        @Parameter(description = "Optional title filter")
        @RequestParam(required = false) String title
    ) {

        return books.stream()
            .filter(book ->
                category == null ||
                    book.getCategory()
                        .equalsIgnoreCase(category)
            )
            .filter(book ->
                author == null ||
                    book.getAuthor()
                        .toLowerCase()
                        .contains(author.toLowerCase())
            )
            .filter(book ->
                title == null ||
                    book.getTitle()
                        .toLowerCase()
                        .contains(title.toLowerCase())
            )
            .toList();
    }

    /*
     * GET /api/books/{id}
     */
    @Operation(
        summary = "Get a book by Id",
        description = "Retrieve a specific book by Id"
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(
        value = "/{id}",
        produces = "application/json"
    )
    public Book getBookById(
        @Parameter(description = "Id of book to be retrieved")
        @PathVariable
        @Min(value = 1)
        Long id
    ) {

        return books.stream()
            .filter(book ->
                book.getId().equals(id)
            )
            .findFirst()
            .orElseThrow(() ->
                new BookNotFoundException(
                    "Book not found - " + id
                )
            );
    }

    /*
     * POST /api/books
     */
    @Operation(
        summary = "Create a new book",
        description = "Add a new book to the list"
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
        consumes = "application/json",
        produces = "application/json"
    )
    public void createBook(@Valid @RequestBody BookRequestDto bookRequestDto) {
        long id = books.isEmpty() ? 1 : books.getLast().getId() + 1;
        Book book = convertToBook(id, bookRequestDto);
        books.add(book);
    }

    /*
     * PUT /api/books/{id}
     */
    @Operation(
        summary = "Update a book",
        description = "Update the details of an existing book"
    )
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(
        value = "/{id}",
        consumes = "application/json",
        produces = "application/json"
    )
    public Book updateBook(
        @Parameter(description = "Id of the book to update")
        @PathVariable
        @Min(value = 1)
        Long id,
        @Valid
        @RequestBody
        BookRequestDto bookRequestDto
    ) {

        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId().equals(id)) {

                Book updatedBook =
                    convertToBook(id, bookRequestDto);

                books.set(i, updatedBook);
                return updatedBook;
            }
        }

        throw new BookNotFoundException(
            "Book not found - " + id
        );
    }

    /*
     * DELETE /api/books/{id}
     */
    @Operation(
        summary = "Delete a book",
        description = "Remove a book from the list"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(
        value = "/{id}",
        produces = "application/json"
    )
    public void deleteBook(
        @Parameter(description = "Id of the book to delete")
        @PathVariable
        @Min(value = 1)
        Long id
    ) {

        books.stream()
            .filter(book ->
                book.getId().equals(id)
            )
            .findFirst()
            .orElseThrow(() -> new BookNotFoundException(
                    "Book not found - " + id
                )
            );

        books.removeIf(book -> book.getId().equals(id));
    }

    /*
     * Convert BookRequest -> Book
     */
    private Book convertToBook(long id, BookRequestDto bookRequestDto) {
        return new Book(
            id,
            bookRequestDto.getTitle(),
            bookRequestDto.getAuthor(),
            bookRequestDto.getCategory(),
            bookRequestDto.getRating()
        );
    }
}