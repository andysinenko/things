package ua.com.sinenko.things.book.controller;

import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.com.sinenko.things.book.dto.BookDto;
import ua.com.sinenko.things.book.dto.BookMapper;
import ua.com.sinenko.things.book.dto.BookResponse;
import ua.com.sinenko.things.book.service.BookService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Controller
@RequestMapping("/api/v1/books")
@AllArgsConstructor
public class BookController {

    private BookService bookService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        var booksEntities = bookService.getAllBooks();
        var booksDto = booksEntities.stream().map(BookMapper::mapEntityToResponse).toList();

        return new ResponseEntity<>(booksDto, HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Void> addBook(@RequestBody BookDto bookDto) {
        bookService.saveBook(bookDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{bookId}")
    @ResponseBody
    public ResponseEntity<BookDto> getBookById(@PathParam("id") Long id) {
        var booksEntities = bookService.getBookById(id);
        var booksDto = BookMapper.mapEntityToDto(booksEntities);

        return new ResponseEntity<>(booksDto, HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a CSV file!");
        }

        if (!file.getOriginalFilename().endsWith(".csv")) {
            return ResponseEntity.badRequest().body("Only CSV files are allowed!");
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Process each line of CSV
                System.out.println("CSV line: " + line);
            }
            return ResponseEntity.ok("CSV uploaded and processed successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error reading CSV: " + e.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteBookById(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
